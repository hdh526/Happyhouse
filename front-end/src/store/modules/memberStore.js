import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
import { findById } from "../../api/member";
import axios from "axios";
import router from "@/router";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    users: [],
    isCheck: false,
    // user: {
    //   id: String,
    //   password: String,
    //   name: String,
    //   phone: String,
    //   country: String,
    //   city: String,
    //   dong: String,
    // },
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    CREATE_USER: function (state, userInfo) {
      state.users.push(userInfo);
    },
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_IS_CHECK: (state, isCheck) => {
      state.isCheck = isCheck;
    },
    CLEAR_IS_CHECK: (state) => {
      state.isCheck = false;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      console.log("memberStore.js");
      await login(user, (response) => {
        if (response.data.message === "success") {
          let token = response.data["access-token"];
          commit("SET_IS_LOGIN", true);
          commit("SET_IS_LOGIN_ERROR", false);
          sessionStorage.setItem("access-token", token);
        } else {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
        }
      });
    },
    idcheck: async function ({ commit }, id) {
      commit("CLEAR_IS_CHECK");
      console.log(id);
      const API_URL = `http://localhost:80/happyhouse/user/check/${id}`;
      await axios({
        url: API_URL,
        method: "get",
      })
        .then((data) => {
          if (data.data["message"] === "success") {
            commit("SET_IS_CHECK", true);
          } else {
            commit("SET_IS_CHECK", false);
            return false;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(decode_token.id, (response) => {
        if (response.data.message === "success") {
          commit("SET_USER_INFO", response.data.userInfo);
        } else {
          console.log("유저 정보 없음!!");
        }
      });
    },
    createUser: function ({ commit }, userInfo) {
      const API_URL = `http://localhost:80/happyhouse/user/regist`;
      console.log(userInfo);
      axios({
        url: API_URL,
        method: "post",
        params: userInfo,
      })
        .then(() => {
          commit("CREATE_USER", userInfo);
          alert("등록되었습니다.");
          router.push("/user");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateUser: function ({ state }, userInfo) {
      const API_URL = `http://localhost:80/happyhouse/user/update`;
      console.log(userInfo);
      axios({
        url: API_URL,
        method: "put",
        params: userInfo,
      })
        .then(() => {
          alert("수정이 완료되었습니다.");
          let index;
          for (let i = 0; i < state.users.length; i++) {
            if ((state.users[i].id = userInfo.id)) {
              index = i;
            }
          }
          state.users[index] = userInfo;
          router.push("/");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteUser: function ({ state }, id) {
      const API_URL = `http://localhost:80/happyhouse/user/delete/${id}`;
      axios({
        url: API_URL,
        method: "delete",
      })
        .then(() => {
          alert("삭제가 완료되었습니다.");
          let index;
          for (let i = 0; i < state.users.length; i++) {
            if (state.users[i].id === id) {
              index = i;
            }
          }
          state.users.splice(index, 1);
          state.userInfo = null;
          router.push("/");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default memberStore;
