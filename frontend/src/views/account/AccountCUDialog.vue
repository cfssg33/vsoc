<template>
  <v-container>
    <v-dialog v-model="openFlag" persistent max-width="1000px">
      <c-card class="white" :title="onDialogTitle">
        <v-form lazy-validation ref="form">
          <div class="mt-2">
            <span class="red--text">*</span>{{ $t('account.registerPageNotify') }}
          </div>
          <v-row align="center">

            <v-col cols="12" md="4">
              <span class="font-weight-black">{{ $t('account.id') }}</span><span class="red--text"> * </span>
            </v-col>
            <v-col cols="12" md="8">
              <span v-if="adminFormData">{{loginId}}</span>
              <v-row v-else>
                <v-col cols="8">
                  <v-text-field
                    v-model="loginId"
                    :rules="[rules.requiredId]"
                    solo
                    maxlength="32"
                  ></v-text-field>
                </v-col>
                <v-col cols="4">
                  <c-btn
                    class="mt-2"
                    color="blue"
                    outlined
                    @click="idDuplicateCheck"
                  >{{ $t('account.checkDuplicate') }}
                  </c-btn>
                </v-col>
              </v-row>
            </v-col>
            <!--todo 현재 비밀번호-->
            <v-col cols="12" md="4" v-if="this.adminFormData">
              <span class="font-weight-black">{{ $t('account.currentPassword') }}</span><span class="red--text"> * </span>
            </v-col>
            <v-col cols="12" md="8" v-if="this.adminFormData">
              <v-text-field
                :type="pwdShow3 ? 'text' : 'password'"
                v-model="currentPwd"
                :append-icon="pwdShow3 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.requiredPass]"
                @click:append="pwdShow3 = !pwdShow3"
                solo
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
              <span class="font-weight-black">{{ $t('account.password') }}</span><span class="red--text"> * </span>
            </v-col>
            <v-col cols="12" md="8">
              <v-text-field
                :type="pwdShow ? 'text' : 'password'"
                v-model="password"
                :append-icon="pwdShow ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.requiredPass, passwordValidationRule]"
                @click:append="pwdShow = !pwdShow"
                solo
                maxlength="64"
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="4">
            </v-col>
            <v-col cols="12" md="8" style="paddig-top:0px;">
              {{ $t('account.passwordGuide1') }}<br>
              {{ $t('account.passwordGuide2') }}<br>
              {{ $t('account.passwordGuide3') }}
            </v-col>
            <v-col cols="12" md="4">
              <span class="font-weight-black">{{ $t('account.checkPassword') }}</span><span class="red--text"> * </span>
            </v-col>
            <v-col cols="12" md="8">
              <v-text-field
                :type="pwdShow2 ? 'text' : 'password'"
                v-model="loginPwdConfirm"
                :append-icon="pwdShow2 ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="pwdShow2 = !pwdShow2"
                :rules="[rules.requiredPass, passwordConfirmationRule]"
                solo
                maxlength="64"
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="4">
              <span class="font-weight-black">{{ $t('account.name') }}</span><span class="red--text"> * </span>
            </v-col>
            <v-col cols="12" md="8">
              <span v-if="adminFormData">{{name}}</span>
              <v-text-field
                v-else
                v-model="name"
                :rules="[rules.requiredName]"
                solo
                maxlength="32"
              ></v-text-field>
            </v-col>
          </v-row>
          <v-row justify="center">
            <c-btn class="mr-1 mt-2" color="blue" outlined @click="onAddAdmin">{{ $t('account.register') }}</c-btn>
            <c-btn class="mt-2" color="grey" outlined @click="openFlag = false">{{ $t('account.cancel') }}</c-btn>
          </v-row>
        </v-form>
      </c-card>
    </v-dialog>
  </v-container>
</template>

<script>
import {mask} from 'vue-the-mask';

export default {
  directives: {mask},
  props: {
    open: {
      type: Boolean,
      default: false
    },
    adminFormData: {
      type: Object,
      default: undefined
    },
  },
  data: () => ({
    openFlag: false,

    pwdShow: false,
    pwdShow2: false,
    pwdShow3: false,

    rules: {
      requiredPass: (value) => !!value || this.$t('account.requiredPass'),
      requiredId: (value) => !!value || this.$t('account.requiredId'),
      requiredName: (value) => !!value || this.$t('account.requiredName'),
    },

    // admin info
    id: '',
    loginId: '',
    password: '',
    loginPwdConfirm: '',
    centerId: '',
    authority: '',

    menuRole: '',

    name: '',
    extension: '',
    currentPwd:'',

    centerInfo: [],
    authorities:[],

    // 중복된 아이디 여부
    availableId: true,
    checkingId: false,

    // dialog title
    dialogTitle: '',

    roleList: [],
  }),
  mounted() {
  },
  watch: {
    async open(value) {
      this.openFlag = value;
    },
    openFlag(value) {
      this.$emit('update:open', value);
    },
    // id 변경되었을 때 중복체크 플래그 초기화
    loginId() {
      this.checkingId = false;
    },
    mobileNo1(value) {
      if(this.customerFormData === undefined){
        this.mobileNoFlag = false;
      }
      if(value === '02' || value.length === 3) {
        document.getElementById('mobileNo2A').focus()
      }
    },
    mobileNo2(value) {
      if(this.customerFormData === undefined){
        this.mobileNoFlag = false;
      }
      if(value.length === 4) {
        document.getElementById('mobileNo3A').focus()
      }
    },
    mobileNo3(value) {
      if(this.customerFormData === undefined){
        this.mobileNoFlag = false;
      }
    },
  },
  computed: {
    passwordValidationRule() {
      return () => (this.password.length >= 8 && this.password.length <= 16) || this.$t('account.passwordValidationRule')
    },
    passwordConfirmationRule() {
      return () => (this.password === this.loginPwdConfirm) || this.$t('account.passwordConfirmationRule')
    },
    onDialogTitle() {
      return this.adminFormData ? this.$t('account.onDialogTitleModify') : this.$t('account.onDialogTitleRegister');
    },
  },
  methods: {
    idDuplicateCheck() {
      //this.reqGetCenterAdmin();
    },
    initData() {
      this.centerId = '';
      this.authority = '';
      this.loginId = '';
      this.currentPwd = '';
      this.password = '';
      this.loginPwdConfirm = '';
      this.name = '';
      this.extension = '';
      this.availableId = false;
      this.mobileNo1 = '';
      this.mobileNo2 = '';
      this.mobileNo3 = '';
      this.menuRole = '';
    },

    // 중복 검사를 위한 center별 id 조회
    async reqGetCenterAdmin() {
      try {
        const res = await this.$api.getCenterAdmin(this.loginId, this.centerId, this.authority);
        this.availableId = !!!(res.data);

        if (this.availableId) {
          this.$store.commit('snackbar', {timeout: 2000, msg: this.$t('account.availableId')});
          this.checkingId = true;
        } else {
          this.$store.commit('snackbar', {timeout: 2000, msg: this.$t('account.duplicatedId')});
        }

      } catch (err) {
        this.$store.commit('snackbar', {err, where:'reqGetCenterAdmin'});
      }
    },

    onAddAdmin() {
      if (!(this.$refs.form.validate())) {
        this.$store.commit('snackbar', {timeout: 2000, msg: this.$t('account.cancelRegister')});
        return;
      }

      let reqData = {
        loginId: this.loginId,
        password: this.password,
        name: this.name
      };

      this.$emit('reqAddAdmin', reqData);
    },

    onUpdateAdmin() {
      if (!(this.$refs.form.validate())) {
        this.$store.commit('snackbar', {timeout: 2000, msg: '입력 정보를 다시 확인해주세요.'});
        return;
      }

      this.extension = this.mobileNo1+'-'+this.mobileNo2+'-'+this.mobileNo3;
      if(!this.mobileRegxCheck(this.extension)) {
        this.$store.commit('snackbar', {timeout : 2000 ,msg: '연락처 양식이 올바르지 않습니다.'});
        return;
      }

      let reqData = {
        id: this.id,
        centerId: this.centerId,
        loginId: this.loginId,
        password: this.password,
        name: this.name,
        extension: this.extension,
        currentPwd: this.currentPwd,
        menuRole: this.menuRole,
      };

      this.$emit('reqUpdateAdmin', reqData);

    },

    async reqGetCenter() {
      try {
        const res = await this.$api.getCenter(this.$store.state.service);
        let centerInfo = [];
        let authorities = [];

        if(this.$store.state.userInfo.role === 'SA') { //super 일 경우
          for (const i in res.data) {
            centerInfo.push({text: res.data[i].centerName, value: res.data[i].id})
          }

          authorities.push({text : '슈퍼어드민 (이동의 자유 관계자)', value: 'SA'});
          authorities.push({text : '어드민 (센터 담당자)', value: 'admin'});
          authorities.push({text : '유저 (직원)', value: 'user'});
          authorities.push({text : '예약관리자 (직원)', value: 'RM'});
          authorities.push({text : '모니터링관리자 (직원)', value: 'MM'});
          authorities.push({text : '임시관리자 (직원)', value: 'TM'});
        }
        else if(this.$store.state.userInfo.role === 'RM') { //super 일 경우
          for (const i in res.data) {
            if(res.data[i].centerName === this.$store.state.userInfo.centerName) {
              centerInfo.push({text: res.data[i].centerName, value: res.data[i].id});
            }
          }

          authorities.push({text : '예약관리자 (직원)', value: 'RM'});
        } else { // admin/ user 일 경우
          for (const i in res.data) {
            if(res.data[i].centerName === this.$store.state.userInfo.centerName) {
              centerInfo.push({text: res.data[i].centerName, value: res.data[i].id});
            }
          }

          authorities.push({text : '유저 (직원)', value: 'user'});
          authorities.push({text : '예약관리자 (직원)', value: 'RM'});
          authorities.push({text : '임시관리자 (직원)', value: 'TM'});
        }

        if(centerInfo.length > 0) this.centerId = centerInfo[0].value;
        if(authorities.length > 0) this.authority = authorities[0].value;
        this.centerInfo = centerInfo;
        this.authorities = authorities;

      } catch (err) {
        this.$store.commit('snackbar', {err, where:'reqGetCenter'});
      }
    },

    mobileRegxCheck(number) {
      if(number === '') return true;
      if(this.mobileNo1 === '010') return this.mobileNoRegx010.test(number)
      else return this.mobileNoRegx.test(number)
    },
    async reqGetRoleList() {
      try {
        const res = await this.$api.getAdminRoleByCenterId();
        this.roleList = res.data;
        console.log('res/ reqGetRoleList : ', res.data);
      } catch (err) {
        this.$store.commit('snackbar', {err, where: 'reqGetRoleList'});
      }
    },
  },
}
</script>
<style scoped>
</style>
