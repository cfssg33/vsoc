<template>
  <contents-layout>
    <template #title>
      <h4>{{ $t('policyEdit.signalRuleComponent.title') }}</h4>
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
        <v-data-table :headers="headers" :items="items" hide-default-footer>
          <template #item.busNumber="{ item }">
            <v-select dense outlined hide-details :items="busNumbers" v-model="item.busNumber"></v-select>
          </template>
          <template #item.messageId="{ item }">
            <v-select dense outlined hide-details :items="messageTypes" v-model="item.messageId"></v-select>
          </template>
          <template #item.signalId="{ item }">
            <v-select dense outlined hide-details :items="signalTypes" v-model="item.signalId"></v-select>
          </template>
          <template #item.ruleType="{ item }">
            <v-select dense outlined hide-details :items="ruleTypes" v-model="item.ruleType"></v-select>
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
  name: 'EditSignalRules',
  props: {
    policyType: String,
    policyId: String,
    historyId: String,
  },
  computed: {
    headers() {
      return [
        { text: this.$t('policyEdit.signalRuleComponent.header.no'), value: 'no', align: 'center', sortable: false },
        { text: this.$t('policyEdit.signalRuleComponent.header.busNumber'), value: 'busNumber', align: 'center', sortable: false, width: '10%' },
        { text: this.$t('policyEdit.signalRuleComponent.header.messageId'), value: 'messageId', align: 'center', sortable: false, width: '15%' },
        { text: this.$t('policyEdit.signalRuleComponent.header.signalId'), value: 'signalId', align: 'center', sortable: false, width: '15%' },
        { text: this.$t('policyEdit.signalRuleComponent.header.ruleType'), value: 'ruleType', align: 'center', sortable: false, width: '25%' },
        { text: this.$t('policyEdit.signalRuleComponent.header.expectedValue'), value: 'expectedValue', align: 'center', sortable: false, width: '20%' },
        { text: this.$t('policyEdit.signalRuleComponent.header.delete'), value: 'delete', align: 'center', sortable: false },
      ];
    }
  },
  data() {
    return {
      busNumbers: ['1','2','3','4','5'],
      messageTypes: ['GW_258(0x258)', 'GW_259(0x259)', 'GW_25A(0x25A)', 'GW_25B(0x25B)'],
      signalTypes: ['ESP_MasCylBrake'],
      ruleTypes: ['SIGNAL_SPACE_VIOLATION', 'CRC_SIGNAL_ERROR', 'COUNTER_SIGNAL_SEQ_ERROR', 'CHECKSUM_SIGNAL_ERROR', 'ENUM_SIGNAL_ERROR', 'SIGNAL_UNDERFLOW', 'SIGNAL_OVERFLOW'],
      items: [
        { no : '1',  busNumber : '1', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'SIGNAL_SPACE_VIOLATION',  expectedValue : '' },
        { no : '2',  busNumber : '2', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'SIGNAL_SPACE_VIOLATION',  expectedValue : '' },
        { no : '3',  busNumber : '3', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'SIGNAL_SPACE_VIOLATION',  expectedValue : '' },
        { no : '4',  busNumber : '4', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'SIGNAL_SPACE_VIOLATION',  expectedValue : '' },
        { no : '5',  busNumber : '5', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'SIGNAL_SPACE_VIOLATION',  expectedValue : '' },
        { no : '6',  busNumber : '1', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'CHECKSUM_SIGNAL_ERROR',  expectedValue : '' },
        { no : '7',  busNumber : '2', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'CHECKSUM_SIGNAL_ERROR',  expectedValue : '' },
        { no : '8',  busNumber : '3', messageId : 'GW_258(0x258)', signalId: 'ESP_MasCylBrake',  ruleType : 'CHECKSUM_SIGNAL_ERROR',  expectedValue : '' },
      ]
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