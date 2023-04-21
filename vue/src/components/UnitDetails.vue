
<template>
  <div>
    <!-- SELECT UNIT TO VIEW -->
    <div class="component text-center">
      <select
        id="inputUnit"
        class="form-select"
        v-model="selectedUnit"
        @change="getDetails()"
      >
        <option value="" disabled selected hidden>
          Select a Unit to see details
        </option>

        <option
          class="form-control form-item"
          v-for="unit in this.$store.state.units"
          v-bind:key="unit.number"
          v-bind:value="unit"
        >
          {{numberPrefix(unit)}}{{ unit.number }}
        </option>
      </select>
      <b-button class="goBackBtn btn-sm" v-on:click="goBack()"
        >Return to Property Info</b-button
      >
    </div>

    <!-- UNIT DETAILS IF SELECTED NOT NULL -->
    <div class="component" v-if="this.unit.number !== undefined">
      <span>{{ numberPrefix(this.unit) }}{{ this.unit.number }}</span>
      <span>{{this.unit.description}}</span>
      <span>Property Comment: {{ this.unit.propertyComment }}</span>

      <div v-if="editing">
        <div class="form-group">
          <textarea
            class="form-control form-item commentField"
            type="text"
            v-model="savedComment"
          />
        </div>
      </div>

      <div class="editingBtns">
        <b-button class="editBtn btn-sm" @click="editing = true" v-if="!editing"
          >Edit Unit</b-button
        >
        <b-button class="editBtn btn-sm" v-if="editing" @click="cancelEdit()"
          >Cancel Edit</b-button
        >
        <b-button
          type="submit"
          :class="!changesMade ? 'disabledBtn btn-sm' : 'submitBtn btn-sm'"
          v-if="editing"
          :disabled="!changesMade"
          v-on:click="updateUnit()"
        >
          Save
        </b-button>
        <b-button
          @click="deleteUnit()"
          class="cancelBtn btn-sm"
          v-if="editing && this.$store.state.person.role == 'Manager'"
          >Delete Unit</b-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import unitService from "../services/UnitService";

export default {
  data() {
    return {
      savedComment: "",
      editing: false,
      selectedUnit: "",
      units: [],
      unit: {},
    };
  },
  methods: {
    goBack() {
      this.$router.push({ name: "propertyInfo" });
    },
    updateUnit() {
      this.unit.propertyComment = this.savedComment;
      unitService
        .setUnitPropComment(this.unit.propertyComment, this.selectedUnit.number)
        .then(() => {
          this.editing = false;
        });
    },
    cancelEdit() {
      this.savedComment = this.unit.propertyComment;
      this.editing = false;
    },
    getDetails() {
      unitService.getUnitById(this.selectedUnit.number).then((response) => {
        this.unit = response.data;
        this.savedComment = this.unit.propertyComment;
        this.cancelEdit();
      });
    },
    numberPrefix(unit) {
      return unit.number < 100 ? "Area " : "#";
    },
    deleteUnit() {
      // Not doing anything right now because not sure if unit deletion is implemented or even possible
    },
  },
  computed: {
    changesMade() {
      return this.savedComment != this.unit.propertyComment;
    },
  },
};
</script>

<style scoped>
.editDisplay {
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