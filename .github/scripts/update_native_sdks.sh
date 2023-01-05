#!/bin/bash

#----------------------------------------------------------
# Update Android and iOS SDKs version (latest from repos)
#----------------------------------------------------------

# Get last version from pod
pod_last_version() {
  version=""
  for line in $(pod trunk info Didomi-XCFramework); do
    if [[ "$line" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
      version=$line
    fi
  done
  echo "$version"
}

#
# Android
#

# Get Android SDK Version
lastAndroidVersion=$(curl -s 'https://search.maven.org/solrsearch/select?q=didomi' | sed -n 's|.*"latestVersion":"\([^"]*\)".*|\1|p')
if [[ ! $lastAndroidVersion =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting android SDK version"
  exit 1
fi

echo "Android SDK last version is $lastAndroidVersion"

# Update Android dependency
pushd android >/dev/null
sed -i~ -e "s|io.didomi.sdk:android:[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|io.didomi.sdk:android:$lastAndroidVersion|g" build.gradle || exit 1
popd >/dev/null

#
# iOS
#

# Get iOS SDK Version
lastIosVersion=$(pod_last_version)
if [[ ! $lastIosVersion =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting ios SDK version"
  exit 1
fi

echo "iOS SDK last version is $lastIosVersion"

# Update iOS dependency
sed -i~ -e "s|s.dependency \"Didomi-XCFramework\", \"[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}\"|s.dependency \"Didomi-XCFramework\", \"$lastIosVersion\"|g" react-native-didomi.podspec || exit 1

# Update pod
pod repo update || exit 1

#
# Sample App
#

echo "Update Sample App dependencies"

pushd sampleApp/ >/dev/null
npm install
popd >/dev/null

# Update Sample App iOS framework
pushd sampleApp/ios >/dev/null
pod repo update
pod install
popd >/dev/null

#
# Test App
#

echo "Update Test App dependencies"

pushd testApp/ >/dev/null
npm install
popd >/dev/null

# Update Test App iOS framework
pushd testApp/ios >/dev/null
pod repo update
pod install
popd >/dev/null

#
# Cleanup
#

# Cleanup backup files
find . -type f -name '*~' -delete

echo "Update complete"
