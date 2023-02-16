<template>
  <v-dialog v-model="dialogState" max-width="1200">
    <template #activator="{ on, attrs }">
      <v-btn  plain small color="mainBlue" class="white--text" v-on="on" :attrs="attrs">
        {{ triggerTitle }}
      </v-btn>
    </template>
    <v-card class="radius: rounded-xl pa-3 pt-5" elevation="15">
      <v-card-title>{{ $t('policyDetail.versionComponent.publishDialog.title') }}</v-card-title>
      <v-card-subtitle>{{`${policy.name} (v${policy.version})`}}</v-card-subtitle>
      <v-card-text class="pb-0">
        <contents-layout>
          <template #title>
            <h3 class="mr-5">{{ $t('policyDetail.versionComponent.publishDialog.publishTime') }}</h3>
            <v-menu
                v-model="dateMenu"
                :close-on-content-click="false"
                offset-y
            >
              <template #activator="{ on, attrs }">
                <v-text-field
                    v-model="publishAt.date"
                    class="mt-0 pt-0 mr-5"
                    prepend-icon="mdi-calendar"
                    readonly
                    hide-details
                    v-bind="attrs"
                    v-on="on"
                    style="max-width: 120px"
                ></v-text-field>
              </template>
              <v-date-picker v-model="publishAt.date" no-title scrollable></v-date-picker>
            </v-menu>
            <v-menu
                v-model="timeMenu"
                :close-on-content-click="false"
                offset-y
            >
              <template #activator="{ on, attrs }">
                <v-text-field
                    v-model="publishAt.time"
                    class="mt-0 pt-0"
                    prepend-icon="mdi-clock"
                    readonly
                    hide-details
                    v-bind="attrs"
                    v-on="on"
                    style="max-width: 80px"
                ></v-text-field>
              </template>
              <time-picker v-model="publishAt.time" @close="timeMenu = false"></time-picker>
            </v-menu>
          </template>
          <template #content>
            <v-data-table v-model="selected" :headers="headers" :items="policies" show-select single-select hide-default-footer>
              <template #item.registeredDate="{ item }">
                {{ item.registeredDate | formatDateWithDot }}
              </template>
              <template #item.detail="{ item }">
                <v-btn icon @click="toDetailPage(item.id)">
                  <v-icon>search</v-icon>
                </v-btn>
              </template>
            </v-data-table>
          </template>
        </contents-layout>
      </v-card-text>
      <v-card-actions class="d-flex justify-center align-content-space-between pb-5">
        <v-btn class="action-button white--text font-weight-bold mx-5" width="180" large color="mainBlue" @click="publish()" :disabled="!policySelected">
          {{ $t('policyDetail.versionComponent.publishDialog.publishButton') }}
        </v-btn>
        <v-btn class="action-button white--text font-weight-bold mx-5" width="180" large color="buttonGrey" @click="dialogState=false">
          {{ $t('policyDetail.versionComponent.publishDialog.cancelButton') }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "PolicyPublishDialog",
  data() {
    return {
      dateMenu: false,
      timeMenu: false,
      dialogState: false,
      defaultPublishDate: null,
      publishAt: {
        date: this.defaultPublishDate.format('YYYY-MM-DD'),
        time: this.defaultPublishDate.format('kk:mm')
      },
      selected: [],
      policies: [
        { id: 1, no: 1, vehicleId:'LS4A885V1GG001497', policy: 'Policy_ALSVIN_210', version: '0.8', registeredDate: Date.now() },
        { id: 2, no: 2, vehicleId:'LS4A885V1GG001093', policy: 'Policy_ALSVIN_312', version: '0.8', registeredDate: Date.now() },
        { id: 3, no: 3, vehicleId:'LS4A885V1GG004938', policy: 'Policy_ALSVIN_039', version: '0.8', registeredDate: Date.now() },
        { id: 4, no: 4, vehicleId:'LS4A885V1GG000242', policy: 'Policy_ALSVIN_495', version: '0.8', registeredDate: Date.now() },
        { id: 5, no: 5, vehicleId:'LS4A885V1GG409586', policy: 'Policy_ALSVIN_728', version: '0.7', registeredDate: Date.now() },
        { id: 6, no: 6, vehicleId:'LS4A885V1GG458503', policy: 'Policy_ALSVIN_090', version: '0.8', registeredDate: Date.now() },
        { id: 7, no: 7, vehicleId:'LS4A885V1GG304952', policy: 'Policy_ALSVIN_320', version: '0.6', registeredDate: Date.now() },
        { id: 8, no: 8, vehicleId:'LS4A885V1GG568306', policy: 'Policy_ALSVIN_574', version: '0.8', registeredDate: Date.now() },
        { id: 9, no: 9, vehicleId:'LS4A885V1GG029405', policy: 'Policy_ALSVIN_923', version: '0.8', registeredDate: Date.now() },
        { id: 10, no: 10, vehicleId:'LS4A885V1GG495032', policy: 'Policy_ALSVIN_609', version: '0.7', registeredDate: Date.now() },
      ],
    }
  },
  beforeCreate() {
    this.defaultPublishDate = this.$moment().add(1, 'days');
  },
  computed: {
    policySelected() {
      return this.selected.length > 0;
    },
    headers() {
      return [
        { text: this.$t('policyDetail.versionComponent.publishDialog.headers.no'), value: "no", align: "center", sortable: false },
        { text: this.$t('policyDetail.versionComponent.publishDialog.headers.vehicleId'), value: "vehicleId", align: "center", sortable: false },
        { text: this.$t('policyDetail.versionComponent.publishDialog.headers.policy'), value: "policy", align: "center", sortable: false },
        { text: this.$t('policyDetail.versionComponent.publishDialog.headers.version'), value: "version", align: "center", sortable: false },
        { text: this.$t('policyDetail.versionComponent.publishDialog.headers.registeredDate'), value: "registeredDate", align: "center", sortable: false },
        { text: this.$t('policyDetail.versionComponent.publishDialog.headers.detail'), value: "detail", align: "center", sortable: false },
      ];
    }
  },
  methods: {
    publish() {
      console.log("publish : " + this.selected);
    },
    toDetailPage(policyId) {
      console.log('to detail page ' + policyId);
    }
  },
  props: {
    policy: {
      type: Object,
      default: () => new Object({
        id: '1',
        name: 'Policy_ALSVIN_210',
        version: '1.2',
      }),
    },
    triggerTitle: {
      type: String,
      default: 'Publish'
    }
  },
}
</script>

<style scoped>
button {
  text-transform: none;
}
.action-button {
  font-size: 16px !important;
}
</style>