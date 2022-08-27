<template>
  <b-row class="mt-4 mb-4 mr-5 ml-5 text-center">
    <b-col class="sm-3">
      <b-form-select v-model="codeSido" :options="sidos" @change="gugunList"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="codeGugun" :options="guguns" @change="dongList"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="codeDong" :options="dongs"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-input type="text" v-model="name" placeholder="아파트명"></b-form-input>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="base" :options="bases"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="sort" :options="sorts"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-button @click="searchApt">아파트 검색</b-button>
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
      codeSido: "",
      codeGugun: "",
      codeDong: "",
      name: "",
      sort: "",
      base: "",
    };
  },
  created() {
    this.codeSido = this.sidoCode;
    this.codeGugun = this.gugunCode;
    this.codeDong = this.dongCode;
    this.name = this.aptName;
  },
  computed: {
    ...mapState(apartStore, ["sidos", "guguns", "dongs", "sidoCode", "gugunCode", "dongCode", "aptName"]),
  },
  methods: {
    ...mapActions(apartStore, ["getSido", "getGugun", "getDong", "searchHouseListC", "searchHouseListNC"]),
    ...mapMutations(apartStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST", "CLEAR_SIDO", "CLEAR_DONG", "CLEAR_GUGUN", "SET_SIDO", "SET_GUGUN", "SET_DONG"]),

    gugunList() {
      console.log(this.codeSido);
      this.SET_SIDO(this.codeSido);
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.CLEAR_GUGUN();
      this.CLEAR_DONG();
      this.codeGugun = "";
      this.codeDong = "";
      //if (this.sidoCode) this.getGugun(this.sidoCode);
      this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.codeGugun);
      this.SET_GUGUN(this.codeGugun);
      this.CLEAR_DONG_LIST();
      this.CLEAR_DONG();
      this.dongCode = "";
      //if (this.gugunCode) this.getDong(this.gugunCode);
      this.getDong(this.gugunCode);
    },
    searchApt() {
      console.log(this.codeDong);
      this.SET_DONG(this.codeDong);
      if (this.name) {
        //var param = { sidoCode: this.sidoCode, gugun: this.gugunCode, dong: this.dongCode, apt: this.aptName };
        var param = { dongCode: this.codeDong, aptName: this.name };
        //console.log(param);
        this.searchHouseListNC(param);
      } else {
        if (this.codeDong) {
          this.searchHouseListC(this.codeDong);
        } else {
          alert("아파트명 검색 혹은 지역(동) 선택 필수");
        }
      }
    },
  },
};
</script>

<style></style>
