import { axios, HOST, config } from '../lib/axiosSetting';

export default {
  async getCanIdsOverall(startDateTime, endDateTime) {
    return (await axios.get(`${HOST}/canids/overall?startDate=` + startDateTime + '&endDate=' + endDateTime, config)).data;
  },
  async getCanIdsLogList(startDateTime, endDateTime, tableItemsPerPage, tablePage) {
    return (await axios.get(
      `${HOST}/canids/log?startDate=` + startDateTime + '&endDate=' + endDateTime + '&itemsPerPage=' + tableItemsPerPage + '&pageNum=' + tablePage,
      config
    )).data;
  },
  async changeCanIdsLogLabel(id, label) {
    await axios.put(`${HOST}/canids/log`, {'id': id, 'label': label}, config);
  },
  async exportCanIdsLog() {
    await axios.get(`${HOST}/canids/export`,
      {
        responseType: 'blob',
        withCredentials: true
      })
      .then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'CanIdsLog');
        document.body.appendChild(link);
        link.click();
      });
  }
}
