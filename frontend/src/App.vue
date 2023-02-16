<template>
  <v-app>
    <v-navigation-drawer
        color="mainBlue"
        dark
        v-model="drawer"
        app
        v-if="showMenuBar"
    >
      <v-list expand>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title font-weight-black">
              PANQI vSOC
            </v-list-item-title>
            <v-list-item-subtitle class="subtitle">
              ver 1.0
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-group
            v-for="routeInfo in navRouteInfo"
            :key="routeInfo.name"
            v-model="routeInfo.active"
            color="white"
        >
          <template #activator>
            <v-list-item-content>
              <v-list-item-title>{{$t("routeName."+routeInfo.name)}}</v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item-group>
            <v-list-item
                v-for="route in routeInfo.subRoutes"
                :key="route.path + route.name"
                v-if="route.type !== 'hidden'"
                class="pl-5"
                :to="route.path"
                active-class="active-border"
                link
            >
              <v-list-item-content class="pl-5">
                <v-list-item-title>{{$t("routeName."+route.name)}}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar app v-if="showMenuBar">
      <v-app-bar-nav-icon
          @click="drawer = !drawer"
      />
      <v-spacer/>
      <v-menu offset-y>
        <template #activator="{on}">
          <v-select
              :items="languages"
              v-model="selectedLanguage"
              @change="selectLocale"
              class="mt-6 mr-3"
              color="mainBlue"
              outlined
              dense
              style="max-width: 120px"
          />
        </template>
      </v-menu>

      <v-menu offset-y @input="clearAlertFlag">
        <template #activator="{on}">
          <v-btn icon small
                 v-on="on"
          >
            <v-badge
                color="red"
                offset-y="-5"
                offset-x="-15"
                dot
                v-if="(alertFlag)"
            />
            <v-icon>
              notifications
            </v-icon>
          </v-btn>
        </template>
        <div class="alert-border">
          <div style="display: flex" v-if="updateCount">
            <div class="update-list">
              You got {{ updateCount }} new updates.
            </div>
            <button class="mark-list" @click="setMonCheckLog">
              Mark as read
            </button>
          </div>
          <div class="update-list" v-if="alertFlag">
            vSOC server health status is poor.
          </div>
          <div class="detection-list">
            <div>
              {{ detectLogCount }} Detection Log is add.
            </div>
            <div v-if="detectLogCount">
              {{ detectCntElapsed }}
            </div >
          </div>
        </div>
      </v-menu>

      <v-menu offset-y>
        <template #activator="{on}">
          <v-btn
              v-on="on"
              plain
              x-large
              style="text-transform: none !important;"
          >
            {{ userId }}
            <v-icon>arrow_drop_down</v-icon>
          </v-btn>
        </template>
        <v-list class="v-card--hover">
          <v-list-item class="d-flex" style="width: 120px; text-align: center">
            <v-list-item-title @click="logout">LOGOUT</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

    </v-app-bar>
    <v-main class="d-flex">
      <router-view class="flex-grow-1" style="min-height: 100vh; width: 100%;"></router-view>
    </v-main>

    <v-snackbar
        class="text-center"
        v-model="snackbar.show"
        :timeout="snackbar.timeout"
        :color="snackbar.color"
        :multi-line="snackbar.multiline"
    >
      {{snackbar.msg}}
    </v-snackbar>
  </v-app>
</template>

<script>
import vsocRouteInfo from './routers/vsoc';
import eventBus from './eventBus';
import router from './routers';
import store from './store';
import loginAPI from '@/api/login';
import accountAPI from './api/account';

export default {
  name: 'App',
  data() {
    return {
      hideRoute: ['/', '/login'],
      drawer: null,
      isLogin: false,
      languages: [{text : 'en', value: 'en'}, {text:'chinese', value:'zhHans'}],
      selectedLanguage: this.$i18n.locale,
      userId: '',
      updateCount: 0,
      detectLogCount: 0,
      alertFlag: false,
      healthStatus: false,
      intervalId : {
        uncheckLog: 0,
        healthStatus: 0,
        detectionLogCnt: 0,
        detectionElapsed: 0,
      },
      detectCntElapsed: '0m'
    }
  },
  computed: {
    navRouteInfo() {
      return vsocRouteInfo.filter(route => route.type !== 'hidden').map(routes => {
        if (routes.type === 'category') {
          routes.subRoutes = routes.subRoutes.filter(route => route.type !== 'hidden');
        }
        return routes;
      })
    },
    snackbar() {
      let err = this.$store.state.snackbar.err;
      if(err && err.response) {
        this.$router.push('/');
      }
      return this.$store.state.snackbar;
    },
    showMenuBar() {
      return !this.hideRoute.some(route => this.$route.path === route);
    }
  },
  components: {},
  created() {
    eventBus.$on('login', () => {
      this.isLogin = true;
      this.userId = store.state.userInfo.userId;
      this.callAlarmInfo();
      this.checkTimer();
      this.changeRoute('/monitoring');
    });

    eventBus.$on('alreadyLogin', () => {
      this.changeRoute('/monitoring');
    });
  },

  mounted() {
    this.userId = this.$store.state.userInfo.userId;
  },

  destroyed() {
    this.clearTimer();
  },

  methods:{
    selectLocale() {
      this.$store.commit('locale', this.selectedLanguage);
      this.$i18n.locale = this.selectedLanguage;
    },

    changeRoute(path) {
      if(router.currentRoute.path.toLowerCase() !== path) {
        this.$router.push(path);
      }
    },

    detectTimeElapsed() {
      if (this.detectLogCount) {
        let currentDate = new Date();
        let lastAccessTime;

        if (this.$store.state.userInfo.lastAccessTime == null) {
          lastAccessTime = new Date();
          this.$store.commit('userInfo', { userId: this.$store.state.userInfo.userId, lastAccessTime: lastAccessTime, logCheckTime: this.$store.state.userInfo.logCheckTime });
        } else {
          lastAccessTime = new Date(this.$store.state.userInfo.lastAccessTime);
        }

        let elapse = new Date(currentDate - lastAccessTime);

        if ((elapse.getUTCDate() - 1) != 0) {
          this.detectCntElapsed = (elapse.getUTCDate() - 1) + 'd';
        } else if (elapse.getUTCHours() != 0) {
          this.detectCntElapsed = elapse.getUTCHours() + 'h';
        } else if (elapse.getUTCMinutes() != 0) {
          this.detectCntElapsed = elapse.getUTCMinutes() + 'm';
        } else {
          this.detectCntElapsed = '0m';
        }
      }
    },

    callAlarmInfo() {
      this.getMonUncheckLogCount();
      this.getMonServerHealthStatus();
      this.getMonDetectionLogCount();
    },

    checkTimer() {
      this.intervalId.uncheckLog = setInterval(() => {
        this.getMonUncheckLogCount();
      }, 60000);

      this.intervalId.healthStatus = setInterval(() => {
        this.getMonServerHealthStatus();
      }, 60000);

      this.intervalId.detectionLogCnt = setInterval(() => {
        this.getMonDetectionLogCount();
      }, 600000);
    },

    clearTimer() {
      clearInterval(this.intervalId.uncheckLog);
      clearInterval(this.intervalId.healthStatus);
      clearInterval(this.intervalId.detectionLogCnt);
      clearInterval(this.intervalId.detectionElapsed);
    },

    clearAlertFlag(opened) {
      if (opened) {
        return;
      }

      this.alertFlag = false;
    },

    async logout() {
      const formData = new FormData();
      formData.append('loginId', this.userId);

      const response = await loginAPI.logout(formData);
      if (response.status === 200) {
        this.isLogin = false;
        this.userId = '';
        this.changeRoute('/login');
      }

      localStorage.removeItem('vuex');
    },

    async getMonUncheckLogCount() {
      const response = await this.$api.getUncheckLogCnt(this.userId);
      if (response.status === 200) {
        this.updateCount = response.data;
        if (this.updateCount) {
          this.alertFlag = true;
        }
      }
    },

    async setMonCheckLog() {
      await this.$api.setCheckLog(this.userId);
      this.updateCount = 0;
    },

    async getMonServerHealthStatus() {

      let isFirstAccess = false;
      if (this.$store.state.userInfo.lastAccessTime == null) {
        isFirstAccess = true;
      }

      const response = await this.$api.getServerHealthStatus(this.$store.state.userInfo.lastAccessTime, isFirstAccess);
      if (response.status === 200) {
        if (response.data.result === 'Restarted') {
          this.alertFlag = true;
        }
      }
    },

    async getMonDetectionLogCount() {
      const response = await this.$api.getDetectionLogCnt(this.$store.state.userInfo.userId, this.$store.state.userInfo.lastAccessTime);
      if (response.status === 200) {
        this.detectLogCount = response.data;
        if (this.detectLogCount) {
          this.alertFlag = true;
          this.detectTimeElapsed();
          this.intervalId.detectionElapsed = setInterval(() => {
            this.detectTimeElapsed();
          }, 60000);
        }
      }
    },

  },
};

router.beforeEach(async (to, from, next) => {
  try {
    await loginAPI.sessionCheck();

    // 세션이 존재하는 경우, 로그인페이지로 접근 시 monitoring 페이지로 이동
    if (to.path === '/') {
      this.changeRoute('/monitoring');
    }

  } catch (err) {
    localStorage.removeItem('vuex');
    return next('/login');
  }

  return next();
});

</script>
<style scoped>
.title {
  font-size: 1.5rem !important;
}
.subtitle {
  font-size: 1.1rem !important;
}
.alert-border {
  font-size: 1rem;
  width: 350px;
  background: white;
  border-color: #0096e0;
  border-style: groove;
  border-radius: 5px;
}
.update-list {
  display: flex; flex: 0.7;
  margin-left: 15px;
  margin-top: 20px;
  font-size: 0.8rem;
}
.detection-list {
  margin-left: 15px;
  margin-top: 20px;
  margin-bottom: 20px;
  font-size: 0.8rem;
}
.mark-list {
  color: #0096e0;
  display: flex; flex: 0.3;
  margin-top: 20px;
  text-align: right;
  font-size: 0.8rem;
}
</style>
<style>
::-webkit-scrollbar {
  display: none;
}
* {
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
</style>
