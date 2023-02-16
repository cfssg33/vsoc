import {axios, config, DEBUG, HOST} from '../lib/axiosSetting';
import platformListData from '../constants/vehicle/vehiclePlatformListData';
import vehicleGroupListData from '../constants/vehicle/vehicleGroupListData';
import dbData from '../constants/policy/dbData';
import vehicleStatData from '../constants/vehicle/vehicleStatData';

export default {
  async getVehicleList() {
    return (await axios.get(`${HOST}/vehicle`, config)).data;
  },
  async getVehicleDetail(vehicleId) {
    return (await axios.get(`${HOST}/vehicle/${vehicleId}`, config)).data;
  },
  async getPlatformList() {
    const data = DEBUG
      ? [ ...platformListData ]
      : (await axios.get(`${HOST}/platform`, config)).data;
    return data;
  },
  async getVehicleGroupList() {
    const data = DEBUG
      ? { ...vehicleGroupListData }
      : (await axios.get(`${HOST}/vehicle/group`, config)).data;
    return data;
  },
  async getPlatformStat(platformId) {
    const data = DEBUG
      ? { ...vehicleStatData }
      : (await axios.get(`${HOST}/platform/${platformId}/stat`, config)).data;
    return data;
  },
  async getPlatformDbData(platformId) {
    const data = DEBUG
      ? [ ...dbData ]
      : (await axios.get(`${HOST}/platform/${platformId}/db`, config)).data;

    return data;
  },
};
