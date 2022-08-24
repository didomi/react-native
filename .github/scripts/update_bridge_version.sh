#!/bin/bash

#----------------------------------------------------------
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
# Cleanup
#

# Cleanup backup files
find . -type f -name '*~' -delete

echo "Update complete"
