#!/bin/bash

#----------------------------------------------------------
# Extract Android SDK version (eg: 1.2.3)
# Returns the Android SDK current version if match pattern
#----------------------------------------------------------

version=$(cat android/build.gradle | sed -n 's|.*io.didomi.sdk:android:\([^"]*\)".*|\1|p')
if [[ -z $version ]]; then
  echo "Error while getting android SDK current version"
  exit 1
fi

echo "$version"
