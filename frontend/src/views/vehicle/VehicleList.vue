<template>
  <contents-layout style="max-width: none;">
    <template #control>
<!--      <v-btn large color="mainBlue" class="white&#45;&#45;text control-button font-weight-bold"><v-icon size="medium">mdi-plus</v-icon>{{ $t('vehicleManagement.addButton') }}</v-btn>-->
<!--      <v-btn large color="mainRed" class="ml-2 white&#45;&#45;text control-button font-weight-bold"><v-icon size="medium">mdi-minus</v-icon>{{ $t('vehicleManagement.deleteButton') }}</v-btn>-->
<!--      <div style="width: 350px;" class="ml-2">-->
<!--        <v-text-field-->
<!--            solo-->
<!--            v-model="searchKeyword"-->
<!--            prepend-inner-icon="search"-->
<!--            :placeholder="$t('vehicleManagement.vehicleSearchBarPlaceholder')"-->
<!--            hide-details-->
<!--        ></v-text-field>-->
<!--      </div>-->
    </template>
    <template #content>
      <v-data-table
          :headers="headers"
          itemKey="vehicleId"
          :items="vehicles"
          show-select
          disable-pagination
          hide-default-footer
      >
        <template #item.registeredDate="{ item }">
          {{ item.registeredDate | formatDate }}
        </template>
        <template #item.detail="{ item }">
          <router-link
              :to="{
                name: 'vehicleDetails',
                query: {vehicleId: item.vehicleId}
              }"
              style="text-decoration: none">
            <v-icon>search</v-icon>
          </router-link>

        </template>
      </v-data-table>
    </template>
  </contents-layout>
</template>
<script>
export default {
  computed: {
    headers() {
      return [
        { text: this.$t('vehicleManagement.vehicleListHeader.vehicleId'),value: 'vehicleId', align: 'center', sortable: false},
        { text: this.$t('vehicleManagement.vehicleListHeader.policy'),value: 'policy', align: 'center', sortable: false },
        { text: this.$t('vehicleManagement.vehicleListHeader.version'),value: 'version', align: 'center', sortable: false },
        { text: this.$t('vehicleManagement.vehicleListHeader.registeredDate'),value: 'registeredDate', align: 'center', sortable: false },
        { text: this.$t('vehicleManagement.vehicleListHeader.detail'),value: 'detail', align: 'center', sortable: false }
      ];
    }
  },
  mounted() {
    this.getVehicles();
  },
  data() {
    return {
      vehicles: [],
      searchKeyword: '',
    }
  },
  methods: {
    async getVehicles() {
      this.vehicles = await this.$api.getVehicleList();
    },
  },
  name: 'VehicleList'
}
</script>

<style scoped>
.control-button {
  font-size: 12px;
  text-transform: none;
}
</style>
