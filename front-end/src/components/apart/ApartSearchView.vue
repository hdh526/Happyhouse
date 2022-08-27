<template>
  <div id="container" class="mt-3">
    <apart-search-bar-detail></apart-search-bar-detail>
    <b-row v-if="housesCnt">
      <b-col cols="8">
        <kakao-map-apt class="ml-3"></kakao-map-apt>
      </b-col>
      <b-col cols="4">
        <b-list-group class="mr-3">
          <b-list-group-item>
            <div><b>아파트 목록</b></div>
          </b-list-group-item>
          <b-list-group-item v-for="(apart, index) in houses" :key="index" :to="`/apart/detail/${index}`" class="flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
              <span
                ><small>{{ index + 1 }}</small> &nbsp; {{ apart.apartmentName }}</span
              >
              건축년도 : {{ apart.buildYear }}
            </div>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>
    <div v-else>
      <p>아파트가 존재하지 않는 지역입니다.</p>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import KakaoMapApt from "@/components/map/KakaoMapApt.vue";
import ApartSearchBarDetail from "./ApartSearchBarDetail.vue";
const apartStore = "apartStore";
export default {
  components: {
    KakaoMapApt,
    ApartSearchBarDetail,
  },
  computed: {
    ...mapState(apartStore, ["houses"]),
    ...mapGetters(apartStore, ["housesCnt"]),
  },
  created() {
    console.log("apartlist");
    console.log(this);
  },
};
</script>

<style></style>
