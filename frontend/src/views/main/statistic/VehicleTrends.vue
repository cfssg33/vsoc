<template>
  <contents-layout>
    <template #title>
      <div class="page-subtitle">{{ $t('trendsAnalytics.vehicleTrends') }}</div>
    </template>
    <template #control>
      <div style="width: 410px">
        <v-text-field outlined dense append-icon="search">
          <template #prepend>
            <span>VID</span>
          </template>
        </v-text-field>
      </div>
    </template>
    <template #content>
      <v-card class="pa-4">
        <v-card-title>{{ $t('trendsAnalytics.singularityAnalysis') }}</v-card-title>
        <c-chart-mixed-bar-line
            :labels="labels"
            :datasets="datasets"
            :legend="legend"
            :scales="scales"
        />
      </v-card>
    </template>
  </contents-layout>
</template>

<script>
export default {
  name: 'VehicleTrends',
  data() {
    return {
      legend: {
        display: false,
      },
      scales: {
        yAxes: [{
          gridLines: {
            display: false,
          },
          ticks: {
            stepSize: 200,
          }
        }],
        xAxes: [{
          gridLines: {
            display: false,
          },
        }],
      },
      datasets: [],

      lineData: [],
      barData: [],
    }
  },
  computed: {
    labels() {
      return this.globalFunc.getDaysLabel(this.$moment(), 62);
    }
  },
  mounted() {
    this.fillData();
  },
  methods: {
    async reqVehicleTrendsData() {
      const res = await this.$api.getVehicleTrendsData();
      this.lineData = res.lineData;
      this.barData = res.barData;
    },
    async fillData() {
      await this.reqVehicleTrendsData();
      this.datasets = [
        {
          // 선택한 차의 detections
          type: 'line',
          data: this.lineData,
          fill: false,
          borderColor: '#18468f',
          pointRadius: 0,
          tension: 0
        },
        {
          // 중국 전체 detections
          type: 'bar',
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