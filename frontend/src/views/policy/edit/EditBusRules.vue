<template>
  <contents-layout>
    <template #title>
      <h4>{{ $t('policyEdit.busRuleComponent.title') }}</h4>
    </template>
    <template #control>
      <v-btn color="mainBlue" class="white--text ruleButton mr-3">
        <v-icon size="medium">mdi-plus</v-icon>
        {{ $t('policyEdit.addButton') }}
      </v-btn>
      <v-btn outlined color="mainBlue" class="ruleButton">{{ $t('policyEdit.saveButton') }}</v-btn>
    </template>
    <template #content>
      <v-card class="mt-5 pa-5">
        <v-data-table :headers="headers" :items="busData.items" hide-default-footer>
          <template #item.busNumber="{ item }">
            <v-select dense outlined hide-details :items="busData.busNumbers" v-model="item.busNumber"></v-select>
          </template>
          <template #item.ruleType="{ item }">
            <v-select dense outlined hide-details :items="busData.ruleTypes" v-model="item.ruleType"></v-select>
          </template>
          <template #item.delete="{ item }">
            <v-btn icon small>
              <v-icon>delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-card>
    </template>
  </contents-layout>
</template>

<script>

export default {
  name: 'EditBusRules',
  props: {
    policyType: String,
    policyId: String,
    historyId: String,
  },
  mounted() {
    this.getBusData();
  },
  computed: {
    headers() {
      return [
        {text: this.$t('policyEdit.busRuleComponent.header.no'), value: 'no', align: 'center', sortable: false},
        {text: this.$t('policyEdit.busRuleComponent.header.busNumber'), value: 'busNumber', align: 'center', sortable: false, width: '10%'},
        {text: this.$t('policyEdit.busRuleComponent.header.ruleType'), value: 'ruleType', align: 'center', sortable: false, width: '25%'},
        {text: this.$t('policyEdit.busRuleComponent.header.expectedValue'), value: 'expectedValue', align: 'center', sortable: false, width: '50%'},
        {text: this.$t('policyEdit.busRuleComponent.header.delete'), value: 'delete', align: 'center', sortable: false},
      ];
    }
  },
  methods: {
    async getBusData() {
      this.busData = await this.$api.getPolicyBusData(this.policyType, this.policyId, this.historyId);
    }
  },
  data() {
    return {
      busData: {
        busNumbers: [],
        ruleTypes: [],
        items: []
      },
    }
  }
}
</script>

<style scoped>
.ruleButton {
  text-transform: none;
  width: 100px;
  font-weight: bold;
}
</style>