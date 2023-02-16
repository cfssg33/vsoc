<template>
  <contents-layout>
    <template #title>
      <div class="page-subtitle">{{ $t('trendsAnalytics.partSingularity') }}</div>
    </template>
    <template #control>
      <v-text-field style="width: 200px;" dense outlined class="mr-7">
        <template #prepend>
          <span>VID</span>
        </template>
      </v-text-field>
      <v-text-field style="width: 150px;" dense outlined append-icon="search">
        <template #prepend>
          <span style="width: 60px">ECU ID</span>
        </template>
      </v-text-field>
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
  name: 'PartSingularity',
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
    async reqPartSingularityData() {
      const res = await this.$api.getPartSingularityData();
      this.lineData = res.lineData;
      this.barData = res.barData;
    },
    async fillData() {
      await this.reqPartSingularityData();
      this.datasets = [
        {
          type: 'line',
          data: this.lineData,
          fill: false,
          borderColor: '#f48383',
          pointRadius: 0,
          tension: 0,
        },
        {
          type: 'bar',
          data: this.barData,
          backgroundColor: '#ffd9d9',
        }
      ];
    }
  },
}
</script>

<style scoped>

</style>