#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(Didomi, RCTEventEmitter)

RCT_EXTERN_METHOD(multiply:(float)a withB:(float)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

//RCT_EXTERN_METHOD(initialize:(NSString *)apikey
//                  localConfigurationPath:(nullable NSString *)localConfigurationPath
//                  remoteConfigurationURL:(nullable NSString *)remoteConfigurationURL
//                  providerId:(nullable NSString *)providerId
//                  disableDidomiRemoteConfig:(BOOL)disableDidomiRemoteConfig)

RCT_EXTERN_METHOD(initialize:(NSString *)apikey
                  localConfigurationPath:(nullable NSString *)localConfigurationPath
                  remoteConfigurationURL:(nullable NSString *)remoteConfigurationURL
                  providerId:(nullable NSString *)providerId
                  disableDidomiRemoteConfig:(BOOL)disableDidomiRemoteConfig
                  languageCode:(nullable NSString *)languageCode
                  noticeId:(nullable NSString *)noticeId)

RCT_EXTERN_METHOD(setUserAgent:(NSString *)name
                  version:(NSString *)version)

RCT_EXTERN_METHOD(setUserConsentStatus:(NSArray<NSString *> *)enabledPurposeIds
                  disabledPurposeIds:(NSArray<NSString *> *)disabledPurposeIds
                  enabledVendorIds:(NSArray<NSString *> *)enabledVendorIds
                  disabledVendorIds:(NSArray<NSString *> *)disabledVendorIds
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isConsentRequired:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isUserLegitimateInterestStatusPartial:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserConsentStatusForPurpose:(NSString *)purposeId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserLegitimateInterestStatusForPurpose:(NSString *)purposeId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserStatusForVendor:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserConsentStatusForVendor:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserLegitimateInterestStatusForVendor:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserConsentStatusForVendorAndRequiredPurposes:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserLegitimateInterestStatusForVendorAndRequiredPurposes:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserStatus:(BOOL)purposesConsentStatus
                  purposesLIStatus:(BOOL)purposesLIStatus
                  vendorsConsentStatus:(BOOL)vendorsConsentStatus
                  vendorsLIStatus:(BOOL)vendorsLIStatus
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserStatusSets:(NSArray<NSString *> *)enabledConsentPurposeIds
                  disabledConsentPurposeIds:(NSArray<NSString *> *)disabledConsentPurposeIds
                  enabledLIPurposeIds:(NSArray<NSString *> *)enabledLIPurposeIds
                  disabledLIPurposeIds:(NSArray<NSString *> *)disabledLIPurposeIds
                  enabledConsentVendorIds:(NSArray<NSString *> *)enabledConsentVendorIds
                  disabledConsentVendorIds:(NSArray<NSString *> *)disabledConsentVendorIds
                  enabledLIVendorIds:(NSArray<NSString *> *)enabledLIVendorIds
                  disabledLIVendorIds:(NSArray<NSString *> *)disabledLIVendorIds
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserAgreeToAll:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserDisagreeToAll:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

//    @objc(onReady:)
//    func onReady(callback: @escaping RCTResponseSenderBlock) {
//        Didomi.shared.onReady(callback: callback)
//    }

//    @objc(onError:)
//    func onError(callback: @escaping (Didomi.DidomiErrorEvent)) {
//        Didomi.shared.onError(callback: callback)
//    }

RCT_EXTERN_METHOD(reset)

RCT_EXTERN_METHOD(getRequiredPurposeIds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getRequiredVendorIds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

//RCT_EXTERN_METHOD(addListener)

RCT_EXTERN_METHOD(isReady:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getRequiredPurposes:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getRequiredVendors:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getEnabledPurposes:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getEnabledPurposeIds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getDisabledPurposes:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getDisabledPurposeIds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getEnabledVendors:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getEnabledVendorIds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getDisabledVendors:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getDisabledVendorIds:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getPurpose:(NSString *)purposeId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getVendor:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getJavaScriptForWebView:(NSString *)extra
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(updateSelectedLanguage:(NSString *)languageCode)


// MARK: ViewProviderDelegate

RCT_EXTERN_METHOD(getNoticeViewController:(NSString *)position
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getPreferencesViewController:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)


// MARK: Didomi extension

RCT_EXTERN_METHOD(setupUI)

RCT_EXTERN_METHOD(forceShowNotice)

RCT_EXTERN_METHOD(showNotice)

RCT_EXTERN_METHOD(hideNotice)

RCT_EXTERN_METHOD(isNoticeVisible:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(shouldConsentBeCollected:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showPreferences:(nullable NSString *)view)

RCT_EXTERN_METHOD(hidePreferences)

RCT_EXTERN_METHOD(isPreferencesVisible:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getTranslatedText:(NSString *)key
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getText:(NSString *)key
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setLogLevel:(int)minLevel)

RCT_EXTERN_METHOD(setUser:(NSString *)id)

RCT_EXTERN_METHOD(setUse:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  salt:(nullable NSString *)salt
                  digest:(NSString *)digest)

RCT_EXTERN_METHOD(supportedEvents)

@end
