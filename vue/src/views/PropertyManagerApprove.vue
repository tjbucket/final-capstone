<template>
  <div>
      <property-manager-list v-if="this.$store.state.person.role == 'Manager'" />
      <h1 v-else>You shouldn't be here.</h1>
  </div>
</template>

<script>
import PropertyManagerList from "../components/PropertyManagerList.vue"
export default {
    components: {PropertyManagerList}
}
</script>

<style scoped>

</style>