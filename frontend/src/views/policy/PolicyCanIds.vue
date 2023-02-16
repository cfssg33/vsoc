<template>
  <contents-layout class="mt-5" style="max-width: none;">
    <template #title>
      <h2>{{ $t('policyCanIds.pageTitle') }}</h2>
    </template>
    <template #control>
      <policy-form-dialog :dialog="policyImportDialog"></policy-form-dialog>
      <div style="width: 350px;" class="ml-2">
        <v-text-field prepend-inner-icon="search" solo :placeholder="$t('policyCanIds.policySearchBarPlaceholder')"></v-text-field>
      </div>
    </template>
    <template #content>
      <v-card class="pa-5">
        <v-data-table
            hide-default-footer
            :headers="headers"
            :items="items"
        >
          <template #item.updated = "{ item }">
            {{ item.updated | formatDateTimeWithDot }}
          </template>
          <template #item.detail = "{ item }">
            <v-btn icon @click="toDetailPage(item.id)">
              <v-icon>search</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-card>
    </template>
  </contents-layout>
</template>

<script>
import PolicyFormDialog from './PolicyFormDialog';
import eventBus from "@/eventBus";
import store from "@/store";

export default {
  components: { PolicyFormDialog },
  name: 'PolicyCanIds',
  computed: {
    headers() {
      return [
        { text: this.$t('policyCanIds.policyListHeader.no'), value: 'no', align: 'center', sortable: false },
        { text: this.$t('policyCanIds.policyListHeader.policyName'), value: 'policyName', align: 'center', sortable: false },
        { text: this.$t('policyCanIds.policyListHeader.latestVersion'), value: 'latestVersion', align: 'center', sortable: false },
        { text: this.$t('policyCanIds.policyListHeader.status'), value: 'status', align: 'center', sortable: false },
        { text: this.$t('policyCanIds.policyListHeader.updated'), value: 'updated', align: 'center', sortable: false },
        { text: this.$t('policyCanIds.policyListHeader.updatedBy'), value: 'updatedBy', align: 'center', sortable: false },
        { text: this.$t('policyCanIds.policyListHeader.detail'), value: 'detail', align: 'center', sortable: false },
      ];
    },
    policyImportDialog() {
      return {
        title: this.$t('policyCanIds.addPolicyDialog.title'),
        inputs: [
          {
            component: 'v-file-input',
            label: this.$t('policyCanIds.addPolicyDialog.fileInputLabel'),
            key: 'file',
            value: [],
            multiple : true,
            attrs: {
              rules: [
                value => !!value || 'Import Policy File is Required!',
              ],
              outlined: true,
              showSize: true,
              accept: '.json',
            }
          },
          {
            component: 'v-text-field',
            label: this.$t('policyCanIds.addPolicyDialog.nameInputLabel'),
            key: 'policyName',
            value: '',
            attrs: {
              rules: [
                value => !!value || 'Policy Name is Required!',
              ],
              outlined: true,
            }
          },
          {
            component: 'v-text-field',
            label: this.$t('policyCanIds.addPolicyDialog.policyVersionLabel'),
            key: 'version',
            value: '',
            attrs: {
              rules: [
                value => !!value || 'Policy Version is Required!',
              ],
              outlined: true,
            }
          },
          {
            type: 'hidden',
            id : 'can_policy',
            value : 'can'
          }
        ],
        trigger: {
          title: this.$t('policyCanIds.addPolicyDialog.triggerButton'),
          icon: 'mdi-plus',
          attrs: {
            large: true
          }
        },
        submit: {
          title: this.$t('policyCanIds.addPolicyDialog.addButton'),
          cancel: this.$t('policyCanIds.addPolicyDialog.cancelButton'),
          action: () => { console.log('submit executed\n' + `file: ${this.dialog.inputs[0].value}\n policy: ${this.dialog.inputs[1].value}\n version: ${this.dialog.inputs[2].value}`) }
        }
      }
    }
  },
  data() {
    return {
      items: [],
    }
  },

  created() {
    eventBus.$on('getPolicyCanIdsData', () => {
      this.getPolicyCanIdsData();
    });
  },
  mounted() {
    this.getPolicyCanIdsData();
  },
  methods: {
    async getPolicyCanIdsData() {
      let log = await this.$api.getPolicy('can');

      this.items= [];
      for(let idx in log.data) {
        let info = {
          no: parseInt(idx)+1,
          policyName: log.data[idx].policyName,
          latestVersion : log.data[idx].policyVersion,
          status : log.data[idx].status,
          updated : log.data[idx].updateTime,
          updatedBy : log.data[idx].updatedBy,
        }
        this.items.push(info);
      }
    },
    toDetailPage(policyId) {
      this.$router.push(`/policy/can/${policyId}`);
    }
  }
}
</script>

<style scoped>
</style>