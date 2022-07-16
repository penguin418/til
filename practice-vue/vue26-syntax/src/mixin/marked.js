import { marked } from "marked";
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
  props: ["dirName", "fileName"],
  computed: {
    markdownHtml() {
      return marked(this.markdown);
    },
  },
};
