<template>
  <v-container>
   <contents-layout>
     <template #title>
       <div class="page-title">{{$t('vehiclePlatformDetail.canDBList') }}</div>
     </template>
     <template #control>
       <v-btn outlined color="mainBlue">{{$t('vehiclePlatformDetail.downloadAll')}}</v-btn>
     </template>
     <template #content>
       <v-card class="mt-5 pa-5">
         <v-data-table hide-default-footer :headers="canDbHeaders" :items="canDbList">
           <template #item.generated="{ item }">
             {{ item.generated | formatDateTimeWithDot }}
           </template>
           <template #item.lastUpdated="{ item }">
             {{ item.lastUpdated | formatDateTimeWithDot }}
           </template>
           <template #item.actions="{ item }">
             <v-btn plain small color="mainBlue">{{ $t('policyEdit.canDbListComponent.body.view') }}</v-btn>
             <v-btn plain small color="mainBlue">{{ $t('policyEdit.canDbListComponent.body.download') }}</v-btn>
           </template>
         </v-data-table>
       </v-card>
     </template>
   </contents-layout>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      canDbList: [],
    }
  },
  props: {
    platformId: String,
  },
  mounted() {
    this.getDbList();
  },
  methods: {
    async getDbList() {
      this.canDbList = await this.$api.getPlatformDbData(this.platformId);
    }
  },
  computed: {
    canDbHeaders() {
      return [
        { text: this.$t('policyEdit.canDbListComponent.header.no'), value: 'no', align: 'center', sortable: false },
        { text: this.$t('policyEdit.canDbListComponent.header.busNumber'), value: 'busNumber', align: 'center', sortable: false },
        { text: this.$t('policyEdit.canDbListComponent.header.canDbName'), value: 'canDbName', align: 'center', sortable: false },
        { text: this.$t('policyEdit.canDbListComponent.header.generated'), value: 'generated', align: 'center', sortable: false },
        { text: this.$t('policyEdit.canDbListComponent.header.lastUpdated'), value: 'lastUpdated', align: 'center', sortable: false },
        { text: this.$t('policyEdit.canDbListComponent.header.actions'), value: 'actions', align: 'center', sortable: false },
      ];
    },
  }
}
</script>

<style scoped>

</style>