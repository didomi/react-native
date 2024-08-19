# Delete `package-lock.json` and `yarn.lock`
(
  [ -f package-lock.json ] && rm package-lock.json && echo "package-lock.json deleted"
  [ -f yarn.lock ] && rm yarn.lock && echo "yarn.lock deleted"
)

npm install

# Update pods
pod update --project-directory=./ios && pod install --project-directory=./ios

# Build Test App
npx react-native bundle --entry-file index.js --platform ios --dev false --bundle-output ios/main.jsbundle --assets-dest ios/assets
