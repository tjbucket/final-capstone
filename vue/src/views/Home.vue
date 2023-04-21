<template>
  <div class="home">
    <div class="component">
      <h1>Home</h1>
      <p>Welcome to our website!</p>
      <img id="logoimg" :src="require('@/assets/Dark Logo.png')" alt="logo" />
    </div>
    
  </div>
</template>

<script>
import workOrderService from "../../src/services/WorkOrderService";
import metricService from "../services/MetricService.js";
import UnitService from '../services/UnitService';


export default {
  name: "home",
  created() {
    if (this.$store.state.units.length == 0) {
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
      metricService.getSixMonthsRatings().then((response)=>{
        this.$store.commit("SET_RATINGS", response.data)
      });
    }
  },
};
</script>

<style scoped>
* {
  text-align: center;
  padding: 0.05rem;
}
/* .home {
    background-image: url("../assets/frontPage.jpg");
} */
.component {
  max-width: 50%;
}
#logoimg {
  margin-top: 1rem;
  max-width: 100%;
  height: auto;
}
</style>
