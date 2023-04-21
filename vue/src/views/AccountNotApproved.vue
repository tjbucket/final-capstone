<template>
  <div class="component">
    <h1>Your account is currently under Maint-Tenants.</h1>
    <h4>Your property manager is still working on your account.</h4>
    <img :src="require('@/assets/scram.png')" alt="scram">
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.component{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
img {
  margin-top: 1rem;
  max-width: 100%;
  height: auto;
  border-radius: 1%;
  border: solid 0.1rem var(--highlight);
}
</style>>

