<template >
  <v-container id="page-admin-list" style="max-width: none;">
    <div class="page-title">{{ $t('account.pageTitle') }}</div>

    <v-row>
      <v-spacer></v-spacer>
      <v-col class="d-flex justify-end">
        <span class="ml-3">
          <c-btn color="blue" outlined iconName="add" @click="adminDialog = true">{{ $t('account.registerAccount') }}</c-btn>
        </span>
      </v-col>
    </v-row>

    <c-table
        :headers="adminTableHeaders"
        item-key="id"
        :items="adminList"
    >
    </c-table>

    <account-c-u-dialog
        :open.sync="adminDialog"
        @reqAddAdmin="reqAddAdmin"
    />
  </v-container>
</template>

<script>
import AccountCUDialog from './AccountCUDialog';

export default {
  components: {AccountCUDialog},
  name: 'Account',
  computed: {
    adminTableHeaders() {
      return [
        {text: this.$t('account.id'), value: 'accountId', align: 'center', sortable: true,},
        {text: this.$t('account.name'), value: 'name', align: 'center', sortable: true,},
        {text: this.$t('account.role'), value: 'role', align: 'center', sortable: false,},
        {text: this.$t('account.createdDate'), value: 'createdDate', align: 'center', sortable: false,},
      ];
    }
  },
  data() {
    return {
      adminDialog: false,
      adminList: [],
    }
  },
  async mounted() {
    const res = await this.$api.getAccountList();
    this.adminList = res.data;
  },
  methods: {
    async reqAddAdmin(data) {
      try {
        const res = await this.$api.addAccount(data);
        this.adminDialog = false;
        this.getAccountList();
        this.$store.commit('snackbar', {color: 'blue', msg: this.$t('account.registerComplete')});
      } catch (err) {
        this.$store.commit('snackbar', {err, where: 'reqAddAdmin'});
      }
    },
    async getAccountList(){
      const res = await this.$api.getAccountList();
      this.adminList = res.data;
    }
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
