#!/bin/bash

#----------------------------------------------------------
# Update Android and iOS SDKs version (latest from repos)
#----------------------------------------------------------

# Get last version from pod
pod_last_version() {
  version=""
  temp_file=$(mktemp)
  pod trunk info Didomi-XCFramework > "$temp_file"
  
  while IFS= read -r line; do
    if [[ "$line" =~ ^[[:space:]]*-[[:space:]]*([0-9]+\.[0-9]+\.[0-9]+) ]]; then
      current_version="${BASH_REMATCH[1]}"
      if [[ -z "$version" || $(printf '%s\n' "$version" "$current_version" | sort -V | tail -n1) == "$current_version" ]]; then
        version=$current_version
      fi
    fi
  done < "$temp_file"
  
  rm "$temp_file"
  echo "$version"
}

#
# Android
#

# Get Android SDK Version
lastAndroidVersion=$(curl -s 'https://repo.maven.apache.org/maven2/io/didomi/sdk/android/maven-metadata.xml' | sed -ne '/release/{s/.*<release>\(.*\)<\/release>.*/\1/p;q;}')
if [[ ! $lastAndroidVersion =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting android SDK version"
  exit 1
fi

echo "Android SDK last version is $lastAndroidVersion"

# Update Android dependency
sed -i~ -e "s|io.didomi.sdk:android:[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|io.didomi.sdk:android:$lastAndroidVersion|g" android/build.gradle || exit 1

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
# Cleanup
#

# Cleanup backup files
find . -type f -name '*~' -delete

echo "Update complete"
