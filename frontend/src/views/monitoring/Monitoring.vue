<template>
  <div
    id="indexContainer"
    class="d-flex flex-column"
    style="width: 100%; height: 100%; text-align: center; max-width: inherit;"
  >
    <div class="flex-shrink-1 d-flex flex-row">
      <v-row class="ml-1 mb-2" style="padding-left: 10px; padding-top: 10px">
        <v-checkbox
            label="Map"
            v-model="visibleMap"
        >
        </v-checkbox>
        <p style="padding-left: 1em"></p>
        <v-checkbox
            label="Summary"
            v-model="visibleInfo"
        >
        </v-checkbox>
        <p style="padding-left: 1em"></p>
        <v-checkbox
            label="Most Vulnerable Region Ranking"
            v-model="visibleRankRegion"
        >
        </v-checkbox>
        <p style="padding-left: 1em"></p>
        <v-checkbox
            label="Most Vulnerable City Ranking"
            v-model="visibleRankCity"
        >
        </v-checkbox>
      </v-row>
    </div>
    <div class="flex-shrink-1 d-flex flex-row">
      <d3-map
          class="flex-shrink-1 d-flex justify-start"
          style="width: 50%; height: 700px; margin: 2vh;"
          v-if="visibleMap"
      />

      <div class="flex-shrink-1 justify-end" style="width: 50%; margin: 2vh;">
        <div
          class="d-flex flex-column"
          v-for="[key, value] in Object.entries(rightPanelData)"
          :key="key"
          v-if="visibleInfo"
        >
          <dashboard-info-card
            :title="$t(`monitoring.${key}`)"
            :datas="value"
            :card-style="cardStyles[key]"
            :colors="monitoringColors[key].colors"
            style="margin-bottom: 0.5vh;"
          ></dashboard-info-card>
        </div>
      </div>
    </div>

    <div class="mt-0 flex-shrink-1 d-flex flex-row" style="margin-top: 3vh;">
      <contents-layout class="flex-1" style="margin: 2vh;"  v-if="visibleRankRegion">
        <template #title>
          <div class="title" style="min-height: 6vh;">
            {{ $t('monitoring.mostVulnerableRegionRanking') }}
          </div>
        </template>
        <template #content>
          <v-data-table
            class="white"
            :headers="vulnerableRegionHeader"
            :items="regionData"
            hide-default-footer
            :disable-pagination="isRegionsExpanded"
          ></v-data-table>
          <v-btn
            @click="isRegionsExpanded = !isRegionsExpanded"
            class="mainBlue--text"
            outlined
            style="margin-top: 1vh;"
            color="mainBlue"
            width="100%"
          >
            {{ isRegionsExpanded ? $t('monitoring.collapse') : $t('monitoring.viewAll') }}
          </v-btn>
        </template>
      </contents-layout>
      <contents-layout style="margin: 2vh;" v-if="visibleRankCity">
        <template #title>
          <div class="title" style="min-height: 6vh;">
            {{ $t('monitoring.mostVulnerableCityRanking') }}
          </div>
        </template>
        <template #control>
          <v-select
            class="ma-0 pa-0"
            v-model="selectedRegion"
            :items="regionList"
            label="region"
            return-object
            single-line
            v-on:change="changeRegion"
          ></v-select>
        </template>
        <template #content>
          <v-data-table
            class="white"
            :headers="vulnerableCityHeader"
            :items="citiesData"
            hide-default-footer
            :disable-pagination="isCitiesExpanded"
          ></v-data-table>
          <v-btn
            v-if="citiesData.length > 10"
            @click="isCitiesExpanded = !isCitiesExpanded"
            class="mainBlue--text"
            style="margin-top: 1vh;"
            outlined
            color="mainBlue"
            width="100%"
          >
            {{ isCitiesExpanded ? $t('monitoring.collapse') : $t('monitoring.viewAll') }}
          </v-btn>
        </template>
      </contents-layout>
    </div>
  </div>
</template>
<script>
import monitoringColors from '../../constants/cardStyles/monitoringColors';
import cardStyles from '../../constants/cardStyles/monitoring';
import monitoringTableTitleStyle from '../../constants/cardStyles/monitoringTableTitle';
import monitoringTableDataStyle from '../../constants/cardStyles/monitoringTable';
import filters from '../../plugins/filters';
// import BaiduMap from './BaiduMap';
import DashboardInfoCard from './DashboardInfoCard.vue';
// import '../../lib/baiduMap.js';

import D3Map from '@/views/monitoring/D3Map';

const { canonicalNumberFormatter } = filters;

export default {
  name: 'Monitoring',
  // components: {D3Map, BaiduMap, DashboardInfoCard },
  components: {D3Map, DashboardInfoCard },
  data() {
    return {
      rightPanelData: {
        overallThreatLevel: {
          datas: []
        },
        totalVehicles: {
          datas: [],
          subTitles: ['danger', 'warning']
        },
        /*vehiclePlatformType: {
          datas: [],
          subTitles: ['appliedSecurityPolicy']
        },*/
        detectedCanLogs: {
          alarms: [],
          datas: [],
          subTitles: ['emergencyAlarms'],
          redirectTo: 'detectionCanIds'
        },
        detectedHostLogs: {
          alarms: [],
          datas: [],
          subTitles: ['emergencyAlarms'],
          redirectTo: 'detectionHostIdps'
        }
      },
      regionList: [],
      selectedRegion: '',
      regionData: [],
      citiesData: [],
      isRegionsExpanded: false,
      isCitiesExpanded: false,
      cardStyles,
      monitoringColors,
      monitoringTableTitleStyle,
      monitoringTableDataStyle,
      visibleInfo: true,
      visibleRankCity: true,
      visibleRankRegion: true,
      visibleMap: true,
      intervalId: 0
    };
  },
  computed: {
    vulnerableRegionHeader() {
      return [
        { text: this.$t('monitoring.number'), value: 'number', align: 'center' },
        { text: this.$t('monitoring.region'), value: 'location', align: 'center' },
        { text: this.$t('monitoring.otl'), value: 'otl', align: 'center' },
        { text: this.$t('monitoring.danger'), value: 'danger', align: 'center' },
        { text: this.$t('monitoring.warning'), value: 'warning', align: 'center' }
      ]
    },
    vulnerableCityHeader() {
      return [
        { text: this.$t('monitoring.number'), value: 'number', align: 'center' },
        { text: this.$t('monitoring.city'), value: 'location', align: 'center' },
        { text: this.$t('monitoring.otl'), value: 'otl', align: 'center' },
        { text: this.$t('monitoring.danger'), value: 'danger', align: 'center' },
        { text: this.$t('monitoring.warning'), value: 'warning', align: 'center' }
      ]
    }
  },
  methods: {
    changeRegion() {
      this.getVulnerableCityData(false);
    },
    addNumber(obj) {
      return obj.map((item, idx) => {
        item.number= idx+1;
        return item;
      });
    },
    changeRightPanelData(rightPanelData) {
      this.rightPanelData.overallThreatLevel.datas = [rightPanelData.overallThreatLevel.count, '/', '100'];

      const totalString = canonicalNumberFormatter(rightPanelData.totalVehicles.count);
      const dangerLocaleString = canonicalNumberFormatter(rightPanelData.totalVehicles.danger);
      const dangerPercent = (rightPanelData.totalVehicles.count) ? Math.round((rightPanelData.totalVehicles.danger / rightPanelData.totalVehicles.count) * 1000) / 10 : 0;
      const dangerString = `${dangerLocaleString} (${dangerPercent} %)`;
      const warningLocaleString = rightPanelData.totalVehicles.warning.toLocaleString();
      const warningPercent = (rightPanelData.totalVehicles.count) ? Math.round((rightPanelData.totalVehicles.warning / rightPanelData.totalVehicles.count) * 1000) / 10 : 0;
      const warningString = `${warningLocaleString} (${warningPercent} %)`;
      this.rightPanelData.totalVehicles.datas = [totalString, dangerString, warningString];

      //this.rightPanelData.vehiclePlatformType.datas = [rightPanelData.vehiclePlatformType.count, rightPanelData.vehiclePlatformType.appliedSecurityPolicy];

      const canLogsString = canonicalNumberFormatter(rightPanelData.detectedCanLogs.count);
      this.rightPanelData.detectedCanLogs.datas = [`${canLogsString} / 1H`];
      this.rightPanelData.detectedCanLogs.alarms = rightPanelData.detectedCanLogs.alarms;

      const hostLogsString = canonicalNumberFormatter(rightPanelData.detectedHostLogs.count);
      this.rightPanelData.detectedHostLogs.datas = [`${hostLogsString} / 1H`];
      this.rightPanelData.detectedHostLogs.alarms = rightPanelData.detectedHostLogs.alarms;
    },
    async getRightPanelData() {
      const rightPanelData = await this.$api.getMonitoringPanelData();
      this.changeRightPanelData(rightPanelData);
    },
    async getVulnerableRegionData() {
      const regionRespData = await this.$api.getVulnerableRegionData();
      this.regionData = [...this.addNumber(regionRespData)];
    },
    async getVulnerableCityData(ifNeedRegionList = false) {
      if (ifNeedRegionList) {
        this.regionList = await this.$api.getRegionList();
        this.selectedRegion = this.regionList[0];
      }
      const cityRespData = await this.$api.getVulnerableCityData(this.selectedRegion);
      this.citiesData = [...this.addNumber(cityRespData)];
    },
    getMonitoringData(ifNeedRegionList) {
      this.getRightPanelData();
      this.getVulnerableRegionData();
      this.getVulnerableCityData(ifNeedRegionList);
    }
  },
  mounted() {
    this.getMonitoringData(true);
    this.intervalId = setInterval(this.getMonitoringData, 60000);
  },
  destroyed() {
    clearInterval(this.intervalId);
  },
};
</script>

<style>
.title {
  font-size: 1.6rem !important;
  font: SpoqaHanSansNeo-Bold !important;
}
#indexContainer {
  background: rgba(7, 69, 148, 0.03);
}
</style>
