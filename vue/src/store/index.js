import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));
const currentPerson = JSON.parse(localStorage.getItem('person'))
if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}


export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    person: currentPerson || {},
    tenants: [],
    units:[],
    statuses:[],
    areas:[],
    pending:[],
    metric:{},
    ratings:[],
    activeWorkOrder: {
        id: "",
        unit: "",
        area: "",
        emergency: false,
        description: "",
        status: "closed",
        rating: "",
        comments: [],
        images: []
    },
    workOrders: [],
  },
  mutations: {
    RESET_ACTIVE_WORK_ORDER(state, workOrder){
      state.activeWorkOrder = workOrder;
    },
    SET_WORK_ORDERS(state, data) {
      state.workOrders = data;
    },
    SET_ACTIVE_WORK_ORDER(state, data){
      state.activeWorkOrder = data;
    },
    SET_UNIT_ARRAY(state, data){
      state.units = data;
    },
    SET_STATUS_ARRAY(state, data){
      state.statuses = data;
    },
    SET_AREA_ARRAY(state, data){
      state.areas = data;
    },
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('person');
      state.token = '';
      state.user = {};
      state.person = {};
      axios.defaults.headers.common = {};
    },
    SET_PERSON(state, person){
      state.person = person;
      localStorage.setItem('person', JSON.stringify(person));
    },
    SET_PENDING(state, data){
      state.pending = data;
    },
    SET_METRIC(state,data){
      state.metric = data;
    },
    SET_RATINGS(state,data){
      let ratings = JSON.parse(JSON.stringify(data.values))
      ratings.forEach(rating => Math.trunc(rating))
      state.ratings = ratings;
    }
  }
})
