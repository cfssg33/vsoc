<template>
  <v-container>
    <v-row>
      <contents-layout>
        <template #title>
          <h3>{{ $t('policyHistoryLogDetail.logDetailComponent.title') }}</h3>
        </template>
        <template #content>
          <info-card class="pa-3 mt-5" :info-data="detailData"></info-card>
        </template>
      </contents-layout>
    </v-row>
    <v-row>
      <contents-layout class="mt-5">
        <template #title>
          <h3>{{ $t('policyHistoryLogDetail.publishedVehiclesComponent.title') }}</h3>
        </template>
        <template #content>
          <v-card class="pa-3 mt-5">
            <v-data-table :headers="headers" :items="items" hide-default-footer>
              <template #item.installationDate="{ item }">
                {{ item.installationDate | formatDateWithDot }}
              </template>
              <template #item.installationStatus="{ item }">
                {{ item.installationStatus ? 'O' : 'X' }}
              </template>
            </v-data-table>
          </v-card>
        </template>
      </contents-layout>
    </v-row>
  </v-container>
</template>

<script>

export default {
  name: 'HistoryLogDetail',
  mounted() {
    this.getLogData();
  },
  props: {
    policyType: String,
    policyId: String,
    historyId: String
  },
  computed: {
    headers() {
      return [
        { text: this.$t('policyHistoryLogDetail.publishedVehiclesComponent.no'), value: 'no', align: 'center', sortable: false },
        { text: this.$t('policyHistoryLogDetail.publishedVehiclesComponent.vehicleId'), value: 'vehicleId', align: 'center', sortable: false },
        { text: this.$t('policyHistoryLogDetail.publishedVehiclesComponent.policy'), value: 'policy', align: 'center', sortable: false },
        { text: this.$t('policyHistoryLogDetail.publishedVehiclesComponent.version'), value: 'version', align: 'center', sortable: false },
        { text: this.$t('policyHistoryLogDetail.publishedVehiclesComponent.installationDate'), value: 'installationDate', align: 'center', sortable: false },
        { text: this.$t('policyHistoryLogDetail.publishedVehiclesComponent.installationStatus'), value: 'installationStatus', align: 'center', sortable: false },
      ];
    }
  },
  data() {
    return {
      detailData: {
        version : { text : this.$t('policyHistoryLogDetail.logDetailComponent.version'), value: '0.9', grid: 4, format: null, fullWidth: true },
        publishedTime : { text : this.$t('policyHistoryLogDetail.logDetailComponent.publishedTime'), value: Date.now(), grid: 4, format: this.$options.filters.formatDateTimeWithDot, fullWidth: true },
        vehicles : {text : this.$t('policyHistoryLogDetail.logDetailComponent.vehicles'), value: 1309, grid: 4, format: this.$options.filters.numberFormatter, fullWidth: true },
        publishedBy : {text : this.$t('policyHistoryLogDetail.logDetailComponent.publishedBy'), value: 'Taehyun Yoo', grid: 4, format: null, fullWidth: true },
      },
      items: []
    }
  },
  methods: {
    async getLogData() {
      this.items = await this.$api.getPolicyLog(this.policyType, this.policyId, this.historyId);
    }
  }
}
</script>

<style scoped>

</style>