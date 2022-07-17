# babel

# 1. 소개

## 바벨은 자바스크립트 트랜스 컴파일러입니다

javascript는 스크립트 언어로 딱히 컴파일러가 필요한 언어가 아니지만, 여러종류 + 다양한버전의 브라우저/Node/GraalVM 등에서 실행됩니다. 이때 각각의 실행환경마다 지원하는 버전이 다르므로, 모든 환경에서 작동하는 코드를 만들려면 가장 모든 환경이 지원하는 오래된 문법만 사용해야 했습니다. 바벨은 작성한 자바스크립트 (또는 타입스크립트)를 실행환경에 맞게 바꿔주어 개발자들이 실행환경을 덜 신경쓰고 개발할 수 있게 해주었습니다. 이렇게 소스코드 형태는 유지하면서도 다른 환경에서 돌아갈 수 있게 수정해주는 babel같은 부류를 트랜스 컴파일러라고 부릅니다.

## 바벨은 es6문법을 es5에 맞게 수정해주는 6to5에서 시작했지만 현재는 polyfill과 최적화까지 수행합니다.

- polyfill이란?

  jquery 처럼 모든 브라우저에서 동작하는 단일 api를 제공하기 위해 각각의 브라우저에 맞는 구현을 제공하는 코드입니다. 현재는 모든 브라우저가 웹 표준을 구현하기 위해 노력하고 있어 대부분의 브라우저에서 동일한 api가 제공되므로 현재는 점차 필요성이 떨어지고 있습니다.

# 2. 사용법

## 2.1. @으로 폴더 접근하기

babel은 vue를 브라우저에서 돌아가는 javascript로 변환해줍니다. 덕분에 우리는 약간의 설정을 통해 vue에서 다른 파일을 더 쉽게 참조할 수 있습니다.

먼저 플러그인을 설치해야 합니다.

```bash
yarn add "babel-plugin-module-resolver"
```

그 다음 babel설정 파일에서 src 폴더를 @로 접근할 수 있도록 설정합니다.

```javascript
// babel.config.js
const path = require("path");
module.exports = {
  presets: ["@vue/cli-plugin-babel/preset"],
  plugins: [
    [
      "module-resolver",
      {
        alias: {
          "@": path.join(__dirname, "src"),
        },
      },
    ],
  ],
};
```
