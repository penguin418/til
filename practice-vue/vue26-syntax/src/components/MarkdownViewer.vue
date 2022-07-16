<template>
  <div style="text-align: left">
    <div v-html="markdownHtml"></div>
  </div>
</template>

<script>
import markedMixin from "@/mixin/marked";
export default {
  name: "MarkdownViewer",
  mixins: [markedMixin],
  mounted() {
    const markdownFile = require(`@/assets/${this.dirName}/${this.fileName}.md`);
    this.markdown = markdownFile.default;
  },
};
</script>

<style></style>
