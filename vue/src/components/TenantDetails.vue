<template>
  <div>
    <!-- SELECT TENANT TO VIEW -->
    <div class="component text-center">
      <select
        id="inputUnit"
        class="form-select"
        v-model="selectedTenant"
        @change="getDetails()"
      >
      <option value="" disabled selected hidden>
          Select a Tenant to see details
        </option>
        <option
          class="form-control form-item"
          v-for="tenant in this.tenants"
          v-bind:key="tenant"
          v-bind:value="tenant"
        >
          {{ tenant }}
        </option>
      </select>
      <b-button class="goBackBtn btn-sm" v-on:click="goBack()">Return to Property Info</b-button>
    </div>

    <!-- TENANT DETAILS DISPLAYED IF SELECTED NOT NULL -->
    <div class="component" v-if="tenant.first !== undefined">
      <span>Tenant Name: {{ this.tenant.first }} {{ this.tenant.last }}</span>
      <span>Tenant Email: {{ this.tenant.email }}</span>

      <!-- Edit Unit # Div -->
      <div class="editUnit">
        <div>
          <span>Unit: {{ this.tenant.unit }}</span>
        </div>

        <div style="unitSelector" v-if="editing">
          <select id="inputUnit" class="form-select" v-model="selectedUnit">
            <option
              class="form-control form-item"
              v-for="unit in tenantUnitsFilter"
              v-bind:key="unit.number"
              v-bind:value="unit.number"
            >
              {{ unit.number }}
            </option>
          </select>
        </div>
      </div>

      <!-- Edit property comment div -->
      <span>Property Comment: {{ this.tenant.propertyComment }}</span>
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
          >Edit Tenant</b-button
        >
        <b-button class="editBtn btn-sm" v-if="editing" @click="cancelEdit()"
          >Cancel Edit</b-button
        >
        <b-button
          type="submit"
          :class="!changesMade ? 'disabledBtn btn-sm' : 'submitBtn btn-sm'"
          v-if="editing"
          :disabled="!changesMade"
          v-on:click="updateTenant()"
        >
          Save
        </b-button>
        <b-button
          @click="deleteTenant()"
          class="cancelBtn btn-sm"
          v-if="this.tenant.role != 'Manager' && editing"
          >Delete Tenant</b-button
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
      selectedTenant: "",
      selectedUnit: "",
      tenants: [],
      tenant: {},
      editing: false,
    };
  },
  methods: {
    cancelEdit() {
      this.selectedUnit = this.tenant.unit;
      this.savedComment = this.tenant.propertyComment;
      this.editing = false;
    },
    goBack() {
      this.$router.push({ name: "propertyInfo" });
    },

    // This retrieves an entire tenant object from the backend by scraping the id from the string in the drop down selector
    getDetails() {
      if (this.editing) {
        this.cancelEdit();
      }
      const ID = parseInt(
        this.selectedTenant.substring(0, this.selectedTenant.indexOf(":"))
      );
      personService.getTenant(ID).then((response) => {
        this.tenant = response.data;
        this.savedComment = this.tenant.propertyComment;
        this.selectedUnit = this.tenant.unit;
      });
    },
    deleteTenant() {
      if (confirm("Are you sure you want to delete?")) {
        let tenant = {
          id: this.tenant.id,
          role: this.tenant.role,
          unit: 0,
        };
        personService.updatePendingUser(tenant).then(() => {
          personService.getTenants().then((response) => {
            this.tenants = response.data;
            this.tenant = {};
          });
        });
      }
    },
    updateTenant() {
      const TENANT_INDEX = this.tenants.indexOf(this.selectedTenant);
      this.tenant.unit = this.selectedUnit;
      this.tenant.propertyComment = this.savedComment;
      personService.updatePendingUser(this.tenant).then(() => {
        personService.getTenants().then((response) => {
          this.tenants = response.data;
          this.selectedTenant = this.tenants[TENANT_INDEX];
          this.editing = false;
        });
      });
    },
    tenantUnit(unit){
      return unit.number >= this.tenantUnitStartNumber;
    }
  },
  computed: {
    changesMade() {
      return (
        this.selectedUnit != this.tenant.unit ||
        this.savedComment != this.tenant.propertyComment
      );
    },
    tenantUnitsFilter(){
      return this.$store.state.units.filter(this.tenantUnit);
    }
  },
  created() {
    personService.getTenants().then((response) => {
      this.tenants = response.data;
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