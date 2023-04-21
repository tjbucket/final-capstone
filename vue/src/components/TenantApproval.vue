<template>
  <div class="entireForm">
    <h1>{{ this.person.last }}, {{ this.person.first }}</h1>
    <b-form v-if="showForm">
      <select v-model="outgoing.unit" id="" class="form-select">
        <option value="" disabled selected hidden>Select a Unit</option>
        <option
              class="form-control form-item"
              v-for="unit in filteredUnitList"
              v-bind:key="unit"
              v-bind:value="unit"
            >
              {{ unit }}
            </option>
      </select>

      <select class="form-select" id="roleSelector" v-model="outgoing.role">
        <option value="" disabled selected hidden>Select a Role</option>
        <option value="Tenant">Tenant</option>
        <option value="Maintenance">Maintenance</option>
      </select>

      <b-button class="submitBtn" @click="approve()">Approve</b-button>
      <b-button class="cancelBtn" @click="deny()">Deny</b-button>
    </b-form>
    <h1 v-else>User was {{ status }}.</h1>
  </div>
</template>

<script>
import personService from "../services/PersonService";
export default {
  props: ["index"],
  data() {
    return {
      person: {
        first: "",
        last: "",
      },
      outgoing: {
        id: "",
        unit: "",
        role: "",
      },
      showForm: true,
      status: "",
    };
  },
  created() {
    this.person.first = this.$store.state.pending[this.index].first;
    this.person.last = this.$store.state.pending[this.index].last;
    this.outgoing.id = this.$store.state.pending[this.index].id;
  },
  methods: {
    approve() {
      personService.updatePendingUser(this.outgoing).then((response) => {
        if (response.status == 200) {
          this.showForm = false;
          this.status = "approved";
        }
      });
    },
    deny() {
      personService.deleteUser(this.outgoing.id).then((response) => {
        if (response.status == 200) {
          this.showForm = false;
          this.status = "denied";
        }
      });
    },
  },
  computed: {
    filteredUnitList() {
      let unitsList = JSON.parse(JSON.stringify(this.$store.state.units));
      unitsList = unitsList.filter((unit) => unit.number > 100);
      let unitNumbers = [];
      unitsList.forEach((unit) => {
        unitNumbers.push(unit.number);
      });
      return unitNumbers;
    },
  },
};
</script>

<style scoped>
.denyBtn {
  background-color: var(--emergency) !important;
}
.entireForm > *,
.entireForm > * > * {
  margin-bottom: .25rem !important;
}
.form-select {
  max-width: max(200px, 25%);
}
.submitBtn{
  margin-right: .25rem !important;
}
</style>