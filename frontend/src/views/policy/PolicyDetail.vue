<template>
<contents-layout>
  <template #title>
    <h2>{{ policy.name }}</h2>
  </template>
  <template #content>
    <contents-layout class="pa-0">
      <template #title>
        <h3 class="mr-5">{{ $t('policyDetail.versionComponent.title') }}</h3>
        <policy-form-dialog :dialog="policyCreationDialog"></policy-form-dialog>
      </template>
      <template #content>
        <v-card class="mt-3">
          <v-data-table
              :headers="versionHeaders"
              :items="policy.version.items"
              hide-default-footer
          >
            <template #item.modified="{ item }">{{ item.modified | formatDateTimeWithDot }}</template>
            <template #item.published="{ item }">{{ item.published | formatDateTimeWithDot }}</template>
            <template #item.action="{ item }">
              <v-btn plain small color="mainBlue" v-if="item.status === 'Editing'" @click="toPolicyEditPage(item.version)">{{ $t('policyDetail.versionComponent.table.body.edit') }}</v-btn>
              <policy-publish-dialog :trigger-title="$t('policyDetail.versionComponent.table.body.publish')"></policy-publish-dialog>
              <v-btn plain small color="mainBlue">{{ $t('policyDetail.versionComponent.table.body.view') }}</v-btn>
              <v-btn plain small color="mainRed" v-if="item.status === 'Editing'">{{ $t('policyDetail.versionComponent.table.body.delete') }}</v-btn>
            </template>
          </v-data-table>
        </v-card>
      </template>
    </contents-layout>
    <contents-layout class="pa-0 mt-10">
      <template #title>
        <h3>{{ $t('policyDetail.historyLogComponent.title') }}</h3>
      </template>
      <template #content>
        <v-card class="mt-3">
          <v-data-table :headers="historyHeaders"
                        :items="policy.history.items"
                        hide-default-footer
          >
            <template #item.vehicles="{ item }">{{ item.vehicles | numberFormatter }}</template>
            <template #item.publishedTime="{ item }">{{ item.publishedTime | formatDateTimeWithDot }}</template>
            <template #item.action="{ item }">
              <v-btn plain small color="mainRed" v-if="item.installationRate === null">{{ $t('policyDetail.historyLogComponent.table.body.cancel') }}</v-btn>
              <v-btn plain small color="mainRed" v-else>{{ $t('policyDetail.historyLogComponent.table.body.rollback') }}</v-btn>
            </template>
            <template #item.detail="{ item }">
              <v-btn icon @click="toHistoryDetailPage(item.id)">
                <v-icon>search</v-icon>
              </v-btn>
            </template>
          </v-data-table>
        </v-card>
      </template>
    </contents-layout>
  </template>
</contents-layout>
</template>

<script>
import PolicyFormDialog from './PolicyFormDialog';
import PolicyPublishDialog from './PolicyPublishDialog';
import policyAPI from '../../api/policy';

export default {
  components: {PolicyFormDialog, PolicyPublishDialog},
  name: 'PolicyDetail',
  mounted() {
    this.getPolicyDetail();
  },
  methods: {
    toHistoryDetailPage(historyId) {
      this.$router.push(`${this.$router.currentRoute.path}/history/${historyId}`);
    },
    toPolicyEditPage(version) {
      this.$router.push(`${this.$router.currentRoute.path}/edit/${version}`);
    },
    async getPolicyDetail() {
      this.policy = {...await policyAPI.getPolicyDetail(this.policyType, this.policyId)};
    }
  },
  computed: {
    versionHeaders() {
      return [
        {
          text: this.$t('policyDetail.versionComponent.table.header.version'),
          value: 'version',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.versionComponent.table.header.status'),
          value: 'status',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.versionComponent.table.header.modified'),
          value: 'modified',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.versionComponent.table.header.modifiedBy'),
          value: 'modifiedBy',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.versionComponent.table.header.published'),
          value: 'published',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.versionComponent.table.header.publishedBy'),
          value: 'publishedBy',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.versionComponent.table.header.action'),
          value: 'action',
          align: 'center',
          sortable: false
        },
      ];
    },
    historyHeaders() {
      return [
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.no'),
          value: 'no',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.version'),
          value: 'version',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.vehicles'),
          value: 'vehicles',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.publishedTime'),
          value: 'publishedTime',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.publishedBy'),
          value: 'publishedBy',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.installationRate'),
          value: 'installationRate',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.action'),
          value: 'action',
          align: 'center',
          sortable: false
        },
        {
          text: this.$t('policyDetail.historyLogComponent.table.header.detail'),
          value: 'detail',
          align: 'center',
          sortable: false
        },
      ];
    },
    policyCreationDialog() {
      return {
        title: this.$t('policyDetail.versionComponent.createDialog.title'),
        inputs: [
          {
            component: 'v-file-input',
            label: this.$t('policyDetail.versionComponent.createDialog.fileInputLabel'),
            key: 'file',
            value: [],
            attrs: {
              rules: [
                value => !!value || 'Import Policy File is Required!',
              ],
              outlined: true,
              showSize: true,
              placeholder: 'Please import a policy file......',
              accept: '.json'
            }
          },
          {
            component: 'v-text-field',
            label: this.$t('policyDetail.versionComponent.createDialog.versionInputLabel'),
            key: 'version',
            value: '',
            attrs: {
              rules: [
                value => !!value || 'Policy Version is Required!',
              ],
              placeholder: 'Please enter policy version......',
              outlined: true,
            }
          }
        ],
        trigger: {
          title: this.$t('policyDetail.versionComponent.createDialog.triggerButton'),
        },
        submit: {
          title: this.$t('policyDetail.versionComponent.createDialog.createButton'),
          cancel: this.$t('policyDetail.versionComponent.createDialog.cancelButton'),
          action: () => {
            console.log('submit executed\n' + `file: ${this.dialog.inputs[0].value}\n version: ${this.dialog.inputs[1].value}`)
          }
        }
      };
    }
  },
  props: {
    // URL props
    policyType: String,
    policyId: String,
  },
  data() {
    return {
      policy: {
        name: '',
        version: {
          items: []
        },
        history: {
          items: []
        },
      }
    }
  }
}
</script>

<style scoped>
button {
  text-transform: none;
}
.control-button {
  font-size: 12px;
}
</style>