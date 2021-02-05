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
    finished: false
  },
  mutations: {
    // this.$store.commit('뮤테이션이름', { 인자 } )로 실행하는 동기 함수
    MARK_XY (state, position) {
      if (state.finished) // 이미 종료된 게임
          return;
      if (state.board[position.x][position.y] !== ' ') // 놓을 수 없는 자리
          return;
      state.board[position.x][position.y] = state.turn;
      let b = state.board
      let t = state.turn
      state.finished = ((b[0][0] == t) && (b[0][1] == t) && (b[0][2] == t))
          || ((b[1][0] == t) && (b[1][1] == t) && (b[1][2] == t))
          || ((b[2][0] == t) && (b[2][1] == t) && (b[2][2] == t))
          || ((b[0][0] == t) && (b[1][0] == t) && (b[2][0] == t))
          || ((b[0][1] == t) && (b[1][1] == t) && (b[2][1] == t))
          || ((b[0][2] == t) && (b[1][2] == t) && (b[2][2] == t))
          || ((b[0][0] == t) && (b[1][1] == t) && (b[2][2] == t))
          || ((b[2][2] == t) && (b[1][1] == t) && (b[0][0] == t));
      if (! state.finished)
        if (state.turn === 'X') state.turn = 'O'; else state.turn = 'X';
    },
    RESET (state){
      state.finished = false;
      state.log.push(state.turn)

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
      return state.log.map(el => el + ' is win');
    },
    getWinner (state) {
      return state.turn;
    },
    isFinished (state) {
      return state.finished;
    }
  },
  modules: {
  }
})
