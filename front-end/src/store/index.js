import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import memberStore from "@/store/modules/memberStore.js";
import boardStore from "@/store/modules/boardStore.js";
import apartStore from "@/store/modules/apartStore.js";

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    memberStore,
    boardStore,
    apartStore,
  },

  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});

export default store;
