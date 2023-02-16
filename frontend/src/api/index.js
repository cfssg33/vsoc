const apiFiles = require.context("@/api", true, /\.js$/);
let exportApis = {};

apiFiles.keys().forEach((fileName) => {
  if (!fileName.includes("index")) {
    const getContents = apiFiles(fileName).default;
    exportApis = { ...exportApis, ...getContents };
  }
});
export default exportApis;
