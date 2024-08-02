const { makeMetroConfig } = require('@rnx-kit/metro-config');
const { resolveSymlinks, getWorkspaces } = require('react-native-monorepo-tools');

module.exports = makeMetroConfig({
  projectRoot: __dirname,
  resolver: {
    resolveRequest: resolveSymlinks,
  },
  watchFolders: getWorkspaces(),
});