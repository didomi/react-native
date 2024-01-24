./scripts/ios/prepare-ui-tests-locally-ios.sh || exit 1

cd ios && TEST=1 && RCT_NO_LAUNCH_PACKAGER=1 xcodebuild \
-workspace DidomiExample.xcworkspace \
-scheme DidomiExample \
-sdk iphonesimulator \
-destination "platform=iOS Simulator,name=iPhone 15" \
clean test
