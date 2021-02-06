<template>
  <div class="game">
    <h2>tic tac toe</h2>
    <div id="game-header" style="margin: 20px">
      <div v-if="isFinished">
        {{ getWinner }} is winner
          <button style="margin-left: 20px" @click="reset()">retry</button>
      </div>
      <div v-else>
        {{ getTurn }} 's turn
      </div>
    </div>
    <table style="text-align: center; margin: auto">
      <tbody>
      <tr v-for="(row, rowindex) in getBoard" :key="rowindex">
        <td v-for="(col, colindex) in row" :key="colindex">
          <button style="height: 100px; width: 100px; text-align: center;"
                  @click="click(rowindex, colindex)">{{ col }}
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "Game",
  computed: {
    getBoard: function () {
      return this.$store.getters.getBoard;
    },
    isFinished: function () {
      return this.$store.getters.isFinished;
    },
    getWinner: function () {
      return this.$store.getters.getWinner;
    },
    getTurn: function () {
      return this.$store.getters.getTurn;
    }
  },
  methods: {
    click(x, y) {
      this.$store.commit('MARK_XY', {'x': x, 'y': y})
    },
    reset() {
      this.$store.commit('RESET');
    }
  }
}
</script>

<style scoped>

</style>