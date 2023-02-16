<template>
  <v-container id="page-admin-list" style="max-width: none;">
    <div class="page-title">{{ $t('apiLog.pageTitle') }}</div>

    <c-table
        :headers="apiLogTableHeaders"
        item-key="id"
        :items="apiLogList"
    >
    </c-table>

  </v-container>
</template>

<script>

export default {
  name: 'ApiLog',
  computed: {
    apiLogTableHeaders() {
      return [
        {text: this.$t('apiLog.createdTime'), value: 'createdTime', align: 'center', sortable: true},
        {text: this.$t('apiLog.url'), value: 'url', align: 'center', sortable: true},
        {text: this.$t('apiLog.method'), value: 'method', align: 'center', sortable: false},
        {text: this.$t('apiLog.level'), value: 'level', align: 'center', sortable: false},
        {text: this.$t('apiLog.sourceIp'), value: 'sourceIp', align: 'center', sortable: true},
        {text: this.$t('apiLog.request'), value: 'request', align: 'center', sortable: false},
        {text: this.$t('apiLog.response'), value: 'response', align: 'center', sortable: false},
        {text: this.$t('apiLog.processingTime'), value: 'processingTime', align: 'center', sortable: true},
      ];
    }
  },
  data() {
    return {
      apiLogList: [],
    }
  },
  async mounted() {
    this.apiLogList = await this.$api.getApiLogList();
  }
}
</script>

<style scoped>
.page-title {
  font-size: 21px;
  font-weight: bold;
  color: #515461;
}
</style>
