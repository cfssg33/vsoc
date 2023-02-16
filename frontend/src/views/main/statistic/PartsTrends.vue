<template>
  <contents-layout>
    <template #title>
      <div class="page-subtitle">{{ $t('trendsAnalytics.partsTrends') }}</div>
    </template>
    <template #control>
      <v-menu
          v-model="startDateFlag"
          transition="scale-transition"
          offset-y
      >
        <template #activator="{ on }">
          <div style="width: 200px">
            <v-text-field
                v-model="startDate"
                prepend-icon="event"
                readonly
                v-on="on"
                :label="$t('trendsAnalytics.startDate')"
            ></v-text-field>
          </div>
        </template>
        <c-date-picker v-model="startDate" @input="startDateFlag = false" no-title firstDayOfWeek="1"
                       :selectedDate="startDate"></c-date-picker>
      </v-menu>
      <v-menu
          v-model="startTimeFlag"
          :close-on-content-click="false"
          offset-y
      >
        <template #activator="{ on, attrs }">
          <v-text-field
              v-model="startTime"
              prepend-icon="mdi-clock"
              readonly
              hide-details
              v-bind="attrs"
              v-on="on"
              style="max-width: 120px"
          ></v-text-field>
        </template>
        <time-picker v-model="startTime" @close="startTimeFlag = false"></time-picker>
      </v-menu>
      <v-menu
          v-model="endDateFlag"
          transition="scale-transition"
          offset-y
      >
        <template #activator="{ on }">
          <div style="width: 200px" class="ml-5">
            <v-text-field
                v-model="endDate"
                prepend-icon="event"
                readonly
                v-on="on"
                :label="$t('trendsAnalytics.endDate')"
            ></v-text-field>
          </div>
        </template>
        <c-date-picker v-model="endDate" @input="endDateFlag = false" no-title firstDayOfWeek="1"
                       :selectedDate="startDate"></c-date-picker>
      </v-menu>
      <v-menu
          v-model="endTimeFlag"
          :close-on-content-click="false"
          offset-y
      >
        <template #activator="{ on, attrs }">
          <v-text-field
              v-model="endTime"
              prepend-icon="mdi-clock"
              readonly
              hide-details
              v-bind="attrs"
              v-on="on"
              style="max-width: 120px"
          ></v-text-field>
        </template>
        <time-picker v-model="endTime" @close="endTimeFlag = false"></time-picker>
      </v-menu>
      <v-text-field style="width: 500px;" dense outlined label="VID" append-icon="search"
                    class="mt-2 ml-3"></v-text-field>
    </template>
    <template #content>
      <v-card class="pa-4">
        <v-card-title>{{ $t('trendsAnalytics.partsSpread') }}</v-card-title>
        <v-card-text>
          <div class="d-flex align-end">
            <div class="flex-grow-0 pr-2">
              <div class="font-weight-bold">ECU</div>
            </div>
            <div class="flex-grow-1" style="width: 100%">
              <c-chart-bar
                  :labels="labels"
                  :datasets="datasets"
                  :legend="legend"
                  :scales="scales"
              />
            </div>
          </div>
        </v-card-text>
      </v-card>
    </template>
  </contents-layout>
</template>

<script>
export default {
  name: 'PartsTrends',
  data() {
    return {
      startDateFlag: false,
      startTimeFlag: false,
      endDateFlag: false,
      endTimeFlag: false,
      startDate: this.$moment().subtract(1, 'day').format('YYYY-MM-DD'),
      startTime: '00:00',
      endDate: this.$moment().format('YYYY-MM-DD'),
      endTime: '00:00',

      legend: {
        display: false,
      },
      scales: {
        yAxes: [{
          gridLines: {
            drawBorder: false,
          },
          ticks: {
            stepSize: 10,
            display: false,
          }
        }],
        xAxes: [{
          gridLines: {
            display: false,
          },
        }],
      },
      datasets: [],

      dataList: [],
    }
  },
  computed: {
    labels() {
      return this.globalFunc.getDaysLabel(this.$moment(this.endDate), this.dataList.length);
    }
  },
  mounted() {
    this.fillData();
  },
  methods: {
    async reqPartsTrendsData() {
      const res = await this.$api.getPartsTrendsData();
      this.dataList = res;
    },
    async fillData() {
      await this.reqPartsTrendsData();
      this.datasets = [
        {
          backgroundColor: '#f48383',
          borderWidth: 1,
          maxBarThickness: 7,
          data: this.dataList,
        }
      ];
    }
  },

}
</script>

<style scoped>

</style>
