import { axios, HOST, config, DEBUG } from '../lib/axiosSetting';

export default {
  async login(formData) {
    // if (DEBUG) {
    //   return {status: 200, data: { userId: formData.userId }};
    // }
    return axios.post(`${HOST}/loginProcessing`, formData, config);
  },
  async logout(formData) {
    return axios.post(`${HOST}/logout`, formData, config);
  },
  async sessionCheck() {
    return axios.get(`${HOST}/session-check`, config);
  },
};
