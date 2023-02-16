<template>
  <contents-layout>
    <template #title>
      <div class="page-subtitle">{{ $t('trendsAnalytics.regionTrends') }}</div>
    </template>
    <template #control>
      <v-select :items="regionList" v-model="selectedRegion" dense outlined class="mr-3"></v-select>
      <v-select :items="cityList" v-model="selectedCity" dense outlined></v-select>
    </template>
    <template #content>
      <v-card class="pa-4">
        <c-chart-mixed-bar-line
            :labels="labels"
            :datasets="datasets"
        />
      </v-card>
    </template>
  </contents-layout>
</template>

<script>

export default {
  name: 'RegionTrends',
  watch: {
    selectedRegion(val) {
      this.cityList = this.divisionData[val];
      this.selectedCity = this.cityList[0];
    },
    selectedCity(newVal, oldVal) {
      if(oldVal) {
        this.reqRegionTrendsData();
      }
    }
  },
  mounted() {
    this.getDivisionsData();
    this.fillData();
  },
  data() {
    return {
      divisionData:{},
      regionList: [],
      cityList:[],
      selectedRegion: '',
      selectedCity: '',

      datasets: [],

      lineData: [],
      barData: [],
    }
  },
  computed: {
    labels() {
      return this.globalFunc.getDaysLabel(this.$moment(), 31);
    }
  },
  methods: {
    async getDivisionsData() {
      this.divisionData = await this.$api.getCityList();
      this.regionList = Object.keys(this.divisionData);
      this.selectedRegion = this.regionList[0];
      this.cityList = this.divisionData[this.selectedRegion];
      this.selectedCity = this.cityList[0];
    },
    async reqRegionTrendsData() {
      const res = await this.$api.getRegionTrendsData();
      this.lineData = res.lineData;
      this.barData = res.barData;
    },
    async fillData() {
      await this.reqRegionTrendsData();
      this.datasets = [
        {
          type: 'line',
          label: 'threats (high-risk detections) ',
          data: this.lineData,
          fill: false,
          borderColor: '#18468f',
          pointBackgroundColor: '#18468f',
          tension: 0
        },
        {
          type: 'bar',
          label: 'detections',
          data: this.barData,
          backgroundColor: '#dee8ed'
        }
      ];
    }
  }
}
</script>

<style scoped>

</style>