<template>
  <v-container style="max-width: none;">
    <contents-layout style="max-width: none;">
      <template #title>
<!--        <v-select v-model="selectedRegion" :items="regionList" outlined style="width:50px"></v-select>-->
<!--        <v-select v-model="selectedCity" :items="cityList" item-text="chinese" outlined class="mx-10" style="width:50px"></v-select>-->
        <v-spacer></v-spacer>
      </template>
      <template #control>
        <v-btn class="mainBlue mr-3" style="top: 10px" @click="reqOverallInfoAndDetectionList" dark>SEARCH</v-btn>
        <v-menu
            v-model="startDateFlag"
            transition="scale-transition"
            offset-y
        >
          <template #activator="{ on }">
            <div style="width: 150px">
              <v-text-field
                  v-model="startDate"
                  prepend-icon="event"
                  readonly
                  v-on="on"
                  :label="$t('detectionCanIds.startDate')"
              ></v-text-field>
            </div>
          </template>
          <c-date-picker v-model="startDate" @input="startDateFlag = false" no-title :firstDayOfWeek="1"
                         :selectedDate="startDate" :nudge-bottom="10"></c-date-picker>
        </v-menu>
        <v-menu v-model="startTimeFlag" :close-on-content-click="false" offset-y>
          <template #activator="{on, attrs}">
            <v-text-field
                v-model="startTime"
                prepend-icon="mdi-clock"
                v-on="on"
                readonly
                class="px-2"
                style="width:120px"
            ></v-text-field>
          </template>
          <time-picker v-model="startTime" @close="startTimeFlag=false"></time-picker>
        </v-menu>

        <v-menu
            v-model="endDateFlag"
            transition="scale-transition"
            offset-y
        >
          <template #activator="{ on }">
            <div class="ml-7" style="width: 150px">
              <v-text-field
                  v-model="endDate"
                  prepend-icon="event"
                  readonly
                  v-on="on"
                  :label="$t('detectionCanIds.endDate')"
              ></v-text-field>
            </div>
          </template>
          <c-date-picker v-model="endDate" @input="endDateFlag = false" no-title :firstDayOfWeek="1" :selectedDate="endDate"></c-date-picker>
        </v-menu>
        <v-menu v-model="endTimeFlag" :close-on-content-click="false" offset-y>
          <template #activator="{on, attrs}">
            <v-text-field
                v-model="endTime"
                prepend-icon="mdi-clock"
                v-on="on"
                readonly
                class="px-2"
                style="width:120px"
            ></v-text-field>
          </template>
          <time-picker v-model="endTime" @close="endTimeFlag=false"></time-picker>
        </v-menu>

        <v-checkbox :label="$t('detectionCanIds.alwaysRealTime')" v-model="isAlwaysRealtime"></v-checkbox>
      </template>
      <template #content>
        <v-row>
          <v-col>
            <c-board :title="$t('detectionCanIds.totalDetections')" :value="overallInfo.totalDetections"></c-board>
          </v-col>
          <v-col>
            <c-board :title="$t('detectionCanIds.averageDetections')" unit="per min">
              <template #value>
                {{Number(overallInfo.averageDetections).toFixed(2)}}
              </template>
            </c-board>
          </v-col>
          <v-col>
            <c-board :title="$t('detectionCanIds.detectedVehicles')" :value="overallInfo.detectedVehicles"></c-board>
          </v-col>
          <v-col cols="4" align="center">
            <v-card outlined class="py-4">
              <c-chart-pie
                  style="height:165px; width:250px"
                  :labels="overallInfo.canIdsTopFiveRules.ruleId"
                  :datasets="drawPieChart"
                  :legend="{position:'right', labels:{boxWidth: 10, padding:5}}"
                  :title="{display: true, fontSize: 20, fontStyle:'normal', text: $t('detectionCanIds.ruleName')}"
              />
            </v-card>
          </v-col>
        </v-row>
      </template>
    </contents-layout>

    <contents-layout style="max-width: none;">
      <template #title>
        <span class="page-title"> {{$t('detectionCanIds.detectionList')}}</span>
      </template>
      <template #control>
        <v-spacer></v-spacer>
        <v-btn class="mainBlue mr-3" @click="reqExport" dark> {{ $t('detectionCanIds.export') }}</v-btn>
        <v-text-field
            :label="$t('detectionCanIds.vehicleIdSearch')"
            v-model="vehicleKeyword"
            @keyup.enter="reqDetectionsByVehicle"
            prepend-inner-icon="search"
            outlined dense hide-details
        ></v-text-field>
      </template>
      <template #content>
        <v-data-table
            :headers="headers"
            :items="detectionList"
            :items-per-page.sync="tableItemsPerPage"
            :page.sync="tablePage"
            hide-default-footer
            class="elevation-1">
          <template #item.label="{ item }">
            <v-select :items="$store.state.detectionLabel" style="width:130px" @change="reqUpdateLabel(item)" v-model="item.label"></v-select>
          </template>
          <template #item.hidden="{ item }">
            <v-btn icon @click="isHideRule(item)">
              <v-icon>delete_outline</v-icon>
            </v-btn>
          </template>
        </v-data-table>
        <v-pagination
          v-model="tablePage"
          :length="totalPage"
          :total-visible="11"
          @input="updatePagination"
        />
      </template>
    </contents-layout>

    <v-dialog v-model="showHideDialog" width="600" persistent>
      <v-card class="pa-4">
        <v-card-title>{{ $t('detectionCanIds.hideRule') }}</v-card-title>
        <v-card-text>
          <span class="contentBlue--text"> {{ hideLogCount }}</span> <span style="white-space: pre-wrap">{{ $t('detectionCanIds.hideRuleMessage') }}</span>
          <v-data-table :headers="dialogHeader" :items="dialogItems" class="mt-8 elevation-1" hide-default-footer>
          </v-data-table>
        </v-card-text>
        <v-card-actions>
          <v-row justify="center">
            <v-btn class="mx-1" color="contentBlue" dark outlined @click="reqHideRules">{{$t('detectionCanIds.accept')}}</v-btn>
            <v-btn class="mx-1" color="contentGrey" dark outlined @click="showHideDialog = false">{{$t('detectionCanIds.cancel')}}</v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>

export default {
  name: 'DetectionCanIds',
  data() {
    return {
      startDateFlag: false,
      startTimeFlag: false,
      endDateFlag: false,
      endTimeFlag: false,
      startDate: this.$moment().format('YYYY-MM-DD'),
      startTime: this.$moment().subtract(1, 'H').format('HH:MM'),
      endDate: this.$moment().format('YYYY-MM-DD'),
      endTime: this.$moment().format('HH:MM'),
      overallInfo: {
        totalDetections: 0,
        averageDetections: 0,
        detectedVehicles: 0,
        canIdsTopFiveRules: {
          ruleId: [],
          count: []
        },
      },
      detectionList:[],
      divisionData:{},
      regionList:[],
      cityList:[],
      selectedRegion: '',
      selectedCity: '',
      isAlwaysRealtime: false,
      vehicleKeyword:'',
      showHideDialog: false,
      dialogItems: [],
      hideLogCount:0,
      intervalId: 0,
      tableItemsPerPage: 10,
      tablePage: 1,
      totalLogCount: 0,
      totalPage: 0,
    }
  },
  created() {
    this.vehicleKeyword = this.$route.params.redirectedKeyword;
  },
  watch:{
    isAlwaysRealtime(val) {
      if(val) {
        const now = this.$moment();
        this.endDate = now.format('YYYY-MM-DD');
        this.endTime = now.format('HH:mm');
      }
    },
    selectedRegion(val) {
      this.cityList = this.divisionData[val];
      this.selectedCity = this.cityList[0];
    },
    selectedCity(newVal, oldVal) {
      if(oldVal) {
        this.needRenew();
      }
    },
    startDate() {
      this.needRenew();
    },
    startTime() {
      this.needRenew();
    },
    endDate() {
      this.needRenew();
    },
    endTime() {
      this.needRenew();
    },
  },
  computed: {
    drawPieChart() {
      const datasets = [
        {
          backgroundColor: ['#1d6dff', '#ff842f', '#a0a0a0', '#ffce00', '#57abff', '#59d100'],
          data: this.overallInfo.canIdsTopFiveRules.count
        }
      ];
      return datasets;
    },
    headers() {
      return [
        {text: this.$t('detectionCanIds.detectedTime'), value: 'detectedTime', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.vehicleId'), value: 'vehicleId', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.policy'), value: 'policy', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.version'), value: 'version', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.ruleName'), value: 'ruleName', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.cnt'), value: 'count', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.tx'), value: 'tx', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.rx'), value: 'rx', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.busId'), value: 'busId', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.messageId'), value: 'message', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.signalId'), value: 'signalId', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.rawMessageData'), value: 'rawMessageData', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.reasonForDetection'), value: 'reason', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.severity'), value: 'severity', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.labeling'), value: 'label', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.hide'), value: 'hidden', align:'center', sortable:false, class:'font-weight-bold'},
      ]
    },
    dialogHeader() {
      return [
        {text: this.$t('detectionCanIds.policy'), value: 'policy', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.version'), value: 'version', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.ruleName'), value: 'ruleName', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.busId'), value: 'busId', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.messageId'), value: 'message', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionCanIds.signalId'), value: 'signalId', align:'center', sortable:false, class:'font-weight-bold'}
      ]
    }
  },
  mounted() {
    //this.getDivisionsData();
    this.getOverallInfo();
    this.getDetectionList();
    this.intervalId = setInterval(this.getDetectionList, 10000);
  },
  destroyed() {
    clearInterval(this.intervalId);
  },
  methods: {
    updatePagination(page) {
      this.getDetectionList();
    },
    async getDivisionsData() {
      this.divisionData = await this.$api.getCityList();
      this.regionList = Object.keys(this.divisionData);
      this.selectedRegion = this.regionList[0];
      this.cityList = this.divisionData[this.selectedRegion];
      this.selectedCity = this.cityList[0];
    },
    async getOverallInfo() {
      let startDateTime = this.startDate + ' ' + this.startTime + ':00';
      let endDateTime = this.endDate + ' ' + this.endTime + ':00';
      let over = await this.$api.getCanIdsOverall(startDateTime, endDateTime);

      this.overallInfo.totalDetections = over.totalDetections;
      this.overallInfo.averageDetections = over.averageDetections;
      this.overallInfo.detectedVehicles = over.detectedVehicles;

      for (let value in over.canIdsTopFiveRules) {
        this.overallInfo.canIdsTopFiveRules.ruleId.push(over.canIdsTopFiveRules[value].ruleId);
        this.overallInfo.canIdsTopFiveRules.count.push(over.canIdsTopFiveRules[value].count);
      }
    },
    async getDetectionList() {
      let startDateTime = this.startDate + ' ' + this.startTime + ':00';
      let endDateTime = this.endDate + ' ' + this.endTime + ':00';

      const result = await this.$api.getCanIdsLogList(startDateTime, endDateTime, this.tableItemsPerPage, this.tablePage);
      this.detectionList = result.canIdsLogList;
      this.totalLogCount = result.totalCount;
      this.totalPage = result.totalPage;
    },
    needRenew() {
    },
    async reqUpdateLabel(item) {
      await this.$api.changeCanIdsLogLabel(item.id, item.label);
    },
    async reqExport() {
      await this.$api.exportCanIdsLog();
    },
    reqDetectionsByVehicle() {
      //todo api call;
      if(this.vehicleKeyword.length < 1) {

      }
    },
    reqOverallInfoAndDetectionList() {
      this.getOverallInfo();
      this.getDetectionList();
    },
    isHideRule(item) {
      let hideTargets = this.detectionList;
      hideTargets = hideTargets.filter(v => v.ruleId === item.ruleId);
      this.hideLogCount = hideTargets.length;
      this.dialogItems = hideTargets;

      this.showHideDialog = true;
    },
    reqHideRules() {
      const dialogIds = this.dialogItems.map(item => item.id);
      this.detectionList = this.detectionList.filter(list => !dialogIds.includes(list.id));
      this.showHideDialog= false;
      this.dialogItems = [];
    }

  },
}
</script>

<style scoped>

</style>
