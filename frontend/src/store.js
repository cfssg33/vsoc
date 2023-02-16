// store.js
import Vue from 'vue';
import Vuex from 'vuex';
import VuesPersistance from 'vuex-persist';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    detectionLabel: ['Untitled','Breakdown','Malfunction','Hacking'],
    locale: '',
    userInfo: {
      userId: '',
      lastAccessTime: '',
      logCheckTime: ''
    },
    snackbar: {
      show: false,
      multiline: true,
      timeout: 5000,
      color: 'red',
      msg: '문제가 발생하였습니다. 잠시후 다시 시도해주세요.',
      err: null,
      where: null,
    },
  },
  mutations:{
    locale(state, payload) {
      state.locale = payload;
    },
    userInfo(state, payload) {
      state.userInfo.userId = payload.userId;
      state.userInfo.lastAccessTime = payload.lastAccessTime;
      state.userInfo.logCheckTime = payload.logCheckTime;
    },
    snackbar(state, payload) {
      // state.snackbar.multiline = payload.multiline || state.snackbar.multiline;
      // state.snackbar.timeout = payload.timeout || state.snackbar.timeout;
      // state.snackbar.color = payload.color || state.snackbar.color;
      // state.snackbar.message = payload.message || state.snackbar.message;

      const {err, where, msg, timeout, color} = payload;
      let showErr = err;
      let showMsg = msg;

      if(err) {
        if(err.response) {
          const {status, data} = err.response;
          if(data && data.length>0) showMsg = data;
          showErr = err.response;
        }
        console.error('Where[', (where|| ''), ']\nError: ', showErr, (showMsg? (`\nMessage: ${showMsg}`): ''));
      }

      state.snackbar.timeout = timeout|| state.snackbar.timeout;
      state.snackbar.color = color|| 'red';
      state.snackbar.msg = showMsg|| '문제가 발생하였습니다. 잠시후 다시 시도해주세요.';
      state.snackbar.err = err;

      state.snackbar.show = true;
    },
  },
  plugins: [(new VuesPersistance({
    reducer: (state) => ({userInfo: state.userInfo}),
  })).plugin]
});

export default store;
