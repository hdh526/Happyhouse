<template>
  <div class="container">
    <div class="row">
      <div class="col-12">
        <div class="border-top my-3"></div>
        <div class="justify-content-around row">
          <h3>{{ board.title }}</h3>
        </div>
        <div class="border-top my-3"></div>
        <div class="row justify-content-around ustify-content-around align-items-center row">
          <div>작성자 {{ board.id }}</div>
          <div>{{ board.regtime }}</div>
          <div>조회수 {{ board.hit }}</div>
        </div>
        <div class="border-top my-3"></div>
        <div class="row-10 text-left" style="min-height: 300px" v-html="board.content"></div>
        <div class="border-top my-3"></div>
      </div>
      <div class="col-12 justify-content-center" v-if="checkSameUser()">
        <b-button class="mr-2" :to="{ name: 'BoardModify', params: `${board.no}` }">수정</b-button>
        <b-button class="mr-2" @click="deleteB">삭제</b-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const boardStore = "boardStore";
const memberStore = "memberStore";
export default {
  name: "BoardList",
  methods: {
    ...mapActions(boardStore, ["getBoard_list", "deleteBoard"]),

    deleteB() {
      this.deleteBoard(this.board.no);
    },

    checkSameUser() {
      console.log(this.userInfo.id);
      console.log(this.board.id);
      if (!this.isLogin) return false;
      if (this.board.writerName != this.userInfo.id) return false;
      return true;
    },
  },
  computed: {
    ...mapState(boardStore, ["board"]),
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    const pathName = new URL(document.location).pathname.split("/");
    const no = pathName[pathName.length - 1];
    this.getBoard_list(no);
  },
};
</script>

<style></style>
