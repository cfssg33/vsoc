<template>
  <div class="dashboard-info-card" style="flex: 1;">
    <v-card
      :style="{ padding: '12px', display: 'flex', flexDirection: 'row' }"
    >
      <v-card-title style="padding: 0 0 24px 0">
        <div
          style="display: flex; flex-direction: column; flex-grow: 2; padding-left: 0.15vw;"
        >
          <div style="font-size: 1.3rem;">
            {{ title }}
          </div>
          <div
            style="font-size: 1.0rem;"
            v-if="datas.subTitles"
            v-for="subTitle in datas.subTitles"
            :key="subTitle"
          >
            {{ $t(`monitoring.${subTitle}`) }}
          </div>
        </div>
      </v-card-title>
      <div
        class="chart-container"
        style="flex-grow: 10; text-align: right;"
        v-if="datas.datas || datas.alarms"
      >
        <div
          v-if="datas.datas"
          v-for="(data, index) in datas.datas"
          :key="index"
          :style="{
            ...cardStyle,
            flexGrow: 1,
            color: colors[index % colors.length],
          }"
        >
          {{ data }}
        </div>
        <emergency-alarm
          v-if="datas.alarms"
          v-for="(alarm, index) in datas.alarms"
          :key="alarm.pk"
          :title="alarm.name"
          :date="alarm.createdAt"
          :redirectTo="datas.redirectTo"
          :redirectedKeyword="alarm.pk"
          :style="{
            ...cardStyle,
            fontSize: '0.95rem',
            flexGrow: 1,
          }"
        ></emergency-alarm>
      </div>
    </v-card>
  </div>
</template>

<script>
import EmergencyAlarm from './EmergencyAlarm.vue';

export default {
  components: { EmergencyAlarm },
  name: 'DashboardInfoCard',
  props: {
    chartWidth: {
      type: Number,
    },
    chartHeight: {
      type: Number,
    },
    title: {
      type: String,
      default: () => '',
    },
    datas: {
      type: Object,
      default: () => {},
    },
    cardStyle: {
      type: Object,
      default: () => new Object({}),
    },
    cardInfo: {
      type: Object,
      default: () => new Object({}),
    },
    colors: {
      type: Array,
      default: () => ['black'],
    },
    height: {
      type: Number,
      default: 100,
    },
    redirectTo: {
      type: String
    }
  },
  mounted() { },
};
</script>

<style></style>
