#!/bin/bash

#---------------------------------------------------------------------------------------------
# Update Android and iOS SDKs version (latest from repos)
# THEN
# Increment React-Native version (from param major|minor|patch)
#   No argument: use patch as default
#---------------------------------------------------------------------------------------------

sh .github/scripts/update_native_sdks.sh || exit 1
sh .github/scripts/update_bridge_version.sh "$1" || exit 1
