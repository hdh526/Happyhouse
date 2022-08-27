<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <div class="container">
      <div role="group" class="mt-2">
        <label for="input-live-id">아이디</label>
        <b-form-input id="input-live-id" v-model="id" :state="idState" aria-describedby="input-live-help-id input-live-feedback-id" placeholder="Enter your id" trim></b-form-input>
        <!-- <span class="badge badge-danger mt-1" v-if="!availableId">이미 사용중인 아이디입니다.</span> -->
        <b-button variant="outline-success" @click="checkID">중복체크</b-button>
        <b-form-invalid-feedback id="input-live-feedback-id"> Enter at least 4 letters </b-form-invalid-feedback>
      </div>
      <div role="group" class="mt-2">
        <label for="input-live-password">비밀번호</label>
        <b-form-input
          id="input-live-password"
          type="password"
          v-model="password"
          :state="passwordState"
          aria-describedby="input-live-help-password input-live-feedback-password"
          placeholder="Enter your password"
          trim
        ></b-form-input>
        <b-form-invalid-feedback id="input-live-feedback-password"> Enter at least 4 letters </b-form-invalid-feedback>
      </div>
      <div role="group" class="mt-2">
        <label for="input-live-name">이름</label>
        <b-form-input id="input-live-name" v-model="name" :state="nameState" aria-describedby="input-live-help-name input-live-feedback-name" placeholder="Enter your name" trim></b-form-input>
        <b-form-invalid-feedback id="input-live-feedback-name"> Enter at least 4 letters </b-form-invalid-feedback>
      </div>
      <div role="group" class="mt-2">
        <label for="input-live-phone">전화번호</label>
        <b-form-input id="input-live-phone" v-model="phone" placeholder="Enter your phone number" trim></b-form-input>
      </div>
      <div class="mt-2">
        <b-button variant="outline-success" @click="regist">등록</b-button>
      </div>
    </div>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const memberStore = "memberStore";
export default {
  name: "MemberRegister",
  data() {
    return {
      id: "",
      password: "",
      name: "",
      phone: "",
      isDuplicateID: false,
    };
  },
  methods: {
    ...mapActions(memberStore, ["createUser", "idcheck"]),

    ...mapMutations(memberStore, ["CLEAR_IS_CHECK"]),
    regist() {
      if (this.id === "" || this.password === "" || this.name === "" || this.phone === "") {
        alert("모든 내용을 입력해주세요");
        // if(this.id===)
        return;
      }

      var user = {
        id: this.id,
        password: this.password,
        name: this.name,
        phone: this.phone,
      };

      this.createUser(user);
    },

    checkID() {
      console.log("중복 확인");
      this.idcheck(this.id).then(() => {
        if (this.isCheck) alert("사용가능");
        else {
          alert("사용 불가능");
          this.id = "";
        }
      });
      //console.log("result : " + this.isCheck);
    },

    // async checkDuplicate() {
    //   this.availableId = true;

    //   const response = await checkDuplicateId(this.id);
    //   if (!response.data) {
    //     this.availableId = false;
    //   } else {
    //     this.availableId = true;
    //   }
    // },
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isCheck"]),
    idState() {
      return this.id.length >= 4 ? true : false;
    },
    passwordState() {
      return this.password.length >= 4 ? true : false;
    },
    nameState() {
      return this.name.length >= 2 ? true : false;
    },
  },
  created() {
    this.CLEAR_IS_CHECK();
  },
};
</script>

<style></style>
