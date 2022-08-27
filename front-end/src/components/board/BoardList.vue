<template>
  <div class="container">
    <h2>Q &amp; A</h2>
    <div v-if="isSearchMode">
      <div v-if="countSearchBoard">
        <b-table-simple hover striped responsive :items="searchBoards" :per-page="perPage" :current-page="currentPage">
          <b-thead>
            <b-tr>
              <b-th>번호</b-th>
              <b-th>제목</b-th>
              <b-th>작성자</b-th>
              <b-th>조회수</b-th>
              <b-th>작성일자</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <b-tr v-for="(board, index) in pageSearchBoardList" :key="index">
              <b-td>{{ board.no }}</b-td>
              <b-td
                ><b-link class="board-link" :to="`/${board.no}`">{{ board.title }}</b-link></b-td
              >
              <b-td>{{ board.id }}</b-td>
              <b-td>{{ board.hit }}</b-td>
              <b-td>{{ board.regtime }}</b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>
        <b-pagination v-model="currentPage" :total-rows="countBoard" :per-page="perPage" align="center"></b-pagination>
      </div>
      <div v-else>검색 내역이 존재하지 않습니다.</div>
      <div class="row justify-content-center align-items-center">
        <b-form-input v-model="searchTxt" placeholder="검색어를 입력하세요" class="col-6"></b-form-input>
        <b-button variant="dark" @click="searchTitle" class="m-1">검색</b-button>
        <b-button variant="dark" @click="cancle" class="m-1">검색 취소</b-button>
      </div>
    </div>
    <div v-else>
      <div v-if="countBoard">
        <b-table-simple hover striped responsive :items="boards" :per-page="perPage" :current-page="currentPage">
          <b-thead>
            <b-tr>
              <b-th>번호</b-th>
              <b-th>제목</b-th>
              <b-th>작성자</b-th>
              <b-th>조회수</b-th>
              <b-th>작성일자</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <b-tr v-for="(board, index) in pageBoardList" :key="index">
              <b-td>{{ board.no }}</b-td>
              <b-td
                ><b-link class="board-link" :to="`/board/detail/${board.no}`">{{ board.title }}</b-link></b-td
              >
              <b-td>{{ board.writerName }}</b-td>
              <b-td>{{ board.hit }}</b-td>
              <b-td>{{ board.regtime }}</b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>
        <div class="row justify-content-center align-items-center">
          <b-form-input v-model="searchTxt" placeholder="검색어를 입력하세요" class="col-6"></b-form-input>
          <b-button variant="dark" @click="searchTitle" class="m-1">검색</b-button>
        </div>
        <b-pagination v-model="currentPage" :total-rows="countBoard" :per-page="perPage" align="center"></b-pagination>
      </div>
      <div v-else>등록된 질문글이 없습니다.</div>
    </div>
    <div class="justify-content-end mb-2">
      <b-button variant="dark" :to="{ name: 'BoardWrite' }" class="justify-content-end">등록</b-button>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const boardStore = "boardStore";
export default {
  name: "BoardList",
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      searchTxt: "",
      isSearchMode: false,
    };
  },
  methods: {
    ...mapActions(boardStore, ["searchTitle", "getBoards"]),

    searchTitle() {
      if (this.searchTxt == "") {
        this.isSearchMode = false;
      } else {
        console.log("submit");
        this.isSearchMode = true;
        this.searchTitle(this.searchTxt);
      }
    },
    cancle() {
      this.searchTxt = "";
      this.isSearchMode = false;
    },
  },
  computed: {
    ...mapState(boardStore, ["boards"]),
    ...mapState(boardStore, ["searchBoards"]),
    pageBoardList() {
      return this.boards.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
    },
    pageSearchBoardList() {
      return this.searchBoards.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
    },
    countBoard() {
      return this.boards.length;
    },
    countSearchBoard() {
      return this.searchBoards.length;
    },
  },
  created() {
    console.log(this);
    this.getBoards();
  },
};
</script>

<style></style>
