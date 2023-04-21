<template>
  <div id="register" class="component">
    <b-form @submit.prevent="register">
      <h1>Create Account</h1>
      <div id="alertBox" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
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
      <b-form-group class="form-input-group">
        <b-form-input
          type="password"
          id="confirmPassword"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
      </b-form-group>
      <b-button type="submit" size="lg" class="submitBtn">Create Account</b-button>
      <p>
        Already have an account? 
        <router-link :to="{ name: 'login' }"
          >Log in.</router-link
        >
      </p>
    </b-form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Passwords do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
#register{
  width: 80%;
  max-width: 350px;
  text-align: center;
  margin-top: 5rem;
}
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
a {
  color: var(--text);
}
.submitBtn{
  margin-bottom: 1rem;
}
#alertBox{
  margin-bottom: 0.5rem;
}
h1 {
  margin-bottom: 1rem;
}
</style>
