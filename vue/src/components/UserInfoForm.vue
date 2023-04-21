<template>
  <div class="component">
    <h1>Welcome to Maint-Tenants</h1>
    <h3>
      We noticed this is the first time you've logged in. Please fill out the
      following information.
    </h3>
    <b-form>
      <b-form-group class="name">
        <b-form-input
          placeholder="First name"
          id="first-name"
          type="text"
          v-model="info.first"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group class="name">
        <b-form-input
          placeholder="Last name"
          id="last-name"
          type="text"
          v-model="info.last"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group class="email">
        <b-form-input
          placeholder="Email address"
          id="email"
          type="email"
          v-model="info.email"
          required
        ></b-form-input>
      </b-form-group>
      <b-button type="button" class="submitBtn" @click="submitPersonalInfo"
        >Submit Info</b-button
      >
    </b-form>
  </div>
</template>

<script>
import personService from "../services/PersonService";
export default {
  data() {
    return {
      info: {
        userId: "",
        first: "",
        last: "",
        email: "",
      },
    };
  },
  methods: {
    submitPersonalInfo() {
      personService.setPersonalInfo(this.info).then((response) => {
        if (response.status == 201) {
          this.$router.push({ name: "waiting" });
        }
      });
    },
  },
  created() {
    this.info.userId = this.$store.state.user.id;
  },
};
</script>

<style scoped>
.component > * {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.component > *,
.component > * > * {
  margin-bottom: 0.25rem !important;
}
.component {
  text-align: center;
}
.component > * > * {
  width: 50%;
}
@media (max-width: 800px) {
  .component > * > * {
    width: 80%;
  }
}
</style>