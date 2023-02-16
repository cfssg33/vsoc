import 'material-design-icons-iconfont/dist/material-design-icons.css';
import '@mdi/font/css/materialdesignicons.css';

import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import 'vuetify/dist/vuetify.min.css';

Vue.use(Vuetify);

export default new Vuetify({
  theme:{
    themes: {
      light: {
        mainBlue: '#004594',
        mainRed: '#ff1d1d',
        contentBlue: '#57b1ff',
        contentRed:'#f0142f',
        contentGrey:'#707070',
        infoGrey: '#9b9b9b',
        buttonGrey: '#5c5c5c',
      },
      dark: {
        mainBlue: '#004594',
        mainRed: '#ff1d1d',
        contentBlue: '#57b1ff',
        contentRed:'#f0142f',
        contentGrey:'#707070',
        infoGrey: '#9b9b9b',
        buttonGrey: '#5c5c5c',
      }
    }
  }
});
