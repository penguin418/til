<template>
  <div class="log">
    {{ getLog }}
  </div>
</template>

<script>
export default {
  name: "Log",
  computed:{
    getLog() {
      return this.$store.getters.getLog;
    }
  }
}
</script>

<style scoped>

</style>