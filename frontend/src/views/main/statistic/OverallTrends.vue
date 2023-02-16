<template>
  <contents-layout>
    <template #title>
      <div class="page-subtitle mb-3">{{ $t('trendsAnalytics.overallTrends') }}</div>
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
  name: 'OverallTrends',
  mounted() {
    this.fillData();
  },
  data() {
    return {
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
    async fillData() {
      await this.reqOverallTrendsData();
      this.datasets = [
        {
          type: 'line',
          label: 'threats (high-risk detections)',
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
        },
      ];
    },
    async reqOverallTrendsData() {
      const res = await this.$api.getOverallTrendsData();
      this.lineData = res.lineData;
      this.barData = res.barData;
    }
  }
}
</script>

<style scoped>

</style>