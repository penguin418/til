import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Game',
    component: () => import(/* webpackChunkName: "about" */ '../views/Game.vue')
  },
  {
    path: '/log',
    name: 'Log',
    component: () => import(/* webpackChunkName: "about" */ '../views/Log.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
