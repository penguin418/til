<template>
  <div style="text-align: left">
    <pre>
    vue-router

    1. 소개
    vue-router는 라우팅을 위해 사용됩니다

    2. 사용법
    2.1. 라우트 매핑
    vue2.6 에서는 다음과 같이 사용할 수 있습니다
    {{ vueRouterSetting }}

    2.2. 라우트
    {{ vurRouterUsage }}
  </pre
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      vueRouterSetting: `
// main.js 상단
import VueRouter from "vue-router";

// main.js 내부
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
      component: require("./components/HomeView.vue").default,
    },
  ],
});
// main.js Vue 선언문
new Vue({
  // ...
  router,
  render: (h) => h(App),
}).$mount("#app");
`,
      vurRouterUsage: `
<!-- App.vue -->
<template>
  <div id="app">
    <!-- routing된 내용이 렌더되는 영역입니다 -->
    <router-view></router-view>

    <!-- to props는 연결될 페이지를 지정합니다 -->
    <router-link to="/home"></router-link>

    <!-- 관련 메서드와 속성은 $router 를 통해 접근할 수 있습니다. 다음은 위와 동일한 기능을 하는 코드입니다. -->
    <a @click="$router.push('/home')" ></a>
  </div>
</template>
`,
    };
  },
};
</script>

<style></style>
