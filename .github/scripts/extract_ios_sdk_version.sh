#!/bin/bash

#----------------------------------------------------------
# Extract iOS SDK version (eg: 1.2.3)
# Returns the iOS SDK current version if match pattern
#----------------------------------------------------------

version=$(cat react-native-didomi.podspec | sed -n "s|.*s.dependency[ ]*\"Didomi-XCFramework\", \"\([^']*\)\".*|\1|p")
if [[ ! $version =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting iOS version"
  exit 1
fi

echo "$version"