# marked

## 1. 소개

marked 는 markdown 을 파싱할 수 있는 javascript 라이브러리입니다

## 2. 사용법

2.1. 아래와 같은 mixin 을 작성합니다.

```javascript
// mixin/marked.js
import { marked } from "marked";

// 생략해도 되지만, 코드에 색상을 넣으려면 필수적인 플러그인인 highlight.js
// 설치는 yarn add highlight.js
import hljs from "highlight.js";
import "highlight.js/styles/atom-one-dark-reasonable.css";
marked.setOptions({
  renderer: new marked.Renderer(),
  highlight: function (code, lang) {
    const language = hljs.getLanguage(lang) ? lang : "plaintext";
    return hljs.highlight(code, { language }).value;
  },
  langPrefix: "hljs language-", // highlight.js css expects a top-level 'hljs' class.
  pedantic: false,
  gfm: true,
  breaks: false,
  sanitize: false,
  smartLists: true,
  smartypants: false,
  xhtml: false,
});

export default {
  data() {
    return {
      markdown: "",
    };
  },
  computed: {
    markdownHtml() {
      return marked(this.markdown);
    },
  },
};
```

2.2. 아래처럼 mixin을 입력받고 원본 마크다운을 markdown에 넣으면 html로 변환된 결과물이 예쁘게 뿌려집니다

```javascript
// sample.vue
<template>
  <div style="text-align: left">
    <div v-html="markdownHtml"></div>
  </div>
</template>

<script>
import markedMixin from "@/mixin/marked";
export default {
  mixins: [markedMixin],
  mounted() {
    this.markdown = "#hello world";
  },
};
</script>

<style></style>
```
