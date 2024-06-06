# Delete `package-lock.json` and `yarn.lock`
(
  [ -f package-lock.json ] && rm package-lock.json && echo "package-lock.json deleted"
  [ -f yarn.lock ] && rm yarn.lock && echo "yarn.lock deleted"
)

yarn install

#  Makes gradlew executable
chmod +x ./android/gradlew

#  Build the test app
npx react-native bundle --platform android --dev false --entry-file index.tsx --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res/
