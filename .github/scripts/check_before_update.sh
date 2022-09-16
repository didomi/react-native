#!/bin/bash

#----------------------------------------------------------
# Check new versions for android and iOS sdks
# exit with error if no update available (abort pipeline)
#----------------------------------------------------------

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

changes=0

# Check android SDK Version
currentVersion=$(sh .github/scripts/extract_android_sdk_version.sh)
if [[ -z $currentVersion ]]; then
  echo "Error while getting android SDK current version"
  exit 1
fi

lastVersion=$(curl -s 'https://search.maven.org/solrsearch/select?q=didomi' | sed -n 's|.*"latestVersion":"\([^"]*\)".*|\1|p')
if [[ -z $lastVersion ]]; then
  echo "Error while getting android SDK latest version"
  exit 1
fi

if [[ "$currentVersion" == "$lastVersion" ]]; then
  echo "No change for Android SDK: $currentVersion"
else
  changes=$changes+1

  echo "Android SDK current version needs update: $currentVersion to $lastVersion"
fi

# Check ios SDK Version
currentVersion=$(sh .github/scripts/extract_ios_sdk_version.sh)
if [[ -z $currentVersion ]]; then
  echo "Error while getting iOS SDK current version"
  exit 1
fi

lastVersion=$(pod_last_version)
if [[ -z $lastVersion ]]; then
  echo "Error while getting iOS SDK version"
  exit 1
fi

if [[ "$currentVersion" == "$lastVersion" ]]; then
  echo "No change for iOS SDK: $currentVersion"
else
  changes=$changes+1

  echo "iOS SDK current version needs update: $currentVersion to $lastVersion"
fi

if [[ $changes == 0 ]]; then
  echo "--------------------"
  echo "| No change, abort! |"
  echo "--------------------"
  exit 1
else
  # continue
  exit 0
fi
