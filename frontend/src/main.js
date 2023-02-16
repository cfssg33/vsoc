import App from './App.vue';
import router from './routers';
import vuetify from './plugins/vuetify';
import store from './store';
import i18n from './plugins/i18n';
import 'core-js';
import 'regenerator-runtime/runtime';
import 'whatwg-fetch';

import './components';
import Vue from 'vue';
import vueMoment from 'vue-moment';
import filters from '@/plugins/filters';
import api from './api';
import globalFunc from './plugins/globalFunc';

Vue.prototype.$api = api;
Vue.prototype.globalFunc = globalFunc;
Vue.use(vueMoment);

for (const [key, func] of Object.entries(filters)) {
  Vue.filter(key, func);
}

export default new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: (h) => h(App),
  components: { App },
}).$mount('#app');
