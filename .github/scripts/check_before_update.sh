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
currentversion=$(cat android/build.gradle | sed -n 's|.*io.didomi.sdk:android:\([^"]*\)".*|\1|p')
if [[ -z $currentversion ]]; then
  echo "Error while getting android SDK current version"
  exit 1
fi

lastversion=$(curl -s 'https://search.maven.org/solrsearch/select?q=didomi' | sed -n 's|.*"latestVersion":"\([^"]*\)".*|\1|p')
if [[ -z $lastversion ]]; then
  echo "Error while getting android SDK latest version"
  exit 1
fi

if [[ "$currentversion" == "$lastversion" ]]; then
  echo "No change for Android SDK: $currentversion"
else
  changes=$changes+1

  echo "Android SDK current version needs update: $currentversion to $lastversion"
fi

# Check ios SDK Version
currentversion=$(cat react-native-didomi.podspec | sed -n "s|.*s.dependency[ ]*\"Didomi-XCFramework\", \"\([^']*\)\".*|\1|p")
if [[ -z $currentversion ]]; then
  echo "Error while getting iOS SDK current version"
  exit 1
fi

lastversion=$(pod_last_version)
if [[ -z $lastversion ]]; then
  echo "Error while getting iOS SDK version"
  exit 1
fi

if [[ "$currentversion" == "$lastversion" ]]; then
  echo "No change for iOS SDK: $currentversion"
else
  changes=$changes+1

  echo "iOS SDK current version needs update: $currentversion to $lastversion"
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
