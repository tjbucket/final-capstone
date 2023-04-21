<template>
  <div>
    <!-- SELECT TENANT TO VIEW -->
    <div class="component text-center">
      <select
        id="inputUnit"
        class="form-select"
        v-model="selectedEmployee"
        @change="loadDetails()"
      >
      <option value="-1" disabled selected hidden>
          Select an Employee to see details
        </option>
        <option
          class="form-control form-item"
          v-for="(personnel, index) in this.personnel"
          v-bind:key="personnel.userId"
          v-bind:value="index"
        >
         {{ personnel.first}} {{ personnel.last }} | {{personnel.role}}
        </option>
      </select>
      <b-button class="goBackBtn btn-sm" v-on:click="goBack()">Return to Property Info</b-button>
    </div>

    <!-- TENANT DETAILS DISPLAYED IF SELECTED NOT NULL -->
    <div class="component" v-if="selectedEmployee >= 0" >
      <span>Employee Name: {{ this.employee.first }} {{ this.employee.last }}</span>
      <span>Employee Email: {{ this.employee.email }}</span>

      <!-- Edit property comment div -->
      <span>Property Comment: {{ this.employee.propertyComment }}</span>
      <div class="text-center" v-if="editing">
        <div class="form-group">
          <textarea
            class="form-control form-item commentField"
            type="text"
            v-model="savedComment"
          />
        </div>
      </div>

      <!-- Editing buttons, only edit shown intially, pressing displays "save", "cancel edit" and "delete" -->
      <div class="editingBtns">
        <b-button class="editBtn btn-sm" @click="editing = true" v-if="!editing"
          >Edit Employee</b-button
        >
        <b-button class="editBtn btn-sm" v-if="editing" @click="cancelEdit()"
          >Cancel Edit</b-button
        >
        <b-button
          type="submit"
          :class="!changesMade ? 'disabledBtn btn-sm' : 'submitBtn btn-sm'"
          v-if="editing"
          :disabled="!changesMade"
          v-on:click="updateEmployee()"
        >
          Save
        </b-button>
        <b-button
          @click="deleteEmployee()"
          class="cancelBtn btn-sm"
          v-if="this.selectedEmployee.role != 'Manager' && editing"
          >De-Activate Employee</b-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import personService from "../services/PersonService";

export default {
  data() {
    return {
      tenantUnitStartNumber: 100,
      savedComment: "",
      editingComment: false,
      selectedEmployee: -1,
      selectedUnit: "",
      personnel: [],
      employee: {},
      editing: false,
    };
  },
  methods: {
    loadDetails(){
        this.employee = this.personnel[this.selectedEmployee];
        this.editing = false;
        personService.getTenant(this.employee.id).then((response) => {
            this.employee = response.data;
            this.savedComment = this.employee.propertyComment;
        })
        
    },
    cancelEdit() {
      this.savedComment = this.employee.propertyComment;
      this.editing = false;
    },
    goBack() {
      this.$router.push({ name: "propertyInfo" });
    },
    deleteEmployee() {
      if (confirm("Are you sure you want to delete?")) {
        let tenant = {
          id: this.employee.id,
          role: "Inactive",
          unit: 0,
        };
        personService.updatePendingUser(tenant).then(() => {
          personService.getAllPersonnel().then((response) => {
            this.selectedEmployee = -1;
            this.personnel = response.data;
            this.employee = {};
          });
        });
      }
    },
    updateEmployee() {
      this.employee.propertyComment = this.savedComment;
      personService.updatePendingUser(this.employee).then(() => {
          this.editing = false;
      });
    },
  },
  computed: {
    changesMade() {
      return (
        this.savedComment != this.selectedEmployee.propertyComment
      );
    },
  },
  created() {
    personService.getAllPersonnel().then((response) => {
      this.personnel = response.data;
    });
  },
};
</script>

<style scoped>
.editUnit {
  height: 1.5rem;
  display: flex !important;
  align-items: center;
  gap: 0.25rem;
}
.editingBtns {
  display: flex !important;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}
.unitSelector {
  flex-basis: 4rem;
  font-size: 0.75rem !important;
}
.component > *:not(:last-child) {
  display: block;
  margin-bottom: 0.25rem;
}
.commentField {
  height: 4rem;
}
</style>