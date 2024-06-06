# Delete `package-lock.json` and `yarn.lock`
(
  [ -f package-lock.json ] && rm package-lock.json && echo "package-lock.json deleted"
  # Comment this line locally because of failure with Xcode 15 (Yoga.cpp needs to be updated to work with Xcode 15)
#   [ -f yarn.lock ] && rm yarn.lock && echo "yarn.lock deleted"
)

yarn install

# Update pods
pod update --project-directory=./ios && pod install --project-directory=./ios

# Build Test App
cp index.tsx index.js
npx react-native bundle --entry-file index.js --platform ios --dev false --bundle-output ios/main.jsbundle --assets-dest ios/assets
rm index.js
