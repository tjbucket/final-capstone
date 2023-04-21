<template>
<div>
  <div class="component">
      <h1 id="welcome">Welcome {{this.$store.state.person.first}}</h1>
      <h2>Here are your pending approvals.</h2>
  </div>
  <tenant-approval class="component" v-for="(pending, index) in this.$store.state.pending" :key="pending.id" :index="index" />
</div>
</template>

<script>
import personService from "../services/PersonService";
import TenantApproval from "../components/TenantApproval.vue";
export default {
    created(){
        this.pendingAccounts();
    },
    methods:{
        pendingAccounts(){
            return personService.getPendingTenants().then(response => this.$store.commit('SET_PENDING', response.data));
        }
    },
    components: {
        TenantApproval
    }
}
</script>

<style scoped>

</style>