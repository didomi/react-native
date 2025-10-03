const {
  withAndroidManifest,
  withAppBuildGradle,
  withInfoPlist,
  createRunOncePlugin,
} = require("@expo/config-plugins");

const packageInfo = require("../package.json");

/*
const withMyLibraryAndroidManifest = (config) => {
  return withAndroidManifest(config, (config) => {
    const application = config.modResults.manifest.application[0];

    // Example: add a permission
    if (!config.modResults.manifest["uses-permission"]) {
      config.modResults.manifest["uses-permission"] = [];
    }
    config.modResults.manifest["uses-permission"].push({
      $: { "android:name": "android.permission.CAMERA" },
    });

    return config;
  });
}


// Modify Info.plist
const withDidomiInfoPlist = (config) => {
  return withInfoPlist(config, (config) => {
    config.modResults.NSLocationWhenInUseUsageDescription =
      "This app uses location for ...";
    return config;
  });
};
*/

const withDidomi = (config) => {
//  config = withMyLibraryAndroidManifest(config);
//  config = withMyLibraryInfoPlist(config);
  return config;
};

module.exports = createRunOncePlugin(
  withDidomi,
  packageInfo.name,
  packageInfo.version
)