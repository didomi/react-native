#!/bin/bash

#----------------------------------------------------------
# Update Android and iOS SDKs version (latest from repos)
# Increment React-Native version (from param major|minor|patch)
#   No argument: use patch as default
# Update React-Native dependencies
# Commit and push changes
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
  lastversion=""
  for line in $(pod trunk info Didomi-XCFramework); do
    if [[ "$line" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
      lastversion=$line
    fi
  done
  echo "$lastversion"
}

#
# React Native
#

# Get RN version
version=$(sh .github/scripts/extract_version.sh)
if [[ ! $version =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting RN version ($version)"
  exit 1
fi

echo "Current version is $version"

# Increment RN version
rnversion=$(increment_version "$version" $position)
echo "React Native version will change from $version to $rnversion"

# Update RN version for Constants
pushd src >/dev/null
sed -i~ -e "s|DIDOMI_VERSION = \"[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|DIDOMI_VERSION = \"$rnversion|g" Constants.ts || exit 1
popd >/dev/null

# Update RN version in package.json
sed -i~ -e "s|\"version\": \"[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|\"version\": \"$rnversion|g" package.json || exit 1

#
# Android
#

# Get Android SDK Version
version=$(curl -s 'https://search.maven.org/solrsearch/select?q=didomi' | sed -n 's|.*"latestVersion":"\([^"]*\)".*|\1|p')
if [[ ! $version =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting android SDK version"
  exit 1
fi

echo "Android SDK last version is $version"

# Update Android dependency
pushd android >/dev/null
sed -i~ -e "s|io.didomi.sdk:android:[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|io.didomi.sdk:android:$version|g" build.gradle || exit 1
popd >/dev/null

#
# iOS
#

# Get iOS SDK Version
version=$(pod_last_version)
if [[ ! $version =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting ios SDK version"
  exit 1
fi

echo "iOS SDK last version is $version"

# Update iOS dependency
sed -i~ -e "s|s.dependency \"Didomi-XCFramework\", \"[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}\"|s.dependency \"Didomi-XCFramework\", \"$version\"|g" react-native-didomi.podspec || exit 1

# Cleanup
rm -rf **/*.*~

echo "Update complete"
