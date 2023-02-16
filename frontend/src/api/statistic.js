import {axios, config, DEBUG, HOST} from '../lib/axiosSetting';
import trendsData from '../constants/trendsData';

export default {
  async getOverallTrendsData() {
    const data = DEBUG
      ? { ...trendsData.overAll }
      : (await axios.get(`${HOST}/trends/overall`, config)).data;
    return data;
  },
  async getRegionTrendsData() {
    const data = DEBUG
      ? { ...trendsData.region }
      : (await axios.get(`${HOST}/trends/region`, config)).data;
    return data;
  },
  async getVehicleTrendsData() {
    const data = DEBUG
      ? { ...trendsData.vehicle }
      : (await axios.get(`${HOST}/trends/vehicle`, config)).data;
    return data;
  },
  async getPartsTrendsData() {
    const data = DEBUG
      ? [ ...trendsData.parts ]
      : (await axios.get(`${HOST}/trends/parts`, config)).data;
    return data;
  },
  async getPartSingularityData() {
    const data = DEBUG
      ? { ...trendsData.singularity }
      : (await axios.get(`${HOST}/singularity/parts`, config)).data;
    return data;
  }
}
