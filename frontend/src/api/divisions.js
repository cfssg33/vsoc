import { axios, config, HOST } from '../lib/axiosSetting';

export default {
  async getRegionList() {
    return (await axios.get(`${HOST}/divisions/regionList`, config)).data;
  },
  async getCityList() {
    return (await axios.get(`${HOST}/divisions/cityList`, config)).data;
  }
}
