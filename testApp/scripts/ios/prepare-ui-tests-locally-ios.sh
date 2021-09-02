yarn install
cd ios && pod install && cd ..
cp index.tsx index.js
npx react-native bundle --entry-file index.js --platform ios --dev false --bundle-output ios/main.jsbundle --assets-dest ios/assets
rm index.js
