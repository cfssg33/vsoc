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
                  :label="$t('detectionHostIdps.startDate')"
              ></v-text-field>
            </div>
          </template>
          <c-date-picker v-model="startDate" @input="startDateFlag = false" no-title :firstDayOfWeek="1"
                         :selectedDate="startDate"></c-date-picker>
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
                  :label="$t('detectionHostIdps.endDate')"
              ></v-text-field>
            </div>
          </template>
          <c-date-picker v-model="endDate" @input="endDateFlag = false" no-title :firstDayOfWeek="1"
                         :selectedDate="endDate"></c-date-picker>
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

        <v-checkbox :label="$t('detectionHostIdps.alwaysRealTime')" v-model="isAlwaysRealtime"></v-checkbox>
      </template>
      <template #content>
        <v-row>
          <v-col>
            <c-board :title="$t('detectionHostIdps.totalDetections')" :value="overallInfo.totalDetections"></c-board>
          </v-col>
          <v-col>
            <c-board :title="$t('detectionHostIdps.averageDetections')" unit="per min">
              <template #value>
                {{Number(overallInfo.averageDetections).toFixed(2)}}
              </template>
            </c-board>
          </v-col>
          <v-col>
            <c-board :title="$t('detectionHostIdps.detectedVehicles')" :value="overallInfo.detectedVehicles"></c-board>
          </v-col>
          <v-col cols="4" align="center">
            <v-card outlined class="py-4">
              <c-chart-pie
                  style="height:165px; width:250px"
                  :labels="overallInfo.hostIdpsTopFiveRules.ruleId"
                  :datasets="drawPieChart"
                  :legend="{position:'right', labels:{boxWidth:10, padding:5}}"
                  :title="{display: true, fontSize: 20, fontStyle:'normal', text: $t('detectionHostIdps.ruleName')}"
              />
            </v-card>
          </v-col>
        </v-row>
      </template>
    </contents-layout>

    <contents-layout style="max-width: none;">
      <template #title>
        <span class="page-title">{{$t('detectionHostIdps.detectionList')}}</span>
      </template>
      <template #control>
        <v-spacer/>
        <v-switch
          v-model="selectIdpsEthernet"
          color="primary"
          label="IDPS"
          value="IDPS"
          class="mr-4"
          style="margin-top: 0"
          inset
          @change="getDetectionList"
        />
        <v-switch
          v-model="selectIdpsEthernet"
          color="primary"
          label="Ethernet"
          value="Firewall"
          class="mr-4"
          style="margin-top: 0"
          inset
          @change="getDetectionList"
        />
        <v-btn class="mainBlue mr-3" @click="reqExport" dark>{{$t('detectionHostIdps.exportBtn')}}</v-btn>
        <v-text-field
            :label="$t('detectionHostIdps.vehicleIdSearch')"
            v-model="vehicleKeyword"
            @keyup.enter="reqDetectionsByVehicle"
            prepend-inner-icon="search"
            outlined dense hide-details
        />
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
            <v-btn icon @click="hideRule(item)">
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
        <v-card-title>{{ $t('detectionHostIdps.hideRule') }}</v-card-title>
        <v-card-text>
          <span class="contentBlue--text"> {{ hideLogCount }}</span> <span style="white-space: pre-wrap">{{ $t('detectionHostIdps.hideRuleMessage') }}</span>
          <v-data-table :headers="dialogHeader" :items="dialogItems" class="mt-8 elevation-1" hide-default-footer>
          </v-data-table>
        </v-card-text>
        <v-card-actions>
          <v-row justify="center">
            <v-btn class="mx-1" color="contentBlue" dark outlined @click="reqHideRules">{{$t('detectionHostIdps.acceptBtn')}}</v-btn>
            <v-btn class="mx-1" color="contentGrey" dark outlined @click="showHideDialog = false">{{$t('detectionHostIdps.cancelBtn')}}</v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>

export default {
  name: 'DetectionHostIdps',
  data() {
    return {
      detectionList: [],
      vehicleKeyword: '',
      overallInfo: {
        totalDetections: 0,
        averageDetections: 0,
        detectedVehicles: 0,
        hostIdpsTopFiveRules: {
          ruleId: [],
          count: []
        },
      },
      startDateFlag: false,
      startTimeFlag: false,
      endDateFlag: false,
      endTimeFlag: false,
      startDate: this.$moment().format('YYYY-MM-DD'),
      startTime: this.$moment().subtract(1, 'H').format('HH:MM'),
      endDate: this.$moment().format('YYYY-MM-DD'),
      endTime: this.$moment().format('HH:MM'),
      isAlwaysRealtime: false,
      divisionData:{},
      regionList:[],
      cityList:[],
      selectedRegion: '',
      selectedCity: '',
      showHideDialog: false,
      dialogItems: [],
      hideLogCount:0,
      intervalId: 0,
      tableItemsPerPage: 10,
      tablePage: 1,
      totalLogCount: 0,
      totalPage: 0,
      selectIdpsEthernet: ['IDPS', 'Firewall'],
    }
  },
  created() {
    this.vehicleKeyword = this.$route.params.redirectedKeyword;
  },
  computed: {
    drawPieChart() {
      const datasets = [
        {
          backgroundColor: ['#1d6dff', '#ff842f', '#a0a0a0', '#ffce00', '#57abff', '#59d100'],
          data: this.overallInfo.hostIdpsTopFiveRules.count
        }
      ];
      return datasets;
    },
    headers() {
      return [
        {text: this.$t('detectionHostIdps.detectedTime'), value: 'detectedTime', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.vehicleId'), value: 'vehicleId', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.logType'), value: 'logType', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.policy'), value: 'policy', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.version'), value: 'version', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.ruleName'), value: 'ruleName', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.protocol'), value: 'protocol', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.srcIp'), value: 'srcIp', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.srcPort'), value: 'srcPort', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.dstIp'), value: 'dstIp', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.dstPort'), value: 'dstPort', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.severity'), value: 'severity', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.labeling'), value: 'label', align:'center', sortable:true, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.hide'), value: 'hidden', align:'center', sortable:true, class:'font-weight-bold'},
      ]
    },
    dialogHeader() {
      return [
        {text: this.$t('detectionHostIdps.policy'), value: 'policy', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.version'), value: 'version', align:'center', sortable:false, class:'font-weight-bold'},
        {text: this.$t('detectionHostIdps.ruleName'), value: 'ruleName', align:'center', sortable:false, class:'font-weight-bold'},
      ]
    },
  },
  watch: {
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
      let over = await this.$api.getHostIdpsOverall(startDateTime, endDateTime);

      this.overallInfo.totalDetections = over.totalDetections;
      this.overallInfo.averageDetections = over.averageDetections;
      this.overallInfo.detectedVehicles = over.detectedVehicles;

      for (let value in over.hostIdpsTopFiveRules) {
        this.overallInfo.hostIdpsTopFiveRules.ruleId.push(over.hostIdpsTopFiveRules[value].ruleId);
        this.overallInfo.hostIdpsTopFiveRules.count.push(over.hostIdpsTopFiveRules[value].count);
      }
    },
    async getDetectionList() {
      if (this.selectIdpsEthernet.length === 0) {
        this.detectionList = [];
        this.totalLogCount = 0;
        this.totalPage = 0;
      }

      let startDateTime = this.startDate + ' ' + this.startTime + ':00';
      let endDateTime = this.endDate + ' ' + this.endTime + ':00';
      let type = this.selectIdpsEthernet.length === 2 ? 'All' : this.selectIdpsEthernet[0];

      const result = await this.$api.getHostIdpsLogList(type, startDateTime, endDateTime, this.tableItemsPerPage, this.tablePage);
      this.detectionList = result.hostIdpsLogList;
      this.totalLogCount = result.totalCount;
      this.totalPage = result.totalPage;
    },
    async reqExport() {
      await this.$api.exportHostIdpsLog();
    },
    reqDetectionsByVehicle() {
      if (this.vehicleKeyword.length < 1) {
      }
    },
    reqOverallInfoAndDetectionList() {
      this.getOverallInfo();
      this.getDetectionList();
    },
    async reqUpdateLabel(item) {
      await this.$api.changeHostIdpsLogLabel(item.uid, item.label);
    },
    hideRule(item) {
      this.dialogItems = this.detectionList.filter(v => v.ruleName === item.ruleName);
      this.hideLogCount = this.dialogItems.length;
      this.showHideDialog = true;
    },
    reqHideRules() {
      const dialogIds = this.dialogItems.map(item => item.id);
      this.detectionList = this.detectionList.filter(list => !dialogIds.includes(list.id));
      this.showHideDialog= false;
      this.dialogItems = [];
    },
    needRenew() {
    }
  }
}
</script>

<style scoped>

</style>
