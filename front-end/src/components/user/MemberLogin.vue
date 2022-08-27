<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isLoginError">아이디 또는 비밀번호를 확인하세요.</b-alert>
            <b-form-group label="아이디:" label-for="id">
              <b-form-input id="id" v-model="user.id" required placeholder="아이디" @keyup.enter="confirm"></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="password">
              <b-form-input type="password" id="password" v-model="user.password" required placeholder="비밀번호" @keyup.enter="confirm"></b-form-input>
            </b-form-group>
            <b-button type="button" variant="primary" class="m-1" @click="confirm">로그인</b-button>
            <b-button type="button" variant="success" class="m-1" @click="movePage">회원가입</b-button>
            <b-button type="button" variant="secondary" class="m-1" @click="movePage2">비밀번호 찾기</b-button>
            <div id="naver_id_login"></div>
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  mounted() {
    var naver_id_login = new window.naver_id_login("9lbytc1dbrdcUCa6AHG_", "http://localhost:8080/login/naver");
    var state = naver_id_login.getUniqState();
    naver_id_login.setButton("white", 2, 40);
    naver_id_login.setDomain("http://localhost:8080/");
    naver_id_login.setState(state);
    naver_id_login.setPopup();
    naver_id_login.init_naver_id_login();
  },
  name: "MemberLogin",
  data() {
    return {
      user: {
        id: null,
        password: null,
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      console.log("methods");
      await this.userConfirm(this.user);
      console.log(sessionStorage);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "home" });
      }
    },
    movePage() {
      this.$router.push({ name: "MemberRegister" });
    },
    movePage2() {
      this.$router.push({ name: "MemberRegister" });
    },
  },
};
</script>

<style></style>
