import moment from "moment";

const filters = {
  numberFormatter: (value) => {
    const formatted = Number(value).toLocaleString();
    if (formatted === "NaN") {
      return 0;
    }
    return formatted;
  },
  canonicalNumberFormatter: (value) => {
    const units = ["million", "billion", "trillion", "quadrillion"];

    if (value >= 1e6) {
      const unit = Math.floor((value / 1000).toFixed(0).toString().length);
      const num = (value / 10 ** (unit + 2)).toFixed(1);
      const unitname = units[Math.floor(unit / 3) - 1];
      return num + " " + unitname;
    }

    return value.toLocaleString();
  },
  formatDate(dateTimeString) {
    if (!dateTimeString) {
      return "";
    }
    return moment(dateTimeString).format("YYYY-MM-DD");
  },
  formatDateTime(dateTimeString) {
    if (!dateTimeString) {
      return "";
    }
    return moment(dateTimeString).format("YYYY-MM-DD HH:mm:ss");
  },
  formatDateWithDot(dateTimeString) {
    if (!dateTimeString) {
      return "";
    }
    return moment(dateTimeString).format("YYYY.MM.DD");
  },
  formatDateTimeWithDot(dateTimeString) {
    if (!dateTimeString) {
      return "";
    }
    return moment(dateTimeString).format("YYYY.MM.DD HH:mm:ss");
  },
};

export default filters;
