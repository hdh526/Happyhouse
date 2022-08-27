<template>
  <div class="header">
    <b-navbar class="head" toggleable="lg" type="dark">
      <b-navbar-brand to="/"><img src="@/assets/home.png" width="50px" alt="HaeBang" /> HaeBang</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/apart">실시간 거래 조회</b-nav-item>
          <b-nav-item to="/board">QnA</b-nav-item>
          <!-- <b-nav-item v-if="getUser" to="/mypage">마이페이지</b-nav-item>
          <b-nav-item v-else to="/login">로그인</b-nav-item>
          <b-nav-item v-if="getUser" @click="logout">로그아웃</b-nav-item>
          <b-nav-item v-else to="/signup">회원가입</b-nav-item> -->
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-if="userInfo">
          <b-nav-item class="align-self-center">{{ userInfo.name }}({{ userInfo.id }})님 환영합니다.</b-nav-item>
          <b-nav-item class="align-self-center" to="/user/mypage">MyPage</b-nav-item>
          <b-nav-item class="link align-self-center" @click.prevent="onClickLogout">로그아웃</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto" v-else>
          <!-- <b-nav-item-dropdown right> -->
          <!-- <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template> -->
          <!-- <b-dropdown-item href="#"> -->
          <!-- <router-link :to="{ name: 'MemberRegister' }" class="link"><b-icon icon="person-circle"></b-icon> 회원가입</router-link> -->
          <b-nav-item to="/user/signup">회원가입</b-nav-item>
          <b-nav-item to="/user/signin">로그인</b-nav-item>
          <!-- </b-dropdown-item> -->
          <!-- <b-dropdown-item href="#"> -->
          <!-- <router-link :to="{ name: 'signIn' }" class="link"><b-icon icon="key"></b-icon> 로그인</router-link> -->
          <!-- </b-dropdown-item> -->
          <!-- </b-nav-item-dropdown> -->
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <div></div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "HeaderNav",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scoped>
.head {
  background-color: #6667ab;
}
</style>
