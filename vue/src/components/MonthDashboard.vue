<template>
  <div v-if="showDash" class="dashboard component">
    <h1 class="text-center">So far this month...</h1>
    <ul>
        <li>Your average work order completion time has been <b>{{ this.currentMonthAvg }}</b> hours. {{this.sixAboveOrBelow}}</li>
        <li>Your average rating has been <b>{{ Math.round((this.metric.values[0] + Number.EPSILON) * 10) / 10 }}</b> stars.</li>
        <li>Your team has taken care of <b>{{Math.trunc(this.metric.values[2])}}</b> work orders.</li>
    </ul>
  </div>
</template>

<script>
import MetricService from "../services/MetricService";
export default {
  data() {
    return {
      sixMonthAvgHr: 0,
      metric: {},
      showDash: false,
      currentMonthAvg: 0
    };
  },
  computed: {
    sixAboveOrBelow() {
      return this.sixMonthAvgHr >= this.currentMonthAvg ? 'This is faster than the previous 6 months!' : 'This is slower than the previous 6 months!';
    }
  },
  created() {
    MetricService.getThisMonth().then((response) => {
      this.metric = response.data;
      this.currentMonthAvg = Math.round((this.metric.values[1] + Number.EPSILON) * 10) / 10;
      this.sixMonthAvgHr = Math.round((this.$store.state.metric.values.reduce((a, b) => a + b, 0)) / 6, 1);
      this.showDash = true;
    });
  },
};
</script>

<style scoped>
.dashboard{
    display: flex;
    flex-direction: column;
}
h4{
    align-self: flex-end;
}
</style>