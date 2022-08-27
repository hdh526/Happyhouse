<template>
  <b-row class="mt-4 mb-4 mr-5 ml-5 text-center">
    <b-col class="sm-3">
      <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="gugunCode" :options="guguns" @change="dongList"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="dongCode" :options="dongs"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-input type="text" v-model="aptName" placeholder="아파트명"></b-form-input>
    </b-col>
    <b-col class="sm-3">
      <b-button @click="searchApt">지역 검색</b-button>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const apartStore = "apartStore";
export default {
  name: "ApartSearchBar",
  data() {
    return {
      sidoCode: "",
      gugunCode: "",
      dongCode: "",
      aptName: "",
    };
  },
  computed: {
    ...mapState(apartStore, ["sidos", "guguns", "dongs", "sido", "gugun"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.CLEAR_SIDO();
    this.CLEAR_GUGUN();
    this.CLEAR_DONG();
    this.getSido();
  },
  methods: {
    ...mapActions(apartStore, ["getSido", "getGugun", "getDong", "searchHouseList", "searchHouseListN"]),
    ...mapMutations(apartStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_SIDO", "CLEAR_DONG", "CLEAR_GUGUN"]),

    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.CLEAR_GUGUN();
      this.CLEAR_DONG();
      this.gugunCode = "";
      this.dongCode = "";
      //if (this.sidoCode) this.getGugun(this.sidoCode);
      this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.CLEAR_DONG();
      this.dongCode = "";
      //if (this.gugunCode) this.getDong(this.gugunCode);
      this.getDong(this.gugunCode);
    },
    searchApt() {
      console.log(this.dongCode);
      if (this.aptName) {
        //var param = { sidoCode: this.sidoCode, gugun: this.gugunCode, dong: this.dongCode, apt: this.aptName };
        var param = { dongCode: this.dongCode, aptName: this.aptName };
        //console.log(param);
        this.searchHouseListN(param);
      } else {
        if (this.dongCode) {
          this.searchHouseList(this.dongCode);
        } else {
          alert("아파트명 검색 혹은 지역(동) 선택 필수");
        }
      }
    },
  },
};
</script>

<style></style>
