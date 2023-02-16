<template>
  <div>
    <contents-layout>
      <template #title>
        <h2>{{ $t('vehicleDetails.detailInfoComponent.title') }}</h2>
      </template>
      <template #content>
        <info-card class="mt-5 pa-5" :info-data="vehicleDetail"></info-card>
      </template>
    </contents-layout>
    <contents-layout>
      <template #title>
        <h2>{{ $t('vehicleDetails.historyLogComponent.title') }}</h2>
      </template>
      <template #content>
        <v-card class="mt-5 pa-5">
          <v-data-table
              :headers="headers"
              :items="histories"
              disable-pagination
              hide-default-footer
          >
            <template #item.appliedTime="{ item }">
              {{ item.appliedTime | formatDateTimeWithDot }}
            </template>
          </v-data-table>
        </v-card>
      </template>
    </contents-layout>
  </div>
</template>

<script>

export default {
  name: 'VehicleDetails',
  computed: {
    headers() {
      return [
        { text: this.$t('vehicleDetails.historyLogComponent.header.no'), value: 'no', align: 'left', sortable: false },
        { text: this.$t('vehicleDetails.historyLogComponent.header.type'), value: 'type', align: 'center', sortable: false },
        { text: this.$t('vehicleDetails.historyLogComponent.header.policy'), value: 'policy', align: 'center', sortable: false },
        { text: this.$t('vehicleDetails.historyLogComponent.header.version'), value: 'version', align: 'center', sortable: false },
        { text: this.$t('vehicleDetails.historyLogComponent.header.publishedTime'), value: 'publishedTime', align: 'center', sortable: false },
        { text: this.$t('vehicleDetails.historyLogComponent.header.publishedBy'), value: 'publishedBy', align: 'center', sortable: false },
        { text: this.$t('vehicleDetails.historyLogComponent.header.appliedTime'), value: 'appliedTime', align: 'center', sortable: false }
      ];
    }
  },
  data() {
    return {
      vehicleDetail: {
        vehicleId: '',
        brand: '',
        type: '',
        size: '',
        registeredDate: '',
        registeredCountry: '',
        lastDate: '',
        lastCountry: '',
        policy: '',
        version: ''
      },
      histories: [
        { no: 4, policy: 'Policy_ALSVIN_210', type: 'HOST', version: '0.9', publishedTime: '2021.09.24 11:22:19', publishedBy: 'Taehyun Yoo', appliedTime: '2021.09.24 16:08:19'},
        { no: 3, policy: 'Policy_ALSVIN_312', type: 'CAN', version: '0.8', publishedTime: '2021.09.24 09:21:07', publishedBy: 'Taehyun Yoo', appliedTime: '2021.09.24 09:50:18'},
        { no: 2, policy: 'Policy_ALSVIN_039', type: 'HOST', version: '0.8', publishedTime: '2021.09.22 21:18:29', publishedBy: 'Taehyun Yoo', appliedTime: '2021.09.23 07:26:03'},
        { no: 1, policy: 'Policy_ALSVIN_495', type: 'CAN', version: '0.8', publishedTime: '2021.09.17 13:05:23', publishedBy: 'Taehyun Yoo', appliedTime: '2021.09.18 11:13:22'}
      ],
    }
  },
  mounted() {
    console.log(this.$route.query.vehicleId);
    this.getVehicleDetail(this.$route.query.vehicleId);
  },
  methods: {
    async getVehicleDetail(vehicleId) {
      this.vehicleDetail = await this.$api.getVehicleDetail(vehicleId);
      console.log(this.vehicleDetail);
    }
  }
}
</script>

<style scoped>

</style>
