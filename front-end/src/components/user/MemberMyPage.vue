<template>
  <div class="container">
    <h2>회원 정보</h2>
    <b-form-group label="아이디" label-for="input-id">
      <b-form-input id="input-id" v-model="userInfo.id" placeholder="Enter id" readonly></b-form-input>
    </b-form-group>
    <b-form-group label="비밀번호" label-for="input-password">
      <b-form-input id="input-password" type="password" v-model="userInfo.password" placeholder="Enter password"></b-form-input>
    </b-form-group>
    <b-form-group label="이름" label-for="input-name">
      <b-form-input id="input-name" v-model="userInfo.name" placeholder="Enter name"></b-form-input>
    </b-form-group>
    <b-form-group label="전화번호" label-for="input-phone">
      <b-form-input id="input-phone" v-model="userInfo.phone" placeholder="Enter phone"></b-form-input>
    </b-form-group>
    <b-button variant="primary" class="mr-1" @click="modifyUser">정보수정</b-button>
    <b-button variant="danger" @click="resignUser">회원탈퇴</b-button>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  name: "MemberMyPage",
  methods: {
    ...mapActions(memberStore, ["updateUser", "deleteUser"]),
    modifyUser() {
      // this.$store.dispatch("updateUser", this.userInfo);
      this.updateUser(this.userInfo);
    },
    resignUser() {
      // this.$store.dispatch("deleteUser", this.userInfo.id);
      this.deleteUser(this.userInfo.id);
    },
  },
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  // created() {
  //   const id = this.$route.params.id;
  //   this.$store.dispatch("setUser", id);
  // },
};
</script>

<style></style>
