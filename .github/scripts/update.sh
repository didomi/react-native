#!/bin/bash

#----------------------------------------------------------
# Update Android and iOS SDKs version (latest from repos)
# Increment React-Native version (from param major|minor|patch)
#   No argument: use patch as default
# Update React-Native dependencies
#----------------------------------------------------------

# set nocasematch option
shopt -s nocasematch

# Increment position (Patch is default)
if [[ "$1" =~ ^major$ ]]; then
  position=0
elif [[ "$1" =~ ^minor$ ]]; then
  position=1
else
  position=2
fi

# unset nocasematch option
shopt -u nocasematch

# Increment version (eg `sh increment_version 1.2.3 1` returns `1.3.0`)
# args:
#   - version number (eg `0.32.4`)
#   - increment number: `0` (major) | `1` (minor) | `2` (patch)
increment_version() {
  local delimiter=.
  local array=($(echo "$1" | tr $delimiter '\n'))
  array[$2]=$((array[$2] + 1))
  if [ $2 -lt 2 ]; then array[2]=0; fi
  if [ $2 -lt 1 ]; then array[1]=0; fi
  echo $(
    local IFS=$delimiter
    echo "${array[*]}"
  )
}

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
# React Native
#

# Get RN version
currentRNVersion=$(sh .github/scripts/extract_version.sh)
if [[ ! $currentRNVersion =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting RN version ($currentRNVersion)"
  exit 1
fi

echo "Current version is $currentRNVersion"

# Increment RN version
newRNVersion=$(increment_version "$currentRNVersion" $position)
echo "React Native version will change from $currentRNVersion to $newRNVersion"

# Update RN version for Constants
pushd src >/dev/null
sed -i~ -e "s|DIDOMI_VERSION = \"[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|DIDOMI_VERSION = \"$newRNVersion|g" Constants.ts || exit 1
popd >/dev/null

# Update RN version in package.json
sed -i~ -e "s|\"version\": \"[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|\"version\": \"$newRNVersion|g" package.json || exit 1

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

pushd sampleApp/ >/dev/null
yarn install
popd >/dev/null

# Update Sample Android dependency
pushd sampleApp/android/app >/dev/null
sed -i~ -e "s|io.didomi.sdk:android:[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|io.didomi.sdk:android:$lastAndroidVersion|g" build.gradle || exit 1
popd >/dev/null

# Update Sample App iOS framework
pushd sampleApp/ios >/dev/null
sed -i~ -e "s|\Didomi-XCFramework ([0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|Didomi-XCFramework ($lastIosVersion|g" Podfile.lock || exit 1
sed -i~ -e "s|\Didomi-XCFramework (= [0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|Didomi-XCFramework (= $lastIosVersion|g" Podfile.lock || exit 1

pod repo update
pod install
popd >/dev/null

#
# Test App
#

pushd testApp/ >/dev/null
yarn install
popd >/dev/null

# Update Test Android dependency
pushd testApp/android/app >/dev/null
sed -i~ -e "s|io.didomi.sdk:android:[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|io.didomi.sdk:android:$lastAndroidVersion|g" build.gradle || exit 1
popd >/dev/null

# Update Test App iOS framework
pushd testApp/ios >/dev/null
sed -i~ -e "s|\Didomi-XCFramework ([0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|Didomi-XCFramework ($lastIosVersion|g" Podfile.lock || exit 1
sed -i~ -e "s|\Didomi-XCFramework (= [0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|Didomi-XCFramework (= $lastIosVersion|g" Podfile.lock || exit 1

pod repo update
pod install
popd >/dev/null

#
# Cleanup
#

# Cleanup backup files
find . -type f -name '*~' -delete

echo "Update complete"
