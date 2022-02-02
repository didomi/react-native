#!/bin/bash

#----------------------------------------------------------
# Extract RN version (eg: 1.2.3)
# Returns the RN current version if match pattern
#----------------------------------------------------------

version=$(grep -m 1 "\"version\"" package.json | sed -r 's/^ *//;s/.*: *"//;s/",?//')
if [[ ! $version =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting RN version"
  exit 1
fi

echo "$version"
