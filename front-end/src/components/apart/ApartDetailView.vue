<template>
  <b-container>
    <b-card class="m-4">
      <h2>{{ house.apartmentName }}</h2>
    </b-card>
    <b-row>
      <b-col> <kakao-map-detail></kakao-map-detail> </b-col>
      <b-col>
        <b-card>
          <b>아파트 정보</b>
          <div>{{ sido }} {{ gugun }} {{ house.dong }} {{ house.jibun }}</div>
          <div>건축년도 : {{ house.buildYear }}</div>
          <div class="mt-3">
            <b-form-group v-slot="{ ariaDescribedby }">
              <b-form-radio-group
                id="btn-radios-2"
                v-model="selectedYear"
                :options="optionYear"
                :aria-describedby="ariaDescribedby"
                button-variant="outline-primary"
                name="radio-btn-outline"
                @change="selectOption"
                buttons
              >
              </b-form-radio-group>
            </b-form-group>
            <b-form-group v-slot="{ ariaDescribedby }">
              <b-form-radio-group
                id="btn-radios-2"
                v-model="selectedArea"
                :options="optionArea"
                :aria-describedby="ariaDescribedby"
                button-variant="outline-primary"
                name="radio-btn-outline"
                @change="selectOption"
                buttons
              >
              </b-form-radio-group>
            </b-form-group>
          </div>
        </b-card>
        <b-table-simple hover striped responsive :items="housedeal" :per-page="perPage" :current-page="currentPage" class="mt-3">
          <b-thead>
            <b-tr>
              <b-th></b-th>
              <b-th @click="sortDate">거래날짜</b-th>
              <b-th>층수</b-th>
              <b-th>전용면적</b-th>
              <b-th @click="sortDeal">거래금액</b-th>
            </b-tr>
          </b-thead>
          <b-tbody v-for="(deal, index) in pageDealList" :key="index">
            <b-td>{{ index + 1 + (currentPage - 1) * perPage }}</b-td>
            <b-td>{{ deal.dealYear }}.{{ deal.dealMonth }}.{{ deal.dealDay }}</b-td>
            <b-td>{{ deal.floor }}</b-td>
            <b-td>{{ deal.area }}</b-td>
            <b-td>{{ deal.dealAmount }}</b-td>
          </b-tbody>
        </b-table-simple>
        <b-pagination v-model="currentPage" :total-rows="countDeal" :per-page="perPage" align="center"></b-pagination>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import KakaoMapDetail from "@/components/map/KakaoMapDetail.vue";
const apartStore = "apartStore";
export default {
  components: {
    KakaoMapDetail,
  },
  name: "ApartDetail",
  data() {
    return {
      perPage: 8,
      currentPage: 1,
      issortDeal: false,
      issortDate: true,
      issortArea: false,
      selectedYear: "All",
      selectedArea: "All",
    };
  },
  methods: {
    ...mapActions(apartStore, ["searchDeals"]),
    ...mapMutations(apartStore, ["CLEAR_DEAL_SELECT", "SET_DEAL_SELECT"]),
    sortDate() {
      console.log("날짜 정렬 임시");
      this.issortDate = !this.issortDate;
      if (!this.issortDate) {
        this.housedeal.sort((a, b) => {
          if (a.dealYear < b.dealYear) return 1;
          if (a.dealYear > b.dealYear) return -1;

          if (a.dealMonth < b.dealMonth) return 1;
          if (a.dealMonth > b.dealMonth) return -1;

          if (a.dealDay < b.dealDay) return 1;
          if (a.dealDay > b.dealDay) return -1;

          return 0;
        });
      } else {
        this.housedeal.sort((a, b) => {
          if (a.dealYear < b.dealYear) return -1;
          if (a.dealYear > b.dealYear) return 1;

          if (a.dealMonth < b.dealMonth) return -1;
          if (a.dealMonth > b.dealMonth) return 1;

          if (a.dealDay < b.dealDay) return -1;
          if (a.dealDay > b.dealDay) return 1;
          return 0;
        });
      }
      console.log(this.housedeal);
    },
    sortDeal() {
      console.log("금액 정렬 임시");
      this.issortDeal = !this.issortDeal;
      if (!this.issortDeal) {
        this.housedeal.sort((a, b) => {
          a = a.dealAmount.replace(",", "") * 1;
          b = b.dealAmount.replace(",", "") * 1;

          if (a < b) return 1;
          if (a > b) return -1;
          return 0;
        });
      } else {
        this.housedeal.sort((a, b) => {
          a = a.dealAmount.replace(",", "") * 1;
          b = b.dealAmount.replace(",", "") * 1;

          if (a < b) return -1;
          if (a > b) return 1;
          return 0;
        });
      }
      console.log(this.housedeal);
    },
    selectOption() {
      this.CLEAR_DEAL_SELECT();
      console.log(this.selectedYear + " ", this.selectedArea);
      this.housedeals.forEach((element) => {
        console.log(element.dealYear + " " + element.area);
        if (this.selectedYear != "All") {
          if (this.selectedArea != "All") {
            if (element.dealYear == this.selectedYear && element.area == this.selectedArea) {
              console.log("hear1");
              this.housedeal.push(element);
            }
          } else {
            if (element.dealYear == this.selectedYear) {
              console.log("hear2");
              this.housedeal.push(element);
            }
          }
        } else {
          if (this.selectedArea != "All") {
            if (element.area == this.selectedArea) {
              console.log("hear3");
              this.housedeal.push(element);
            }
          } else {
            console.log("hear4");
            this.housedeal.push(element);
          }
        }
      });
      console.log(this.housedeal);
      this.SET_DEAL_SELECT(this.housedeal);
    },
  },
  computed: {
    ...mapState(apartStore, ["housedeals", "house", "sido", "gugun", "dong", "optionYear", "optionArea", "housedeal"]),
    pageDealList() {
      return this.housedeal.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
    },
    countDeal() {
      return this.housedeal.length;
    },
  },
  created() {
    const pathName = new URL(document.location).pathname.split("/");
    const idx = pathName[pathName.length - 1];
    this.searchDeals(idx).then(() => {
      console.log(this.housedeals);
      console.log(this.housedeal);
    });
  },
};
</script>

<style></style>
