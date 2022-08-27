<template>
  <b-row class="mb-1" id="contents-wrap">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group id="title-group" label="제목:" label-for="title">
          <b-form-input id="title" v-model="board.title" type="text" required placeholder="제목 입력..."></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea id="content" v-model="board.content" placeholder="내용 입력..." rows="10" max-rows="15"></b-form-textarea>
        </b-form-group>

        <b-button type="submit" variant="primary" class="m-1" v-if="this.type === 'write'">글작성</b-button>
        <b-button type="submit" variant="primary" class="m-1" v-else>글수정</b-button>
        <b-button type="reset" variant="danger" class="m-1">초기화</b-button>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
// import http from "@/api/http";
const boardStore = "boardStore";
const memberStore = "memberStore";
export default {
  name: "BoardInputItem",
  props: {
    type: { type: String },
  },
  created() {
    this.CLEAR_BOARD();
    this.board.id = this.userInfo.id;
    if (this.type === "modify") {
      const pathName = new URL(document.location).pathname.split("/");
      const no = pathName[pathName.length - 1];
      this.getBoard(no);
      // this.isUserid = true;
      this.board.content = this.board.content.replaceAll("<br />", "\n");
    } else {
      this.board.content = "";
      this.board.title = "";
      this.board.no = null;
      this.board.hit = 0;
      this.board.regtime = "";
    }
  },
  computed: {
    ...mapState(boardStore, ["board"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(boardStore, ["createBoard", "updateBoard", "getBoard"]),
    ...mapMutations(boardStore, ["CLEAR_BOARD"]),
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.board.id && ((msg = "작성자 입력해주세요"), (err = false), this.$refs.id.focus());
      err && !this.board.title && ((msg = "제목 입력해주세요"), (err = false), this.$refs.title.focus());
      err && !this.board.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "write" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.board.no = 0;
      this.board.title = "";
      this.board.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registArticle() {
      // http
      //   .post(`/board`, {
      //     id: this.article.id,
      //     title: this.article.title,
      //     content: this.article.content,
      //   })
      //   .then(({ data }) => {
      //     let msg = "등록 처리시 문제가 발생했습니다.";
      //     if (data === "success") {
      //       msg = "등록이 완료되었습니다.";
      //     }
      //     alert(msg);
      //     this.moveList();
      //   });
      this.createBoard(this.board);
    },
    modifyArticle() {
      //   http
      //     .put(`/board/${this.article.no}`, {
      //       no: this.article.no,
      //       id: this.article.id,
      //       title: this.article.title,
      //       content: this.article.content,
      //     })
      //     .then(({ data }) => {
      //       let msg = "수정 처리시 문제가 발생했습니다.";
      //       if (data === "success") {
      //         msg = "수정이 완료되었습니다.";
      //       }
      //       alert(msg);
      //       // 현재 route를 /list로 변경.
      //       this.$router.push({ name: "BoardList" });
      //     });
      this.updateBoard(this.board);
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
};
</script>

<style></style>
