<template>
  <form class="component" v-on:submit.prevent>
    <!-- Drop down for unit -->
    <div class="form-group">
      <label class="form-identifier" for="inputUnit">Unit</label>
      <select id="inputUnit" class="form-select" v-model="workOrder.unit">
        <option value="" disabled selected hidden>Select a Unit</option>
        <option
          class="form-control form-item"
          v-for="unit in filteredUnitsList"
          v-bind:key="unit.number"
          v-bind:value="unit.number"
        >
          {{numberPrefix(unit)}}{{ unit.text }}
        </option>
      </select>
    </div>
    <!-- Drop down for area -->
    <div class="form-group">
      <label class="form-identifier" for="">Type of Issue</label>

      <select
        name=""
        id="inputArea"
        class="form-select"
        v-model="workOrder.area"
      >
        <option
          class="form-control form-item"
          v-for="id in this.$store.state.areas"
          v-bind:key="id"
          v-bind:value="id"
        >
          {{ id }}
        </option>
      </select>
      <small class="small"> Leave N/A if unsure. </small>
    </div>
    <!-- Description of issue -->
    <div class="form-group">
      <label class="form-identifier" for="">Description Of Issue</label>&nbsp;
      <textarea
      placeholder="Please describe the issue you're experiencing."
        class="form-control form-item commentField"
        type="text"
        v-model="workOrder.description"
      />
    </div>

    <!-- This is the button to upload images as well as the container to view uploaded images. -->

    <div class="form-group imageBox">
      <label for="" class="form-identifier">Attach Images</label>
      <b-button class="submitBtn" @click="openUploadWidget()">Upload</b-button>
      <div class="imageHolder">
        <div
          class="imgBox"
          v-for="(image, index) in this.workOrder.images"
          :key="image"
        >
          <img class="imgReduc" :src="image" />
          <!-- <span @click="deletePhoto(index)" class="deleteImg">X</span> -->
          <img @click="deletePhoto(index)" class="deleteImg" :src="require('@/assets/deleteBtn.png')" alt="delete"/>
        </div>
      </div>
    </div>

    <!-- Emergency or not -->
    <div class="form-group emergencySelector">
      <label class="form-identifier">Is this an emergency?</label>&nbsp;
      <input
        class="form-check-input form-item"
        type="checkbox"
        v-model="workOrder.emergency"
        id="accept"
        @change="emergencyChecked"
      />
    </div>

    <div class="form-group text-center">
      <small :class="[workOrder.emergency ? 'emergency' : 'transparent']">
        Please call 911 or 614-867-5309 immediately!
      </small>
    </div>

    <!-- Submission -->
    <div class="form-group text-center btnCont">
      <b-button
        type="submit"
        class="btn-primary btn-lg form-item"
        :class="isFormFilled ? 'disabledBtn' : 'submitBtn'"
        :disabled="isFormFilled"
        v-on:click="saveWorkOrder()"
      >
        Create Work Order
      </b-button>
      <b-button class="cancelBtn btn-lg" v-on:click="goBack()">Cancel</b-button>
    </div>
  </form>
</template>

<script>
import workOrderService from "../services/WorkOrderService";
import emailjs from "@emailjs/browser";

export default {
  name: "CreateWorkOrder",
  data() {
    return {
      workOrder: {
        unit: "",
        area: "N/A",
        emergency: false,
        description: "",
        images: [],
      },
      options: [
        { value: "Plumbing", text: "Plumbing" },
        { value: "Electrical", text: "Electrical" },
        { value: "HVAC", text: "HVAC" },
        { value: "N/A", text: "N/A" },
      ],
    };
  },
  methods: {
    openUploadWidget() {
      window.cloudinary
        .createUploadWidget(
          {
            cloud_name: "drdme3dct",
            upload_preset: "base-upload",
            sources: ["local", "url"],
            folder: "workOrder",
          },
          (error, result) => {
            if (!error && result && result.event === "success") {
              this.workOrder.images.push(result.info.secure_url);
            }
          }
        )
        .open();
    },
    saveWorkOrder() {
      workOrderService.create(this.workOrder).then((response) => {
        if (response.status == 201) {
          if (this.workOrder.emergency) {
            let templateParams = {
              first_name: this.$store.state.person.first,
              last_name: this.$store.state.person.last,
              unit_number: this.workOrder.unit,
              text_description: this.workOrder.description,
              to_address: "mainttenantssecondary@gmail.com"
            };

            emailjs.send(
              "notif_service",
              "emergency_notif",
              templateParams,
              "6thTHR6985fVs23-3"
            );
          }

          // call back database and get the work order ID, then upload staged images to work order ID
          this.$router.push({ name: "viewWorkOrders" })
        }
      });
    },
    emergencyChecked() {
      if (this.workOrder.emergency) {
        alert("Please call 911 or 614-867-5309 immediately!");
      }
    },
    deletePhoto(index) {
      this.workOrder.images.splice(index, 1);
    },
    goBack() {
      this.$router.push({ name: "home" });
    },
    numberPrefix(unit) {
      return unit.number < 100 ? "Area " : "#";
    },
  },
  computed: {
    isFormFilled() {
      return (
        this.workOrder.unit === "" ||
        this.workOrder.area === "" ||
        this.workOrder.description === ""
      );
    },
    filteredUnitsList() {
      let unitsList = JSON.parse(JSON.stringify(this.$store.state.units));
      unitsList = unitsList.filter(
        (unit) =>
          unit.number != 0 &&
          (unit.number < 50 ||
            (unit.number == this.$store.state.person.unit &&
              this.$store.state.person.role == "Tenant") ||
            this.$store.state.person.role != "Tenant")
      );
      unitsList.forEach((unit) => {
        return (unit.text =
          unit.number +
          (unit.description == "" ? "" : " | " + unit.description));
      });
      return unitsList;
    },
  },
  created(){
    if(this.$store.state.person.role == 'Tenant'){
      this.workOrder.unit = this.$store.state.person.unit;
    }
  },
};
</script>

<style scoped>
* {
  padding: 0.2rem;
}
.imageHolder {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  flex-direction: row;
  padding: 1rem;
  transition: all 500ms;
  justify-content: space-evenly;
}
.imgBox {
  position: relative;
  width: 25%;
  height: 150px;
  gap: 0.25rem;
  display: flex;
}
@media (max-width: 800px) {
  .imgBox {
    width: 45%;
  }
}
@media (max-width: 450px) {
  .imgBox {
    width: 100%;
  }
}
.emergencySelector{
  display: flex !important;
  align-items: center !important;
}
.imgReduc {
  width: 100%;
  height: auto;
  transition: all 500ms;
  margin: 0 !important;
  padding: 0 !important;
  border-radius: 5%;
  border: solid 0.1rem var(--highlight);
}
.deleteImg {
  font-size: 0.75rem;
  position: absolute;
  top: 2px;
  right: 2px;
  z-index: 100;
  width: 15%;
  color: var(--text);
  cursor: pointer;
  border-radius: 5%;
}
.btnCont > :first-child {
  margin-right: 0.2rem;
}
.btn.cancel {
  background-color: var(--emergency) !important;
}
.transparent {
  color: transparent;
}
.emergency {
  color: red;
  font-size: 1rem;
  background: #f4f4f9;
  border: solid 0.1rem black;
  border-radius: 5px;
}
.form-check-input{
  margin: 0 !important;
  padding: 0 !important;
}
.form-identifier {
  font-weight: 700;
  text-decoration: underline;
}
.workOrder {
  background-color: var(--component);
  color: var(--text);
  border: 0.01rem solid var(--highlight);
  border-radius: 1rem;
  padding: 10px;
  margin: 10px;
}
</style>