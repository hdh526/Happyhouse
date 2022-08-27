import axios from "axios";
import router from "@/router";
import createPersistedState from "vuex-persistedstate";

const apartStore = {
  namespaced: true,
  state: {
    currentLocation: {},
    sidos: [{ value: "", text: "선택하세요" }],
    guguns: [{ value: "", text: "선택하세요" }],
    dongs: [{ value: "", text: "선택하세요" }],
    sido: null,
    sidoCode: "",
    gugun: null,
    gugunCode: "",
    dong: null,
    dongCode: "",
    houses: null,
    house: null,
    housedeals: null,
    housedeal: null,
    optionYear: [],
    optionAmout: [],
  },
  getters: {
    housesCnt: function (state) {
      return state.houses.length;
    },
    dealCnt: function (state) {
      return state.housedeals.length;
    },
  },
  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: "", text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: "", text: "선택하세요" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: "", text: "선택하세요" }];
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses = null;
    },
    CLEAR_DEAL_LIST: (state) => {
      state.housedeals = null;
    },
    SET_SIDO: (state, sidoCode) => {
      state.sidoCode = sidoCode;
      state.sidos.forEach((element) => {
        if (element.value == sidoCode) {
          state.sido = element.text;
          return;
        }
      });
    },
    SET_GUGUN: (state, gugunCode) => {
      state.gugunCode = gugunCode;
      state.guguns.forEach((element) => {
        if (element.value == gugunCode) {
          state.gugun = element.text;
          return;
        }
      });
    },
    SET_DONG: (state, dongCode) => {
      state.dongCode = dongCode;
      state.guguns.forEach((element) => {
        if (element.value == dongCode) {
          state.dong = element.text;
          return;
        }
      });
    },
    SET_APT_NAME: (state, aptName) => {
      state.aptName = aptName;
    },
    CLEAR_SIDO: (state) => {
      state.sido = null;
      state.sidoCode = "";
    },
    CLEAR_GUGUN: (state) => {
      state.gugun = null;
      state.gugunCode = "";
    },
    CLEAR_DONG: (state) => {
      state.dong = null;
      state.dongCode = "";
    },
    CLEAR_APT_NAME: (state) => {
      state.aptName = null;
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_DEAL_LIST: (state, housedeals) => {
      state.housedeals = housedeals;
    },
    SET_DEAL_SELECT: (state, housedeal) => {
      state.housedeal = housedeal;
    },

    CLEAR_DEAL_SELECT: (state) => {
      state.housedeal = [];
    },
    SET_CURRENT: (state, current) => {
      state.currentLocation = current;
    },
    SET_OPTION_YEAR: (state, deal) => {
      state.optionYear.push({ value: deal.dealYear, text: deal.dealYear });
    },
    CLEAR_OPTION_YEAR: (state) => {
      state.optionYear = [{ value: "All", text: "전체" }];
    },
    SET_OPTION_AREA: (state, deal) => {
      state.optionArea.push({ value: deal.area, text: deal.area });
    },
    CLEAR_OPTION_AREA: (state) => {
      state.optionArea = [{ value: "All", text: "전체" }];
    },
  },
  actions: {
    getSido: ({ commit }) => {
      commit("CLEAR_SIDO");
      commit("CLEAR_GUGUN");
      commit("CLEAR_DONG");
      commit("CLEAR_GUGUN_LIST");
      commit("CLEAR_DONG_LIST");
      commit("CLEAR_SIDO_LIST");
      const API_URL = `http://localhost:80/happyhouse/address/sido`;
      axios({
        url: API_URL,
        method: "get",
      })
        .then(({ data }) => {
          commit("SET_SIDO_LIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getGugun: ({ commit }, sidoCode) => {
      commit("CLEAR_GUGUN");
      commit("CLEAR_DONG");
      commit("CLEAR_GUGUN_LIST");
      commit("CLEAR_DONG_LIST");
      commit("SET_SIDO", sidoCode);
      const API_URL = `http://localhost:80/happyhouse/address/gugun`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          sido: sidoCode,
        },
      })
        .then(({ data }) => {
          commit("SET_GUGUN_LIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getDong: ({ commit }, gugunCode) => {
      commit("CLEAR_DONG");
      commit("CLEAR_DONG_LIST");
      commit("SET_GUGUN", gugunCode);
      const API_URL = `http://localhost:80/happyhouse/address/dong`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          gugun: gugunCode,
        },
      })
        .then(({ data }) => {
          data.forEach((element) => {
            if (element.dongName == null) {
              element.dongName = "전체";
            }
          });
          commit("SET_DONG_LIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchHouseList: function ({ commit, state }, dongCode) {
      console.log("1");
      commit("CLEAR_HOUSE_LIST");
      commit("SET_DONG", dongCode);
      commit("CLEAR_APT_NAME");
      const API_URL = `http://localhost:80/happyhouse/address/apt`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          dong: dongCode,
          lat: state.currentLocation.lat,
          lon: state.currentLocation.lng,
          base: "distance",
          sort: "오름차순",
        },
      })
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
          console.log(data);
          router.push("/apart");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchHouseListN: function ({ commit, state }, param) {
      console.log("2");
      const API_URL = `http://localhost:80/happyhouse/address/aptN`;
      console.log("In stroe");
      console.log(param);
      commit("SET_DONG", param.dongCode);
      commit("SET_APT_NAME", param.aptName);
      axios({
        url: API_URL,
        method: "get",
        params: {
          sido: state.sidoCode,
          gugun: state.gugunCode,
          dong: state.dongCode,
          apt: state.aptName,
          lat: state.currentLocation.lat,
          lon: state.currentLocation.lng,
          base: "distance",
          sort: "오름차순",
        },
      })
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
          console.log(data);
          router.push("/apart");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchHouseListC: function ({ commit, state }, dongCode) {
      console.log("1");
      commit("CLEAR_HOUSE_LIST");
      commit("SET_DONG", dongCode);
      commit("CLEAR_APT_NAME");
      const API_URL = `http://localhost:80/happyhouse/address/apt`;
      axios({
        url: API_URL,
        method: "get",
        params: {
          dong: dongCode,
          lat: state.currentLocation.lat,
          lon: state.currentLocation.lng,
          base: "distance",
          sort: "오름차순",
        },
      })
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
          console.log(data);
          router.go(router.currentRoute);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchHouseListNC: function ({ commit, state }, param) {
      console.log("2");
      const API_URL = `http://localhost:80/happyhouse/address/aptN`;
      console.log("In stroe");
      console.log(param);
      commit("SET_DONG", param.dongCode);
      commit("SET_APT_NAME", param.aptName);
      axios({
        url: API_URL,
        method: "get",
        params: {
          sido: state.sidoCode,
          gugun: state.gugunCode,
          dong: state.dongCode,
          apt: state.aptName,
          lat: state.currentLocation.lat,
          lon: state.currentLocation.lng,
          base: "distance",
          sort: "오름차순",
        },
      })
        .then(({ data }) => {
          commit("SET_HOUSE_LIST", data);
          console.log(data);
          router.go(router.currentRoute);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    searchDeals: async function ({ commit, state }, idx) {
      console.log("searchDeals");
      console.log(idx);
      commit("CLEAR_DEAL_LIST");
      commit("CLEAR_OPTION_YEAR");
      commit("CLEAR_OPTION_AREA");
      commit("SET_DETAIL_HOUSE", state.houses[idx]);
      console.log(state.house);
      const API_URL = `http://localhost:80/happyhouse/address/deal`;
      await axios({
        url: API_URL,
        method: "get",
        params: {
          aptCode: state.house.aptCode,
        },
      })
        .then(({ data }) => {
          commit("SET_DEAL_LIST", data);
          commit("SET_DEAL_SELECT", data);
          for (var d = 0; d < data.length; d++) {
            var isContain = false;
            for (var i = 0; i < state.optionYear.length; i++) {
              if (state.optionYear[i].text == data[d].dealYear) {
                isContain = true;
                break;
              }
            }
            if (!isContain) commit("SET_OPTION_YEAR", data[d]);
            isContain = false;
            for (i = 0; i < state.optionArea.length; i++) {
              if (state.optionArea[i].text == data[d].area) {
                isContain = true;
                break;
              }
            }
            if (!isContain) commit("SET_OPTION_AREA", data[d]);
          }
          console.log(data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  modules: {},
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
};

export default apartStore;
