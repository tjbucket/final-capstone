import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import CreateWorkOrder from '../views/CreateWorkOrder'
import ViewWorkOrders from '../views/ViewWorkOrders'
import WorkOrder from '../views/WorkOrder'
import UserInfoPage from '../views/UserInfoPage'
import Property from '../views/Property'
import Units from '../views/Units'
import Tenants from '../views/Tenants'
import PropertyManagerApprove from "../views/PropertyManagerApprove";
import AccountNotApproved from "../views/AccountNotApproved";
import Personnel from "../views/Personnel"

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/viewWorkOrders',
      name: 'viewWorkOrders',
      component: ViewWorkOrders,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: '/workOrder/:id',
      name: 'workOrder',
      component: WorkOrder,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: '/createWorkOrder',
      name: 'createWorkOrder',
      component: CreateWorkOrder,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/units',
      name: 'units',
      component: Units,
      meta: {
        requiresAuth: true
      }

    },
    {
      path: '/tenants',
      name: 'tenants',
      component: Tenants,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/personnel',
      name: 'personnel',
      component: Personnel,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/propertyInfo',
      name: 'propertyInfo',
      component: Property,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/info",
      name: "info",
      component: UserInfoPage,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/pending",
      name: "pending",
      component: PropertyManagerApprove,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/waiting",
      name: "waiting",
      component: AccountNotApproved,
      meta: {
        requiresAuth: true
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
