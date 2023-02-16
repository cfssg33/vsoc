<template>
  <v-container id="page-platform-detail">
    <v-card class="mt-4">
      <v-tabs v-model="tab" color="mainBlue" slider-size="2" grow>
        <v-tabs-slider></v-tabs-slider>
        <v-tab v-for="menu in menuItems" @click="selectedMenu = menu">
          <span class="font-weight-bold"> {{ menu.title }} </span>
        </v-tab>
      </v-tabs>
    </v-card>
    <v-tabs-items v-model="tab">
      <v-tab-item v-for="menu in menuItems">
        <component :is="menu.component" :platform-id="platformId"></component>
      </v-tab-item>
    </v-tabs-items>
  </v-container>
</template>

<script>
import PlatformInfo from './platformDetail/PlatformInfo';
import VehicleGroup from './platformDetail/VehicleGroup';

export default {
  props: {
    platformId: String,
  },
  components: {
    PlatformInfo, VehicleGroup
  },
  data() {
    return {
      tab: null,
      selectedMenu: {},
    }
  },
  computed: {
    menuItems() {
      return [
        {title: this.$t('vehiclePlatformDetail.information'), component: 'PlatformInfo'},
        {title: this.$t('vehiclePlatformDetail.vehicleGroup'), component: 'VehicleGroup'},
      ]
    }
  }
}
</script>

<style scoped>
</style>