# vue26-syntax

## 1. project

### 1.1. 생성

```sh
vue create app .
```

### 1.2. project 세팅

```sh
yarn install
```

### 1.3. dev 모드

```sh
yarn serve
```

### 1.4. 컴파일

```sh
yarn build
```

### 1.5. 린트

```sh
yarn lint
```

## 2. 레이아웃 구성

### 2.1. 라우터 설치(vue2.6의 최신버전은 3.5.3)

```sh
yarn add vue-router@3.5.3
```

### 2.2. 라우터 코드 추가

```javascript
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
```
