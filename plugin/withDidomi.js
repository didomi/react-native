const { createRunOncePlugin } = require("@expo/config-plugins");

const packageInfo = require("../package.json");

const withDidomi = (config) => {
  return config;
};

module.exports = createRunOncePlugin(
  withDidomi,
  packageInfo.name,
  packageInfo.version
)
