<template>
  <div class="component" id="login">
    <b-form @submit.prevent="login">
      <h1>Welcome</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <b-form-group class="form-input-group">
        <b-form-input
          type="text"
          id="username"
          placeholder="Username"
          v-model="user.username"
          required
        />
      </b-form-group>
      <b-form-group class="form-input-group">
        <b-form-input
          type="password"
          id="password"
          placeholder="Password"
          v-model="user.password"
          required
        />
      </b-form-group>
      <b-button id="submit" type="submit" size="lg" class="submitBtn"
        >Sign in</b-button
      >
      <p>
        Need an account?
        <router-link :to="{ name: 'register' }">Sign up.</router-link>
      </p>
    </b-form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import personService from "../services/PersonService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            personService.getPersonalDetails().then((response) => {
              this.$store.commit("SET_PERSON", response.data);
              if (this.$store.state.person.id === 0) {
                this.$router.push("/info");
              } else if((this.$store.state.person.role == undefined || this.$store.state.person.role == 'Inactive') && this.$store.state.person.unit == 0){
                this.$router.push("/waiting");
              } else {
                this.$router.push("/");
              }
            });
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
#login {
  width: 80%;
  max-width: 350px;
  text-align: center;
  margin-top: 5rem;
}
h1 {
  margin-bottom: 1rem;
}
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
#submit {
  margin-bottom: 1rem;
}
a {
  color: var(--text);
}
</style>