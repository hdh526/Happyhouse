import axios from "axios";
import router from "@/router";

const boardStore = {
  namespaced: true,
  state: {
    boards: [],
    board: {},
    searchBoards: [],
  },
  getters: {},
  mutations: {
    SET_BOARDS: (state, boards) => {
      state.boards = boards;
    },
    SET_BOARD: (state, board) => {
      state.board = board;
    },
    SEARCH_TITLE: (state, boards) => {
      state.searchBoards = boards;
    },
    CREATE_BOARD: (state, board) => {
      state.boards.push(board);
    },
    CLEAR_BOARDS: (state) => {
      state.boards = [];
    },
    CLEAR_BOARD: (state) => {
      state.board = {
        id: "",
        content: "",
        title: "",
      };
    },
    CLEAR_SEARCH_BOARD: (state) => {
      state.searchBoards = [];
    },
  },
  actions: {
    getBoards({ commit }) {
      commit("CLEAR_BOARDS");
      const API_URL = `http://localhost:80/happyhouse/boardapi/board`;
      axios({
        url: API_URL,
        method: "get",
      })
        .then(({ data }) => {
          console.log(data);
          commit("SET_BOARDS", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getBoard_list({ commit }, no) {
      const API_URL = `http://localhost:80/happyhouse/boardapi/board/${no}`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          isWatch: true,
        },
      })
        .then(({ data }) => {
          data.content = data.content.replaceAll("\n", "<br />");
          console.log(data);
          commit("SET_BOARD", data);
        })
        .catch(() => {
          alert("게시글 조회 error 발생");
        });
    },
    getBoard({ commit }, no) {
      const API_URL = `http://localhost:80/happyhouse/boardapi/board/${no}`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          isWatch: false,
        },
      })
        .then(({ data }) => {
          console.log(data);
          commit("SET_BOARD", data);
        })
        .catch(() => {
          alert("게시글 조회 error 발생");
        });
    },
    createBoard: function ({ commit }, board) {
      const API_URL = `http://localhost:80/happyhouse/boardapi/board`;
      axios({
        url: API_URL,
        method: "post",
        params: {
          title: board.title,
          id: board.id,
          content: board.content,
        },
      })
        .then(() => {
          // mutation
          commit("CREATE_BOARD", board);
          alert("등록되었습니다.");
          router.push("/board");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateBoard: function ({ state }, board) {
      const API_URL = `http://localhost:80/happyhouse/boardapi/board/${board.no}`;
      axios({
        url: API_URL,
        method: "put",
        params: {
          title: board.title,
          id: board.id,
          content: board.content,
        },
      })
        .then(() => {
          alert("수정 완료!");
          let index;
          for (let i = 0; i < state.boards.length; i++) {
            if ((state.boards[i].no = board.no)) {
              index = i;
            }
          }
          state.boards[index] = board;
          //this.$set(state.users, index, user);
          router.push("/board");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteBoard: function ({ state }, no) {
      const API_URL = `http://localhost:80/happyhouse/boardapi/board/${no}`;
      axios({
        url: API_URL,
        method: "delete",
      })
        .then(() => {
          alert("삭제 완료!");
          let index;
          for (let i = 0; i < state.boards.length; i++) {
            if (state.boards[i].no === no) {
              index = i;
            }
          }
          state.boards.splice(index, 1);
          router.push("/board");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchTitle: function ({ commit }, title) {
      const API_URL = `http://localhost:80/happyhouse/boardapi/board/search`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          title,
        },
      })
        .then((res) => {
          commit("SEARCH_TITLE", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default boardStore;
