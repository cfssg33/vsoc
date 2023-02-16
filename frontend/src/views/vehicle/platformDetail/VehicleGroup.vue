<template>
  <v-container>
    <div class="page-title">{{$t('vehiclePlatformDetail.canDBList') }}</div>
    <v-row justify="start">
      <v-col cols="12" md="3" sm="4">
        <c-board :title="$t('vehiclePlatformDetail.vehicles')" :value="stat.vehiclesNum"></c-board>
      </v-col>
      <v-col cols="12" md="3" sm="4">
        <c-board :title="$t('vehiclePlatformDetail.vehicleModels')" :value="stat.modelsNum"></c-board>
      </v-col>
      <v-col cols="12" md="3" sm="4">
        <c-board :title="$t('vehiclePlatformDetail.vehicleGroups')" :value="stat.groupsNum"></c-board>
      </v-col>
    </v-row>

    <contents-layout class="mt-9">
      <template #title>
        <div></div>
      </template>
      <template #control>
        <v-btn large color="mainBlue" class="white--text font-weight-bold mr-2 mt-1">{{ $t('vehiclePlatformDetail.moveVehicles') }}</v-btn>
        <div style="width: 350px;" class="ml-2">
          <v-text-field
              solo
              prepend-inner-icon="search"
              v-model="searchKeyword"
              :placeholder="$t('vehiclePlatformDetail.vehicleIdSearch')"
              hide-details
          ></v-text-field>
        </div>
      </template>
      <template #content>
        <v-card class="my-3">
          <v-tabs-items v-model="tableTab">
            <v-data-table
                :headers="groupTableHeader"
                itemKey="id"
                :items="groupList"
                show-select
                hide-default-footer
            ></v-data-table>
          </v-tabs-items>

          <v-tabs show-arrows v-model="tableTab" color="mainBlue" slider-size="4">
            <v-tab href="#default"><span class="font-weight-bold">{{$t('vehiclePlatformDetail.defaultGroup')}}</span></v-tab>
            <v-tab href="#group1"><span class="font-weight-bold">{{$t('vehiclePlatformDetail.group1')}}</span></v-tab>
            <v-tab href="#group2"><span class="font-weight-bold">{{$t('vehiclePlatformDetail.group2')}}</span></v-tab>
            <v-tab href="#group3"><span class="font-weight-bold">{{$t('vehiclePlatformDetail.group3')}}</span></v-tab>
          </v-tabs>
        </v-card>
      </template>
    </contents-layout>
  </v-container>
</template>
<script>
export default {
  props: {
    platformId: String,
  },
  data() {
    return {
      stat: {
        vehiclesNum: 0,
        modelsNum: 0,
        groupsNum: 0,
      },


      tableTab: null,
      searchKeyword: '',

      default: [],
      group1: [],
      group2: [],
      group3: [],

      groupListObj: {},
    }
  },
  mounted() {
    this.reqGetGroupList();
    this.getPlatformStat();
  },
  computed: {
    groupList() {
      return this.groupListObj[this.tableTab];
    },
    groupTableHeader() {
      return [
        { text: this.$t('vehiclePlatformDetail.number'), value: 'no', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.vehicleId'), value: 'vehicleId', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.brand'), value: 'brand', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.type'), value: 'type', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.size'), value: 'size', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.appliedDate'), value: 'appliedDate', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.appliedPolicy'), value: 'appliedPolicy', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.publishedDate'), value: 'publishedDate', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformDetail.publishedPolicy'), value: 'publishedPolicy', align: 'center', sortable: false },
      ];
    }
  },
  methods: {
    async reqGetGroupList() {
      const res = await this.$api.getVehicleGroupList();
      this.groupListObj = res;
    },
    async getPlatformStat() {
      this.stat = await this.$api.getPlatformStat(this.platformId);
    }
  }
}
</script>

<style scoped>

</style>