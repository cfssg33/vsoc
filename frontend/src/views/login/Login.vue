<template>
  <div id="page-login" style="padding-top: 35px;" v-resize="onResize">
    <v-container style="position: relative;">
      <v-row>
        <v-card-text class="font-weight-bold text-center caption">
          <img
            src="../../assets/panqi_logo.png"
            alt="Panqi-Logo"
            style="height: 55px; margin-top: 6vh; margin-bottom: 3vh;"
          />
        </v-card-text>
      </v-row>
    </v-container>

    <v-form v-model="valid" lazy-validation ref="form">
      <v-container class="mt-6 rounded-0" id="loginForm">
        <v-row justify="center" align="center" class="mx-2 mt-1">
          <v-col cols="12">
            <v-text-field
              v-model="loginForm.loginId"
              :placeholder="$t('login.id')"
              required
              :rules="[(v) => !!v]"
              hide-details
              :autofocus="true"
              color="mainBlue"
              dense
              @keyup.enter="submit"
            ></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field
              v-model="loginForm.password"
              type="password"
              :placeholder="$t('login.password')"
              required
              :rules="[(v) => !!v]"
              hide-details
              color="mainBlue"
              dense
              @keyup.enter="submit"
            ></v-text-field>
          </v-col>
          <v-col cols="12" class="mt-10">
            <v-btn
              class="mainBlue white--text"
              block
              text
              outlined
              @click="submit"
              >{{$t('login.login')}}</v-btn
            >
          </v-col>
        </v-row>
      </v-container>
    </v-form>

    <div class="footer_div">
      <v-footer class="footer mainBlue" fixed>
        <div class="text-center" style="width: 100%;">
          <img
            src="../../assets/AUTOCRYPT_UNDER.png"
            alt="AutoCrypt-Logo"
            style="height: 25px; margin-top: 2vh; margin-right: 2.5vw;"
          />
          <img
            src="../../assets/panqi_logo.png"
            alt="Panqi-Logo"
            style="height: 35px; margin-top: 2vh; margin-left: 2.5vw;"
          />
          <div class="white--text message">
            {{$t('login.autoFooter')}}
          </div>
        </div>
      </v-footer>
    </div>
  </div>
</template>

<script>
import loginAPI from '../../api/login';
import eventBus from '../../eventBus';

export default {
  props: ['isLogin'],
  data() {
    return {
      loginForm: {
        centerId: '',
        loginId: '',
        password: '',
      },
      centerInfo: [],
      valid: false,

      message: '',
      snackbar: false,
    };
  },
  mounted() {
    if (this.isLogin) {
      eventBus.$emit('alreadyLogin');
    }
  },
  methods: {
    async submit() {
      if (!this.$refs.form.validate()) {
        alert(this.$t('login.fieldEmpty'));
        return;
      }
      try {
        const formData = new FormData();
        formData.append('loginId', this.loginForm.loginId);
        formData.append('password', this.loginForm.password);

        const res = await loginAPI.login(formData);
        if (res.status === 200) {
          this.$store.commit('locale', this.$i18n.locale);
          this.$store.commit('userInfo', { userId: this.loginForm.loginId, lastAccessTime: res.data.lastAccessTime, logCheckTime: res.data.logCheckTime });
          eventBus.$emit('login');
          this.$destroy();
        }
      } catch (err) {
        alert(this.$t('login.userInfoDoesNotMatch'));
      }
    },
    onResize() {
      let height = window.innerHeight;
      let width = window.innerWidth;
      let loginPage = document.getElementById('page-login');
      if (height > 1080 || width > 1920) {
        loginPage.style.paddingTop = `${height - 1055}px`;
      }
    },
  },
};
</script>
<style>
.message {
  font-size: 14px;
  text-align: center;
  margin: 15px 0;
  font: SpoqaHanSans-Regular;
}

#loginForm {
  max-width: 432px;
  height: 100%;
  background-color: white;
  padding: 20px;
  padding-bottom: 50px;
  border-radius: 10px;
  box-shadow: grey 1px 1px 4px;
}

#2u-logo {
  width: 200px;
  object-fit: contain;
}
#page-login {
  background-image: url("../../assets/VSOC.png");
  background-size: cover;
}

@media screen and (max-width: 767px) {
  #loginForm {
    width: 90%;
    height: auto;
    padding: 0 !important;
  }
  #page-login {
    padding: 0 !important;
  }
  #2u-logo {
    display: none;
  }
}

@media screen and (max-height: 732px) {
  .footer_div {
    display: none;
  }
}
</style>
