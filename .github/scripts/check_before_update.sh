#!/bin/bash

#----------------------------------------------------------
# Check new versions for android and iOS sdks
# exit with error if no update available (abort pipeline)
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

changes=0

# Check android SDK Version
currentVersion=$(sh .github/scripts/extract_android_sdk_version.sh)
if [[ -z $currentVersion ]]; then
  echo "Error while getting android SDK current version"
  exit 1
fi

lastVersion=$(curl -s 'https://repo.maven.apache.org/maven2/io/didomi/sdk/android/maven-metadata.xml' | sed -ne '/release/{s/.*<release>\(.*\)<\/release>.*/\1/p;q;}')
if [[ ! $lastVersion =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting android SDK version"
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
