<template>
  <div>
    <work-order-details v-bind:order-id="parseInt($route.params.id)" />
    <comment-thread />
  </div>
</template>

<script>
import WorkOrderDetails from "../components/WorkOrderDetails.vue";
import CommentThread from "../components/CommentThread.vue"
export default {
  components: { WorkOrderDetails, CommentThread},
};
</script>

<style>
</style>