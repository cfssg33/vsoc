export const axios = require("axios");

export const HOST =
  process.env.NODE_ENV === "production" ? "" : `http://127.0.0.1:9040`;
export const DEBUG = true;
export const config = {withCredentials: true};
