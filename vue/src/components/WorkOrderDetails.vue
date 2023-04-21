<template>
  <div class="component">
    <!-- Basic order details -->

    <!-- Show emergency in status if emergency -->
    <h1>
      #{{ this.$store.state.activeWorkOrder.id }} ({{
        this.$store.state.activeWorkOrder.status
      }})
    </h1>
    <h2>{{numberPrefix(this.$store.state.activeWorkOrder.unit)}}{{ this.$store.state.activeWorkOrder.unit }}</h2>
    <h2>{{ this.$store.state.activeWorkOrder.area }}</h2>
    <h2 class="description">
      "{{ this.$store.state.activeWorkOrder.description }}"
    </h2>
    

    <div class="imgCont">
      <img
        v-for="(image, index) in this.$store.state.activeWorkOrder.images"
        :key="image"
        :class="{ woImage: true, 'img-enlarged-size': imageHovered === index }"
        :src="image"
        @click="enlargeImage(index)"
      />
    </div>

    <!-- Radio buttons for statuses -->
    <div
      v-if="(isMaintenance || isManager) && !orderClosed"
      class="form-check form-check-inline"
    >
      <b-form-group v-slot="{ ariaDescribedby }">
        <b-form-radio-group
          id="btn-radios-2"
          v-model="selectedStatus"
          :options="statusesToShow"
          :aria-describedby="ariaDescribedby"
          button-variant="secondary"
          size="md"
          name="radio-btn-outline"
          class="status-selectors"
          buttons
        ></b-form-radio-group>
      </b-form-group>
    </div>
    <!-- Rating scale -->
    <b-form-group v-if="isTenant && orderClosed">
      <div id="half-stars-example">
        <div class="rating-group">
          <input
            class="rating__input rating__input--none"
            checked
            name="rating2"
            id="rating2-0"
            value="0"
            v-model="selectedRating"
            type="radio"
          />
          <label aria-label="0 stars" class="rating__label" for="rating2-0"
            >&nbsp;</label
          >
          <label
            aria-label="0.5 stars"
            class="rating__label rating__label--half"
            for="rating2-05"
            ><i class="rating__icon rating__icon--star fa fa-star-half"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-05"
            value="1"
            v-model="selectedRating"
            type="radio"
          />
          <label aria-label="1 star" class="rating__label" for="rating2-10"
            ><i class="rating__icon rating__icon--star fa fa-star"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-10"
            value="1"
            v-model="selectedRating"
            type="radio"
          />
          <label
            aria-label="1.5 stars"
            class="rating__label rating__label--half"
            for="rating2-15"
            ><i class="rating__icon rating__icon--star fa fa-star-half"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-15"
            value="2"
            v-model="selectedRating"
            type="radio"
          />
          <label aria-label="2 stars" class="rating__label" for="rating2-20"
            ><i class="rating__icon rating__icon--star fa fa-star"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-20"
            value="2"
            v-model="selectedRating"
            type="radio"
          />
          <label
            aria-label="2.5 stars"
            class="rating__label rating__label--half"
            for="rating2-25"
            ><i class="rating__icon rating__icon--star fa fa-star-half"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-25"
            value="3"
            v-model="selectedRating"
            type="radio"
            checked
          />
          <label aria-label="3 stars" class="rating__label" for="rating2-30"
            ><i class="rating__icon rating__icon--star fa fa-star"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-30"
            value="3"
            v-model="selectedRating"
            type="radio"
          />
          <label
            aria-label="3.5 stars"
            class="rating__label rating__label--half"
            for="rating2-35"
            ><i class="rating__icon rating__icon--star fa fa-star-half"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-35"
            value="4"
            v-model="selectedRating"
            type="radio"
          />
          <label aria-label="4 stars" class="rating__label" for="rating2-40"
            ><i class="rating__icon rating__icon--star fa fa-star"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-40"
            value="4"
            v-model="selectedRating"
            type="radio"
          />
          <label
            aria-label="4.5 stars"
            class="rating__label rating__label--half"
            for="rating2-45"
            ><i class="rating__icon rating__icon--star fa fa-star-half"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-45"
            value="5"
            v-model="selectedRating"
            type="radio"
          />
          <label aria-label="5 stars" class="rating__label" for="rating2-50"
            ><i class="rating__icon rating__icon--star fa fa-star"></i
          ></label>
          <input
            class="rating__input"
            name="rating2"
            id="rating2-50"
            value="5"
            v-model="selectedRating"
            type="radio"
          />
        </div>
      </div>
    </b-form-group>
    <!-- Comment box -->
    <b-form-textarea
      class="commentBox"
      :placeholder="
        isMaintenance || isManager
          ? 'Comment on status here'
          : 'Please leave a comment on the results of your work order'
      "
      v-if="
        ((isMaintenance || isManager) && !orderClosed) ||
        (isTenant && orderClosed)
      "
      v-model="comment"
    ></b-form-textarea>
    <!-- Update work order button -->
    <div class="btnCont text-center">
      <b-button
        v-if="(isMaintenance || isManager) && !orderClosed"
        variant="primary"
        class="submitBtn"
        :class="!isFormFilled ? 'disabledBtn' : 'submitBtn'"
        :disabled="!isFormFilled"
        v-on:click.prevent="updateStatus()"
      >
        Update Work Order
      </b-button>
      <!-- Mark completed button -->
      <b-button
        v-if="isTenant && orderClosed"
        variant="success"
        class="complete"
        :class="!isFormFilled ? 'disabledBtn' : 'submitBtn'"
        :disabled="!isFormFilled"
        v-on:click.prevent="markComplete()"
      >
        Mark Complete
      </b-button>
      <b-button
        class="cancelBtn"
        v-if="isTenant && orderClosed"
        @click="reopenOrder()"
      >
        Reopen Work Order
      </b-button>
      <b-button class="goBackBtn" @click="goBack()"> Return to List </b-button>
    </div>
  </div>
</template>

<script>
import workOrderService from "../services/WorkOrderService";
import personService from "../services/PersonService";
import emailjs from "@emailjs/browser";

export default {
  data() {
    return {
      selectedStatus: "",
      selectedRating: null,
      comment: "",
      imageHovered: -1,
      imageEnlarged: false,
      primaryTenant: {},
    };
  },
  props: {
    orderId: Number,
  },
  methods: {
    enlargeImage(index) {
      if(index != this.imageHovered){
        this.imageHovered = index;
        this.imageEnlarged = true;
      } else{
      this.imageEnlarged = false;

      this.imageHovered = -1;
      }
    },
    shrinkImage() {
      this.imageHovered = "";
      this.imageEnlarged = false;
    },
    submitComment() {
      let fullComment = { text: this.comment, orderId: this.orderId };
      workOrderService.createComment(fullComment);
      this.resetActive();
      this.$router.push("/viewWorkOrders");
    },
    updateStatus() {
      this.$store.state.activeWorkOrder.status =
        this.selectedStatus === "" ? "Pending" : this.selectedStatus;
      this.$store.state.activeWorkOrder.emergency = false;
      workOrderService
        .updateWorkOrder(this.$store.state.activeWorkOrder)
        .then((response) => {
          if (response.status == 200) {
            if (this.selectedStatus === "Closed") {
              let templateParams = {
                first_name: this.primaryTenant.first,
                last_name: this.primaryTenant.last,
                work_id: this.orderId,
                to_address: this.primaryTenant.email,
                worker_first_name: this.$store.state.person.first,
                worker_last_name: this.$store.state.person.last,
                close_message: this.comment,
              };

              emailjs.send(
                "notif_service",
                "closed_notif",
                templateParams,
                "6thTHR6985fVs23-3"
              );
            }
            this.submitComment();
          }
        });
    },
    markComplete() {
      this.$store.state.activeWorkOrder.rating = this.selectedRating;
      this.$store.state.activeWorkOrder.status = "Accepted";
      workOrderService
        .updateWorkOrder(this.$store.state.activeWorkOrder)
        .then((response) => {
          if (response.status == 200) {
            this.submitComment();
          }
        });
    },
    resetActive() {
      let resetter = {
        id: "",
        unit: "",
        area: "",
        emergency: false,
        description: "",
        status: "closed",
        rating: "",
      };
      this.$store.commit("RESET_ACTIVE_WORK_ORDER", resetter);
    },
    goBack() {
      this.resetActive();
      this.$router.back();
    },
    reopenOrder() {
      this.$store.state.activeWorkOrder.status = "Open";
      workOrderService
        .updateWorkOrder(this.$store.state.activeWorkOrder)
        .then((response) => {
          if (response.status == 200) {
            this.submitComment();
          }
        });
    },
    numberPrefix(unit) {
      return unit < 100 ? "Area " : "Unit ";
    },
  },
  created() {
    workOrderService.get(this.orderId).then((response) => {
      this.$store.commit("SET_ACTIVE_WORK_ORDER", response.data);
      personService
        .getTenant(this.$store.state.activeWorkOrder.submitterId)
        .then((response) => {
          this.primaryTenant = response.data;
        });
    });
  },
  computed: {
    isFormFilled() {
      return (
        (this.$store.state.activeWorkOrder.status === "Open" &&
          this.comment !== "" &&
          this.selectedStatus !== "") ||
        (this.$store.state.activeWorkOrder.status === "Pending" &&
          this.comment !== "") ||
        this.$store.state.activeWorkOrder.status === "Closed"
      );
    },
    isTenant() {
      return this.$store.state.person.role === "Tenant";
    },
    isMaintenance() {
      return this.$store.state.person.role === "Maintenance";
    },
    isManager() {
      return this.$store.state.person.role === "Manager";
    },
    orderClosed() {
      return this.$store.state.activeWorkOrder.status === "Closed";
    },
    statusesToShow() {
      let allStatuses = this.$store.state.statuses;
      let activeStatus = this.$store.state.activeWorkOrder.status;
      return allStatuses.filter(
        (status) => status != activeStatus && status != "Accepted"
      );
    },
  },
};
</script>

<style>
.commentBox {
  margin-bottom: 0.25rem !important;
}
.form-check {
  margin: 0 !important;
  padding: 0 !important;
  margin-bottom: 0.25rem !important;
}
.description {
  font-style: italic;
}
.imgCont {
  display: flex;
  width: 100%;
  transition: all 500ms;
  flex-wrap: wrap;
  flex-direction: row;
  padding: 1rem;
  gap: 0.25rem;
  justify-content: space-evenly;
}
.woImage {
  width: 25%;
  transition: all 500ms;
  height: 150px;
  border-radius: 5%;
  border: solid 0.1rem var(--highlight);
}
@media (max-width: 800px) {
  .woImage {
    width: 45%;
  }
}
@media (max-width: 450px) {
  .woImage {
    width: 100%;
  }
}
.img-enlarged-size {
  width: 100%;
  height: 100%;
  max-height: 76%;
  max-width: 76%;
  transition: all 500ms;
  order: -1;
  flex: 1 0 100%;
}
input[type="radio"] {
  display: none;
}
#status-buttons {
  display: inline;
}
.workOrder {
  background-color: var(--component);
  color: var(--text);
  border: 0.15rem solid var(--highlight);
  border-radius: 1rem;
  padding: 1rem;
  margin: 1rem;
}
.btn {
  appearance: none;
}
.btnCont > * {
  margin: 0 0.1rem;
}
.workOrder > * {
  margin-top: 0.5rem;
}
.workOrder > *:first-child {
  margin-top: 0;
}

/* use display:inline-flex to prevent whitespace issues. alternatively, you can put all the children of .rating-group on a single line */
.rating-group {
  display: inline-flex;
}

/* make hover effect work properly in IE */
.rating__icon {
  pointer-events: none;
}

/* hide radio inputs */
.rating__input {
  position: absolute !important;
  left: -9999px !important;
}

/* set icon padding and size */
.rating__label {
  cursor: pointer;
  /* if you change the left/right padding, update the margin-right property of .rating__label--half as well. */
  padding: 0 0.1em;
  font-size: 2rem;
}

/* add padding and positioning to half star labels */
.rating__label--half {
  padding-right: 0;
  margin-right: -0.6em;
  z-index: 2;
}

/* set default star color */
.rating__icon--star {
  color: orange;
}

/* set color of none icon when unchecked */
.rating__icon--none {
  color: #eee;
}

/* if none icon is checked, make it red */
.rating__input--none:checked + .rating__label .rating__icon--none {
  color: red;
}

/* if any input is checked, make its following siblings grey */
.rating__input:checked ~ .rating__label .rating__icon--star {
  color: #ddd;
}

/* make all stars orange on rating group hover */
.rating-group:hover .rating__label .rating__icon--star,
.rating-group:hover .rating__label--half .rating__icon--star {
  color: orange;
}

/* make hovered input's following siblings grey on hover */
.rating__input:hover ~ .rating__label .rating__icon--star,
.rating__input:hover ~ .rating__label--half .rating__icon--star {
  color: #ddd;
}

/* make none icon grey on rating group hover */
.rating-group:hover
  .rating__input--none:not(:hover)
  + .rating__label
  .rating__icon--none {
  color: #eee;
}

/* make none icon red on hover */
.rating__input--none:hover + .rating__label .rating__icon--none {
  color: red;
}
</style>