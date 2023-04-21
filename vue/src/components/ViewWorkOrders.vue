<template>
  <div class="workOrder-container">
    <div class="selectors">
      <b-form-group id="selectors" v-slot="{ ariaDescribedby }">
        <b-form-radio-group
          v-model="selectedStatus"
          :options="statusesToShow"
          :aria-describedby="ariaDescribedby"
          :size="determineButtonSize"
          buttons
        ></b-form-radio-group>
      </b-form-group>
    </div>
    <router-link
      style="cursor: pointer"
      tag="div"
      v-bind:to="{ name: 'workOrder', params: { id: workOrder.id } }"
      class="component routers"
      :class="[
        workOrder.emergency
          ? 'emergency'
          : workOrder.status === 'Open'
          ? 'open'
          : workOrder.status === 'Pending'
          ? 'pending'
          : '',
      ]"
      v-for="workOrder in filterOrders"
      v-bind:key="workOrder.id"
    >
      <div class="card-identifier">
        <h1>#{{ workOrder.id }} ({{ workOrder.status }})</h1>
        <h2>{{ numberPrefix(workOrder.unit) }}{{ workOrder.unit }}</h2>
        <h2>{{ workOrder.area }}</h2>
        <h2 class="description">"{{ workOrder.description }}"</h2>
      </div>
    </router-link>
  </div>
</template>

<script>
import workOrderService from "../services/WorkOrderService";
export default {
  data() {
    return {
      selectedStatus: "All",
      window: {
        width: 0,
        height: 0,
      },
    };
  },
  created() {
    this.getWorkOrders();
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    getWorkOrders() {
      workOrderService.getWorkOrders().then((response) => {
        this.$store.commit("SET_WORK_ORDERS", response.data);
      });
    },
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
    },
    numberPrefix(unit) {
      return unit < 100 ? "Area " : "Unit ";
    },
  },
  computed: {
    determineButtonSize() {
      if (this.window.width < 400) {
        return "md";
      }
      return "lg";
    },
    filterOrders() {
      let workOrders = this.$store.state.workOrders;
      let role = this.$store.state.person.role;
      if (role == "Tenant") {
        workOrders = workOrders.filter((order) => {
          return order.unit == this.$store.state.person.unit;
        });
      }
      if (this.selectedStatus != "All") {
        workOrders = workOrders.filter((order) => {
          return order.status == this.selectedStatus;
        });
      }
      return workOrders;
    },
    statusesToShow() {
      let allStatuses = JSON.parse(JSON.stringify(this.$store.state.statuses));
      allStatuses.unshift("All");
      return allStatuses.filter((status) => status != "Accepted");
    },
  },
};
</script>

<style scoped>
.routers:first-of-type {
  margin-top: 0 !important;
}

.selectors {
  padding-top: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.description {
  font-style: italic;
}
.emergency {
  background-color: var(--emergency) !important;
}
.component {
  overflow: hidden;
  overflow-wrap: break-word;
  position: relative;
}
.borderLine {
  border-bottom: 1px solid black;
  height: 2rem;
  max-width: 72rem;
  margin: 0 auto;
}
/* .scroll {
  overflow-y: scroll !important;
  max-height: 78.3vh;
  background-color: transparent; 
  border: none;
  padding-top:0 !important;
  margin-top: 0 !important;
} */

.pending:after {
  content: "";
  position: absolute;
  margin: -0rem;
  right: -8rem;
  top: -8rem;
  width: 200px;
  height: 200px;
  transform: rotate(45deg);
  background-color: rgb(202, 139, 43) !important;
}
.closed:after {
  content: "";
  position: absolute;
  margin: -0rem;
  right: -8rem;
  top: -8rem;
  width: 200px;
  height: 200px;
  transform: rotate(45deg);
  background-color: var(--button) !important;
}
.open:after {
  content: "";
  position: absolute;
  margin: -0rem;
  right: -8rem;
  top: -8rem;
  width: 200px;
  height: 200px;
  transform: rotate(45deg);
  background-color: var(--button) !important;
}
</style>