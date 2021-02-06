<template>
  <div class="log">
    <ul>
      <li v-for="(log, index) in getLog" :key="index"
      style=" list-style-position: inside;">
        {{ log }}
      </li>
    </ul>
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