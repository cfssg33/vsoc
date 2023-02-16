import {axios, config, DEBUG, HOST} from '../lib/axiosSetting';
import policyCanIdsData from '../constants/policy/policyCanIdsData';
import policyHostIdpsData from '../constants/policy/policyHostIdpsData';
import policyDetailData from '../constants/policy/policyDetailData';
import policyLogData from '../constants/policy/policyLogData';
import dbData from '../constants/policy/dbData';
import busData from '../constants/policy/busData';

export default {
  async getPolicyCanIds() {
    const data = DEBUG
      ? [ ...policyCanIdsData ]
      : (await axios.get(`${HOST}/policy/canids`, config)).data;
    return data;
  },
  async getPolicyHostIdps() {
    const data = DEBUG
      ? [ ...policyHostIdpsData ]
      : (await axios.get(`${HOST}/policy/hostidps`, config)).data;
    return data;
  },
  async getPolicyDetail(policyType, policyId) {
    const type = policyType.toLowerCase();
    const mapping = {
      'can': 'canids',
      'host': 'hostidps'
    }

    const mappedType = mapping[type];
    const data = DEBUG
      ? { ...policyDetailData }
      : (await axios.get(`${HOST}/policy/${mappedType}/${policyId}`, config)).data;

    return data;
  },
  async getPolicyLog(policyType, policyId, historyId) {
    const type = policyType.toLowerCase();
    const mapping = {
      'can': 'canids',
      'host': 'hostidps'
    }

    const mappedType = mapping[type];
    const data = DEBUG
      ? [ ...policyLogData ]
      : (await axios.get(`${HOST}/policy/${mappedType}/${policyId}/${historyId}`, config)).data;

    return data;
  },
  async getPolicyDbList(policyType, canId, version) {
    const type = policyType.toLowerCase();
    const mapping = {
      'can': 'canids',
      'host': 'hostidps'
    }

    const mappedType = mapping[type];
    const data = DEBUG
      ? [ ...dbData ]
      : (await axios.get(`${HOST}/policy/${mappedType}/${canId}/${version}/db`, config)).data;

    return data;
  },
  async getPolicyBusData(policyType, canId, version) {
    const type = policyType.toLowerCase();
    const mapping = {
      'can': 'canids',
      'host': 'hostidps',
    }

    const mappedType = mapping[type];
    const data = DEBUG
      ? { ...busData }
      : (await axios.get(`${HOST}/policy/${mappedType}/${canId}/${version}/bus`, config)).data;

    return data;
  },
  async addPolicy(data, type) {
    await axios.post(`${HOST}/policy/add/${type}`, data, config);
  },

  async getPolicy(type) {
    return await axios.get(`${HOST}/policy/${type}/info`, config);
  }
}