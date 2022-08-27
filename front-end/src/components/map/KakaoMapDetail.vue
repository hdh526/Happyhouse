<template>
  <div>
    <div id="map" class="mx-auto"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const apartStore = "apartStore";

/* global kakao */
export default {
  computed: {
    ...mapState(apartStore, ["house"]),
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAOMAP_KEY}&autoload=false&libraries=services`;
      script.onload = () => kakao.maps.load(this.initMap);
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(this.house.lat, this.house.lng),
        level: 2,
      };
      var map = new kakao.maps.Map(container, options);

      var markerPosition = new kakao.maps.LatLng(this.house.lat, this.house.lng);
      var marker = new kakao.maps.Marker({
        position: markerPosition,
        title: "",
      });
      marker.setMap(map);
      var iwContent = `<div style="font-size:15px">${this.house.apartmentName}</div>`;
      var iwRemovable = true;
      var infoWindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemovable,
      });
      infoWindow.open(map, marker);
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 600px;
}
</style>
