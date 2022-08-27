<template>
  <div>
    <div id="map" class="mx-auto"></div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const apartStore = "apartStore";
export default {
  data() {
    return {
      lat: 0.0,
      lng: 0.0,
    };
  },
  created: function () {
    console.log("현재 위치 파싱");
    this.setCurrentLoc();
  },
  computed: {
    ...mapState(apartStore, ["currentLocation"]),
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => {
        console.log("after load");
        kakao.maps.load(this.initMap);
      };
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.VUE_APP_KAKAOMAP_KEY}&autoload=false`;
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapMutations(apartStore, ["SET_CURRENT"]),
    initMap() {
      if (!window.kakao) return;
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(this.lat, this.lng),
        level: 3,
      };

      var map = new kakao.maps.Map(container, options);
      console.log(this.lat + " " + this.lng);
      var markerPosition = new kakao.maps.LatLng(this.lat, this.lng);
      var marker = new kakao.maps.Marker({
        position: markerPosition,
        title: "",
      });
      marker.setMap(map);
      var iwContent = `<div style="font-size:15px">Welcome to HAPPYHOUSE</div>`;
      var iwRemovable = true;
      var infoWindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemovable,
      });
      infoWindow.open(map, marker);
    },

    setCurrentLoc() {
      console.log("setCurrentLoc 호출");
      if (navigator.geolocation) {
        console.log("geolocation 이용");
        navigator.geolocation.getCurrentPosition((pos) => {
          this.lat = pos.coords.latitude;
          this.lng = pos.coords.longitude;
          console.log("NAVI" + " " + this.lat + " " + this.lng);
          this.initMap();
          this.SET_CURRENT({ lat: this.lat, lng: this.lng });
        });
      } else {
        this.lat = 33.450701;
        this.lng = 126.570667;
        this.initMap();
        this.SET_CURRENT({ lat: this.lat, lng: this.lng });
      }
    },
  },
};
</script>

<style scoped>
#map {
  width: 80%;
  height: 500px;
}
</style>
