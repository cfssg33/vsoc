import { axios, HOST, config } from '../lib/axiosSetting';

export default {
  async getMonitoringPanelData() {
    return (await axios.get(`${HOST}/monitor/monitorpanel`, config)).data;
  },

  async getVulnerableRegionData() {
    return (await axios.get(`${HOST}/monitor/vulnerable/region`, config)).data;
  },

  async getVulnerableCityData(region) {
    return (await axios.get(`${HOST}/monitor/vulnerable/city?region=`+region, config)).data;
  },

  async getUncheckLogCnt(accountId) {
    return axios.get(`${HOST}/monitor/log/uncheck/count?accountId=`+accountId, config);
  },

  async setCheckLog(accountData) {
    return axios.post(
      `${HOST}/monitor/log/check`,
      {
        'accountId' : accountData
      }, 
      config
    );
  },

  async getServerHealthStatus(lastAccessTime, isFirstAccess) {
    return axios.get(`${HOST}/monitor/server/status?lastAccessTime=` + lastAccessTime + '&isFirstAccess=' + isFirstAccess , config);
  },

  async getDetectionLogCnt(accountId, lastAccessTime) {
    return axios.get(`${HOST}/monitor/detectionLog/hour?accountId=` + accountId + '&lastAccessTime='+ lastAccessTime, config);
  },
};
