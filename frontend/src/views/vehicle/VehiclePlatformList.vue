<template>
  <contents-layout style="max-width: none;">
    <template #title>
      <div class="page-title">{{ $t('routeName.vehiclePlatformList') }}</div>
    </template>
    <template #control>
      <v-btn large color="mainBlue" class="mt-1 white--text control-button font-weight-bold"><v-icon size="medium">mdi-plus</v-icon>{{$t('vehiclePlatformList.addPlatform')}}</v-btn>
      <v-btn large color="mainRed" class="mt-1 ml-2 white--text control-button font-weight-bold"><v-icon size="medium">mdi-minus</v-icon>{{$t('vehiclePlatformList.deletePlatform')}}</v-btn>
      <div style="width: 350px;" class="ml-2">
        <v-text-field
            solo
            v-model="searchKeyword"
            prepend-inner-icon="search"
            :placeholder="$t('vehiclePlatformList.nameSearch')"
            hide-details
        ></v-text-field>
      </div>
    </template>
    <template #content>
      <v-data-table
          :headers="platformHeader"
          itemKey="id"
          :items="platformList"
          show-select
          hide-default-footer
      >
        <template #item.policyStatus="{ item }">
          <span :class="policyStatusColor(item)">{{ item.policyPublishedStatus }}</span>
        </template>
        <template #item.detail="{ item }">
          <v-btn icon @click="onMoveDetail(item)">
            <v-icon>search</v-icon>
          </v-btn>
        </template>
      </v-data-table>
    </template>
  </contents-layout>
</template>

<script>
export default {
  data: () => ({
    platformList: [],
    searchKeyword: '',
  }),
  mounted() {
    this.reqVehiclePlatformList()
  },
  computed: {
    platformHeader() {
      return  [
        { text: this.$t('vehiclePlatformList.number'), value: 'no', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.name'), value: 'name', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.registeredDate'), value: 'registeredDate', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.numberOfVehicleModel'), value: 'modelNum', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.numberOfVehicleGroup'), value: 'groupNum', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.numberOfVehicle'), value: 'vehicleNum', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.policyPublishedStatus'), value: 'policyStatus', align: 'center', sortable: false },
        { text: this.$t('vehiclePlatformList.detail'), value: 'detail', align: 'center', sortable: false },
      ];
    }
  },
  methods: {
    async reqVehiclePlatformList() {
      const res = await this.$api.getPlatformList();
      this.platformList = res.map((item, idx) => {
        const newItem = {...item};
        newItem.no = idx + 1;
        return newItem;
      });
    },
    onMoveDetail(item) {
      this.$router.push(`/vehicle-platforms/${item.id}`);
    },
    policyStatusColor(item) {
      return item.policyPublishedStatus === 'Registered' ? 'contentBlue--text' : 'contentRed--text';
    }
  }
}
</script>

<style scoped>
.control-button {
  font-size: 12px;
  text-transform: none;
}
</style>