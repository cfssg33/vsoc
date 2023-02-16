import { axios, config, HOST } from '../lib/axiosSetting';

export default {
  async getHostIdpsOverall(startDateTime, endDateTime) {
    return (await axios.get(`${HOST}/hostidps/overall?startDate=`  + startDateTime + '&endDate=' + endDateTime, config)).data;
  },
  async getHostIdpsLogList(type, startDateTime, endDateTime, tableItemsPerPage, tablePage) {
    return (await axios.get(
      `${HOST}/hostidps/log?type=` + type + '&startDate=' + startDateTime + '&endDate=' + endDateTime + '&itemsPerPage=' + tableItemsPerPage + '&pageNum=' + tablePage,
      config
    )).data;
  },
  async changeHostIdpsLogLabel(uid, label) {
    await axios.put(`${HOST}/hostidps/log`, {'uid': uid, 'label': label}, config);
  },
  async exportHostIdpsLog() {
    await axios.get(`${HOST}/hostidps/export`,
      {
        responseType: 'blob'
      })
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'HostIdpsLog');
        document.body.appendChild(link);
        link.click();
      });
  }
}
