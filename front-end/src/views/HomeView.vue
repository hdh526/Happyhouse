<template>
  <div class="home">
    <b-card overlay img-src="https://cdn.pixabay.com/photo/2021/10/22/00/59/restaurant-6730811_960_720.jpg" img-alt="Card Image" class="cardnav"> </b-card>
    <!--<img alt="Home logo" src="../assets/home.png" width="200" height="200" />-->
    <apart-search-bar></apart-search-bar>
    <kakao-map></kakao-map>
  </div>
</template>

<script>
// @ is an alias to /src
import KakaoMap from "@/components/map/KakaoMapHome.vue";
import ApartSearchBar from "@/components/apart/ApartSearchBar.vue";
export default {
  name: "HomeView",

  components: {
    KakaoMap,
    ApartSearchBar,
  },
};
</script>

<style scoped>
/*.background {
  width: 100%;
  height: 300px;
}*/
</style>
