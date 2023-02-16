import Vue from 'vue';
import VueI18n from 'vue-i18n';

Vue.use(VueI18n);

let messages = {};
const locales = require.context('@/locales', true, /\.json$/);
locales.keys().forEach(key => {
  const matched = key.match(/([A-Za-z0-9-_]+)\./i);
  if (matched && matched.length > 1) {
    const locale = matched[1];
    messages[locale] = locales(key);
  }
});

//todo 배포시 default값 중문으로 변경
export default new VueI18n({
  locale: process.env.VUE_APP_I18N_LOCALE || 'en',
  fallbackLocale: process.env.VUE_APP_I18N_FALLBACK_LOCALE || 'en',
  messages: messages,
});
