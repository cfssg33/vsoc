<template>
  <v-container id="page-trends-analytics" style="max-width: none;">
    <v-row>
      <v-col cols="12" md="6">
        <overall-trends />
      </v-col>
      <v-col cols="12" md="6">
        <region-trends />
      </v-col>
      <v-col cols="12">
        <vehicle-trends />
      </v-col>
      <v-col cols="12">
        <parts-trends />
      </v-col>
      <v-col cols="12">
        <part-singularity />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import OverallTrends from './statistic/OverallTrends';
import RegionTrends from './statistic/RegionTrends';
import VehicleTrends from './statistic/VehicleTrends';
import PartsTrends from './statistic/PartsTrends';
import PartSingularity from './statistic/PartSingularity';

export default {
  name: 'TrendsAnalytics',
  components: {PartSingularity, PartsTrends, VehicleTrends, RegionTrends, OverallTrends},
}
</script>

<style>
.page-subtitle {
  font-size: 18px;
  font-weight: bold;
  color: #515461;
  padding-top: 1vh;
  padding-bottom: 1vh;
}
</style>