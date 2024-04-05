#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(Didomi, RCTEventEmitter)

RCT_EXTERN_METHOD(initialize:(NSString *)userAgentName
                  userAgentVersion:(NSString *)userAgentVersion
                  apiKey:(NSString *)apiKey
                  localConfigurationPath:(nullable NSString *)localConfigurationPath
                  remoteConfigurationURL:(nullable NSString *)remoteConfigurationURL
                  providerId:(nullable NSString *)providerId
                  disableDidomiRemoteConfig:(BOOL)disableDidomiRemoteConfig
                  languageCode:(nullable NSString *)languageCode
                  noticeId:(nullable NSString *)noticeId
                  androidTvNoticeId:(nullable NSString *)androidTvNoticeId
                  androidTvEnabled:(BOOL)androidTvEnabled
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(onReady:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(onError:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getQueryStringForWebView:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserConsentStatus:(NSArray<NSString *> *)enabledPurposeIds
                  disabledPurposeIds:(NSArray<NSString *> *)disabledPurposeIds
                  enabledVendorIds:(NSArray<NSString *> *)enabledVendorIds
                  disabledVendorIds:(NSArray<NSString *> *)disabledVendorIds
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isConsentRequired:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(shouldUserStatusBeCollected:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isUserLegitimateInterestStatusPartial:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isUserStatusPartial:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getCurrentUserStatus:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getUserStatus:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setCurrentUserStatus:(NSString *)currentUserStatusAsString
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

RCT_EXTERN_METHOD(reset:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

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

RCT_EXTERN_METHOD(getPurpose:(NSString *)purposeId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getVendor:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getJavaScriptForWebView:(NSString *)extra
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(updateSelectedLanguage:(NSString *)languageCode
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

// MARK: Didomi extension

RCT_EXTERN_METHOD(setupUI:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(forceShowNotice:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showNotice:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(hideNotice:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isNoticeVisible:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(shouldConsentBeCollected:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showPreferences:(nullable NSString *)view
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(hidePreferences:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(isPreferencesVisible:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getTranslatedText:(NSString *)key
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getText:(NSString *)key
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setLogLevel:(int)minLevel
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(clearUser:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUser:(NSString *)id
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserAndSetupUI:(NSString *)id
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithHashAuth:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  digest:(NSString *)digest
                  salt:(nullable NSString *)salt
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithHashAuthAndSetupUI:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  digest:(NSString *)digest
                  salt:(nullable NSString *)salt
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithHashAuthWithExpiration:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  digest:(NSString *)digest
                  salt:(nullable NSString *)salt
                  expiration: (double)expiration
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithHashAuthWithExpirationAndSetupUI:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  digest:(NSString *)digest
                  salt:(nullable NSString *)salt
                  expiration: (double)expiration
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithEncryptionAuth:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  initializationVector:(NSString *)initializationVector
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithEncryptionAuthAndSetupUI:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  initializationVector:(NSString *)initializationVector
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithEncryptionAuthWithExpiration:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  initializationVector:(NSString *)initializationVector
                  expiration: (double)expiration
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setUserWithEncryptionAuthWithExpirationAndSetupUI:(NSString *)id
                  algorithm:(NSString *)algorithm
                  secretId:(NSString *)secretId
                  initializationVector:(NSString *)initializationVector
                  expiration: (double)expiration
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(listenToVendorStatus:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(stopListeningToVendorStatus:(NSString *)vendorId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(commitCurrentUserStatusTransaction:(NSArray<NSString *> *)enabledPurposes
                  disabledPurposes:(NSArray<NSString *> *)disabledPurposes
                  enabledVendors:(NSArray<NSString *> *)enabledVendors
                  disabledVendors:(NSArray<NSString *> *)disabledVendors
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(supportedEvents)

@end
