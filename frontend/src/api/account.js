import { axios, HOST, config, DEBUG } from '../lib/axiosSetting';

export default {
  async getAccountList() {
    const data = await axios.get(`${HOST}/account`, config);
    return data;
  },
  async addAccount(data) {
    const resp = await axios.post(`${HOST}/account`, data, config);
    return resp;
  },
}
