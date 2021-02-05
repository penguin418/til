import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)


export default new Vuex.Store({
  state: {
    // 저장소, computed
    board: {
      0: { 0: ' ', 1: ' ', 2: ' '},
      1: { 0: ' ', 1: ' ', 2: ' '},
      2: { 0: ' ', 1: ' ', 2: ' '},
    },
    log: [],
    turn: 'O',
    Finished: false
  },
  mutations: {
    // this.$store.commit('뮤테이션이름', { 인자 } )로 실행하는 동기 함수
    MARK_XY (state, position) {
      state.board[position.x][position.y] = state.turn;
      let b = state.board
      function checkRow(c1, c2, c3, turn){
        return (c1.charAt(0) === turn) && (c2.charAt(0) === turn) && (c3.charAt(0) === turn);
      }
      function checkWin(turn){
        return checkRow(b[0][0], b[0][1], b[0][2], turn) ||
            checkRow(b[1][0], b[1][1], b[1][2], turn) ||
            checkRow(b[2][0], b[2][1], b[2][2], turn) ||
            checkRow(b[0][0], b[1][0], b[2][0], turn) ||
            checkRow(b[0][1], b[1][1], b[2][1], turn) ||
            checkRow(b[0][2], b[1][2], b[2][2], turn) ||
            checkRow(b[0][0], b[1][1], b[2][2], turn) ||
            checkRow(b[0][2], b[1][1], b[0][2], turn);
      }
      state.finished = checkWin(this.turn);
      if (! state.finished)
        if (state.turn === 'X') state.turn = 'O'; else state.turn = 'X';
    },
    RESET (state){
      state.Finished = false;
      for (var i=0; i<3; i++)
        for (var j=0; j<3; j++)
          state.board[i][j] = ' '
    }
  },
  actions: {
    // this.$store.commit('뮤테이션이름', { 인자 } )로 실행하는 동기+비동기 함수
    // axios 등은 여기서 사용
  },
  getters: {
    // get 함수, computed
    getBoard (state) {
      return state.board;
    },
    getLog (state) {
      console.log('logic')
      return state.log;
    },
    getWinner (state) {
      return { game: state.Finished, winner: state.turn }
    }
  },
  modules: {
  }
})
