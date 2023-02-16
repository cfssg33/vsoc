import { axios, HOST, config } from '../lib/axiosSetting';

export default {
  async getApiLogList() {
    const data = (await axios.get(`${HOST}/log/api`, config)).data;
    return data;
  }
}