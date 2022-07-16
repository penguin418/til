import Vue from "vue";
import App from "./App.vue";
import VueRouter from "vue-router";

Vue.config.productionTip = false;
Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: "/home",
    },
    {
      path: "/home",
      component: require("@/components/HomeView.vue").default,
    },
    {
      path: "/:dirName/:fileName",
      component: require("@/components/MarkdownViewer").default,
      props: (route) => ({
        dirName: route.params.dirName,
        fileName: route.params.fileName,
      }),
    },
  ],
});

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");
