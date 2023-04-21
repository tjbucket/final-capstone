<template>
  <div>
    <div class="component">
      <!-- Container with two routes that go tenant and unit info views, only visible to property employees -->
      <router-link :to="{ name: 'tenants' }"
        ><b-button class="goBackBtn navBtn"
          >View Current Tenants</b-button
        ></router-link
      >
      <router-link :to="{ name: 'personnel' }"
        ><b-button
          v-if="this.$store.state.person.role == 'Manager'"
          class="goBackBtn navBtn"
          >View Personnel</b-button
        ></router-link
      >
      <router-link :to="{ name: 'units' }"
        ><b-button class="goBackBtn navBtn"
          >View Unit Information</b-button
        ></router-link
      >
    </div>
    <month-dashboard/>
    <completion-times class="component"/>
    <ratings class="component" />
  </div>
</template>

<script>
import CompletionTimes from "../components/CompletionTimes.vue";
import MonthDashboard from "../components/MonthDashboard.vue";
import Ratings from "../components/Ratings.vue";
export default {
  components: { CompletionTimes, MonthDashboard, Ratings },
};
</script>

<style scoped>
.component {
  display: flex;
  align-items: center;
  justify-content: space-around;
}
@media (max-width: 500px) {
  .component {
    flex-direction: column !important;
    gap: 0.25rem !important;
  }
  .component > *{
    display: flex;
    justify-content: center;
    width: 75% !important;
  }
  .component > * > *{
    width: 100% !important;
  }
}
a{
  text-decoration: none !important;
}
</style>