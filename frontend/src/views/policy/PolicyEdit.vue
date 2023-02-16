<template>
  <contents-layout class="mt-5">
    <template #title>
      <h3 class="mr-5">{{ policy.name }} {{`(v${policy.version})`}}</h3>
      <policy-form-dialog :dialog="policyImportDialog"></policy-form-dialog>
    </template>
    <template #content>
      <contents-layout>
        <template #title>
          <h4>{{ $t('policyEdit.canDbListComponent.title') }}</h4>
        </template>
        <template #control>
          <v-btn color="mainBlue" class="white--text canDbButton"><v-icon size="medium">mdi-plus</v-icon>{{ $t('policyEdit.addButton') }}</v-btn>
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
                <v-btn plain small color="mainBlue">{{ $t('policyEdit.canDbListComponent.body.edit') }}</v-btn>
                <v-btn plain small color="mainBlue">{{ $t('policyEdit.canDbListComponent.body.download') }}</v-btn>
                <v-btn plain small color="mainBlue">{{ $t('policyEdit.canDbListComponent.body.view') }}</v-btn>
                <v-btn plain small color="mainRed">{{ $t('policyEdit.canDbListComponent.body.delete') }}</v-btn>
              </template>
            </v-data-table>
          </v-card>
        </template>
      </contents-layout>
      <contents-layout>
        <template #title>
          <br>
        </template>
        <template #content>
          <v-card>
            <v-tabs v-model="currentTab" color="mainBlue" grow class="tabMenu">
              <v-tabs-slider></v-tabs-slider>
              <v-tab href="#tab-1">Bus {{ $t('policyEdit.rule')}}<v-badge color="red" dot offset-x="-3"></v-badge></v-tab>
              <v-tab href="#tab-2">Message {{ $t('policyEdit.rule')}}</v-tab>
              <v-tab href="#tab-3">Signal {{ $t('policyEdit.rule')}}</v-tab>
            </v-tabs>
          </v-card>
          <v-tabs-items v-model="currentTab">
            <v-tab-item value="tab-1">
              <edit-bus-rules class="mt-5" :policy-id="policyId" :policy-type="policyType" :history-id="historyId"></edit-bus-rules>
            </v-tab-item>
            <v-tab-item value="tab-2">
              <edit-message-rules class="mt-5" :policy-id="policyId" :policy-type="policyType" :history-id="historyId"></edit-message-rules>
            </v-tab-item>
            <v-tab-item value="tab-3">
              <edit-signal-rules class="mt-5" :policy-id="policyId" :policy-type="policyType" :history-id="historyId"></edit-signal-rules>
            </v-tab-item>
          </v-tabs-items>
        </template>
      </contents-layout>
    </template>
  </contents-layout>
</template>

<script>
import PolicyFormDialog from './PolicyFormDialog';
import EditBusRules from './edit/EditBusRules';
import EditMessageRules from './edit/EditMessageRules';
import EditSignalRules from './edit/EditSignalRules';

export default {
  name: 'PolicyEdit',
  components: { PolicyFormDialog, EditBusRules, EditMessageRules, EditSignalRules },
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
    policyImportDialog() {
      return {
        title: this.$t('policyEdit.canDbListComponent.importPolicyDialog.title'),
        inputs: [
          {
            component: 'v-file-input',
            label: this.$t('policyEdit.canDbListComponent.importPolicyDialog.fileInputLabel'),
            key: 'file',
            value: [],
            attrs: {
              rules: [
                value => !!value || 'Import Policy File is Required!',
              ],
              outlined: true,
              showSize: true,
              accept: '.json'
            }
          },
          {
            component: 'v-text-field',
            label: this.$t('policyEdit.canDbListComponent.importPolicyDialog.nameInputLabel'),
            key: 'policyName',
            value: '',
            attrs: {
              rules: [
                value => !!value || 'Policy Name is Required!',
              ],
              outlined: true,
            }
          },
        ],
        trigger: {
          title: this.$t('policyEdit.canDbListComponent.importPolicyDialog.trigger'),
          attrs: {
            outlined: true,
          }
        },
        submit: {
          title: this.$t('policyEdit.canDbListComponent.importPolicyDialog.addButton'),
          cancel: this.$t('policyEdit.canDbListComponent.importPolicyDialog.cancelButton'),
          action: () => { console.log('submit executed\n' + `file: ${this.dialog.inputs[0].value}\n policy: ${this.dialog.inputs[1].value}`) }
        }
      };
    }
  },
  data() {
    return {
      currentTab: null,
      canDbList: [
        { no: '1', busNumber: '1', canDbName: 'BUS1.dbc', generated: Date.now(), lastUpdated: Date.now() },
        { no: '2', busNumber: '2', canDbName: 'BUS2.dbc', generated: Date.now(), lastUpdated: Date.now() },
        { no: '3', busNumber: '3', canDbName: 'BUS3.dbc', generated: Date.now(), lastUpdated: Date.now() },
        { no: '4', busNumber: '4', canDbName: 'BUS4.dbc', generated: Date.now(), lastUpdated: Date.now() },
        { no: '5', busNumber: '5', canDbName: 'BUS5.dbc', generated: Date.now(), lastUpdated: Date.now() },
      ]
    }
  },
  mounted() {
    this.getDbList();
  },
  methods: {
    async getDbList() {
      this.canDbList = await this.$api.getPolicyDbList(this.policyType, this.policyId, this.historyId);
    }
  },
  props: {
    // URL props
    policyType: String,
    policyId: String,
    historyId: String,

    // 프론트엔드 모킹 전용 오브젝트. 삭제 TODO
    policy: {
      type: Object,
      default: () => new Object({
        id: '1',
        name: 'Policy_ALSVIN_210',
        version: '1.2',
      })
    }
  },
}
</script>

<style scoped>
button {
  text-transform: none;
}
.canDbButton {
  width: 100px;
  font-weight: bold;
}
.tabMenu a {
  text-transform: none;
}
</style>