<template>
  <div class="game">
    <h2>tic tac toe</h2>
    <table style="text-align: center; margin: auto">
      <tbody>
      <tr v-for="(row, rowindex) in getBoard" :key="rowindex">
        <td v-for="(col, colindex) in row" :key="colindex">
          <button style="height: 100px; width: 100px; text-align: center;"
                  @click="click(rowindex, colindex)">{{ col }}</button>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if=""
    {{ getBoard }}
  </div>
</template>

<script>
export default {
  name: "Game",
  computed: {
    getBoard: function() {
      return this.$store.getters.getBoard;
    }
  },
  methods:{
    click(x, y){
      this.$store.commit('MARK_XY', {'x': x, 'y': y})
    }
  }
}
</script>

<style scoped>

</style>