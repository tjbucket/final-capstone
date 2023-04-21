<template>
  <div id="app">
    <div id="navdiv">
      <b-navbar toggleable>
        <b-navbar-brand id="logo" :to="{ name: 'home' }"
          >MAINT-TENANTS</b-navbar-brand
        >
        <b-navbar-toggle
          v-if="this.$store.state.token != ''"
          target="navbar-toggle-collapse"
        >
          <template #default="{ expanded }">
            <b-icon
              class="navIcon"
              v-if="expanded"
              icon="chevron-bar-up"
              font-scale="1.5"
            ></b-icon>
            <b-icon
              class="navIcon"
              v-else
              icon="chevron-bar-down"
              font-scale="1.5"
            ></b-icon>
          </template>
        </b-navbar-toggle>

        <b-collapse
          v-if="this.$store.state.token != ''"
          id="navbar-toggle-collapse"
          is-nav
        >
          <b-navbar-nav class="ml-auto">
            <b-nav-item align="right" v-if="this.$store.state.person.role != undefined && this.$store.state.person.role != 'Inactive'" :to="{ name: 'viewWorkOrders' }"
              >View Work Orders</b-nav-item
            >
            <b-nav-item align="right" v-if="this.$store.state.person.role != undefined && this.$store.state.person.role != 'Inactive'" :to="{ name: 'createWorkOrder' }"
              >Create Work Order</b-nav-item
            >
            <b-nav-item
              align="right"
              v-if="this.$store.state.person.role !== 'Tenant' && this.$store.state.person.role != undefined && this.$store.state.person.role != 'Inactive'"
              :to="{ name: 'propertyInfo' }"
            >
              Property Info</b-nav-item
            >
            <b-nav-item
              align="right"
              v-if="this.$store.state.person.role === 'Manager'"
              :to="{ name: 'pending' }"
            >
              View Pending Users
            </b-nav-item>
            <b-nav-item align="right" :to="{ name: 'logout' }"
              >Logout</b-nav-item
            >
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="spacer"></div>
    <router-view />
  </div>
</template>


<script>
import workOrderService from "../src/services/WorkOrderService";
import metricService from "../src/services/MetricService.js";
import UnitService from "./services/UnitService";

export default {
  data() {
    return {
      window: {
        width: 0,
        height: 0,
      },
    };
  },
  created() {
    if (this.$store.state.units.length == 0 && this.$store.state.token != "") {
      UnitService.getUnits().then((response) => {
        this.$store.commit("SET_UNIT_ARRAY", response.data);
      });
      workOrderService.getAreas().then((response) => {
        this.$store.commit("SET_AREA_ARRAY", response.data);
      });
      workOrderService.getStatuses().then((response) => {
        this.$store.commit("SET_STATUS_ARRAY", response.data);
      });
      metricService.getSixMonths().then((response) => {
        this.$store.commit("SET_METRIC", response.data);
      });
      metricService.getSixMonthsRatings().then((response) => {
        this.$store.commit("SET_RATINGS", response.data);
      });
      if (this.$store.state.person.role == undefined) {
        this.$router.push("/waiting");
      }
    }
  },
};
</script>

<style scoped>
.spacer {
  height: 4rem;
}
#logo {
  font-size: 2rem;
  font-weight: 500;
  font-family: var(--headerFont);
}
*:focus, *:hover {
  color: var(--text) !important;
}

#navdiv {
  background-color: var(--component) !important;
  display: flex;
  justify-content: center;
  width: 100vw;
  border-bottom: 0.01rem solid var(--highlight);
  position: fixed;
  z-index: 1000;
  padding: 0 0.7rem;
}

nav,
nav > *,
nav > * > *,
nav > * > * > * > * {
  background-color: var(--component) !important;
  color: var(--text);
  padding: 0;
  margin: 0;
}

nav {
  flex-grow: 1;
  padding: 0.25rem 0.5rem;
  max-width: 800px;
}
.navIcon {
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}

@media screen and (max-width: 500px) {
  #logo {
    font-size: 1.6rem;
  }
}

</style>
