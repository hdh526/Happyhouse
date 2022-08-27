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
  data() {
    return {
      markers: [],
      map: null,
      customOverlay: null,
    };
  },
  computed: {
    ...mapState(apartStore, ["houses"]),
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

    this.displayMarkers();
    console.log(this.markers.length);
  },
  methods: {
    initMap() {
      var container = document.getElementById("map");
      var options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 1,
      };
      this.map = new kakao.maps.Map(container, options);
    },

    displayMarkers() {
      console.log("call");
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }

      console.log("init marker");
      console.log(this.houses);
      for (var i = 0; i < this.houses.length; i++) {
        const house = this.houses[i];
        /*const infowindow = new window.kakao.maps.InfoWindow({
          removable: true,
          content: `<div style="padding:5px;">${house.apartmentName}</div>`,
        });*/
        const marker = this.addMarker(house, i);

        window.kakao.maps.event.addListener(marker, "click", () => {
          if (this.customOverlay) this.customOverlay.setMap(null);
          var position = new window.kakao.maps.LatLng(marker.getPosition().getLat() + 0.0005, marker.getPosition().getLng() - 0.0005);
          this.customOverlay = new window.kakao.maps.CustomOverlay({
            removable: true,
            position: position,
            content: `<div style="padding:5px; background:white">${house.apartmentName}</div>`,
            xAnchor: 0.3,
            yAnchor: 0.91,
          });
          this.customOverlay.setMap(this.map);
        });

        this.markers.push(marker);
      }

      const bounds = this.houses.reduce((bounds, house) => bounds.extend(new kakao.maps.LatLng(house.lat, house.lng)), new kakao.maps.LatLngBounds());
      console.log(bounds);
      this.map.setBounds(bounds);
    },

    addMarker(house, idx) {
      const imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png"; // 마커 이미지 url, 스프라이트 이미지를 씁니다
      const imageSize = new window.kakao.maps.Size(36, 37); // 마커 이미지의 크기
      const imgOptions = {
        spriteSize: new window.kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
        spriteOrigin: new window.kakao.maps.Point(0, idx * 46 + 10),
        offset: new window.kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
      };
      const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
      console.log(house.apartmentName + ": " + house.lat + ", " + house.lng);
      return new window.kakao.maps.Marker({
        position: new window.kakao.maps.LatLng(house.lat, house.lng),
        map: this.map,
        image: markerImage,
      });
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 400px;
}
</style>
