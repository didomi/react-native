package io.didomi.reactnative

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReadableArray

/**
 * Old-architecture base class.
 *
 * Under the new architecture this class is replaced (via Gradle source-set selection)
 * by one that extends the codegen-generated `NativeDidomiSpec`. The codegen base declares
 * all spec methods as `open`, so subclasses must mark them `override`. To keep
 * `DidomiModule` source identical between architectures, the old-arch variant of
 * `DidomiModuleSpec` declares the same methods here as `open` stubs that throw
 * if not overridden — they are always overridden by `DidomiModule`.
 */
abstract class DidomiModuleSpec(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    private fun stub(): Nothing =
        throw NotImplementedError("DidomiModuleSpec stub: subclass must override")

    open fun initialize(
        userAgentName: String,
        userAgentVersion: String,
        apiKey: String,
        localConfigurationPath: String?,
        remoteConfigurationUrl: String?,
        providerId: String?,
        disableDidomiRemoteConfig: Boolean,
        languageCode: String?,
        noticeId: String?,
        androidTvNoticeId: String?,
        androidTvEnabled: Boolean,
        countryCode: String?,
        regionCode: String?,
        promise: Promise
    ): Unit = stub()

    open fun initializeWithParameters(
        userAgentName: String,
        userAgentVersion: String,
        jsonParameters: String,
        promise: Promise
    ): Unit = stub()

    open fun onReady(promise: Promise): Unit = stub()
    open fun onError(promise: Promise): Unit = stub()
    open fun setupUI(promise: Promise): Unit = stub()
    open fun setLogLevel(level: Double, promise: Promise): Unit = stub()
    open fun getJavaScriptForWebView(extra: String?, promise: Promise): Unit = stub()
    open fun getQueryStringForWebView(promise: Promise): Unit = stub()
    open fun getPurpose(purposeId: String, promise: Promise): Unit = stub()
    open fun getRequiredPurposes(promise: Promise): Unit = stub()
    open fun getRequiredPurposeIds(promise: Promise): Unit = stub()
    open fun getVendor(vendorId: String, promise: Promise): Unit = stub()
    open fun getTotalVendorCount(promise: Promise): Unit = stub()
    open fun getIabVendorCount(promise: Promise): Unit = stub()
    open fun getNonIabVendorCount(promise: Promise): Unit = stub()
    open fun getRequiredVendors(promise: Promise): Unit = stub()
    open fun getRequiredVendorIds(promise: Promise): Unit = stub()
    open fun getText(textKey: String, promise: Promise): Unit = stub()
    open fun getTranslatedText(key: String, promise: Promise): Unit = stub()
    open fun getCurrentUserStatus(promise: Promise): Unit = stub()
    open fun getUserStatus(promise: Promise): Unit = stub()
    open fun getApplicableRegulation(promise: Promise): Unit = stub()
    open fun hideNotice(promise: Promise): Unit = stub()
    open fun hidePreferences(promise: Promise): Unit = stub()
    open fun isConsentRequired(promise: Promise): Unit = stub()
    open fun shouldUserStatusBeCollected(promise: Promise): Unit = stub()
    open fun isUserConsentStatusPartial(promise: Promise): Unit = stub()
    open fun isUserStatusPartial(promise: Promise): Unit = stub()
    open fun isUserLegitimateInterestStatusPartial(promise: Promise): Unit = stub()
    open fun isNoticeVisible(promise: Promise): Unit = stub()
    open fun isPreferencesVisible(promise: Promise): Unit = stub()
    open fun isError(promise: Promise): Unit = stub()
    open fun isReady(promise: Promise): Unit = stub()
    open fun clearUser(promise: Promise): Unit = stub()
    open fun setUser(organizationUserId: String, promise: Promise): Unit = stub()
    open fun setUserAndSetupUI(organizationUserId: String, promise: Promise): Unit = stub()

    open fun setUserWithHashAuth(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithHashAuthAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithHashAuthWithExpiration(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        expiration: Double,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithHashAuthWithExpirationAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        expiration: Double,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithEncryptionAuth(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithEncryptionAuthAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithEncryptionAuthWithExpiration(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        expiration: Double,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithEncryptionAuthWithExpirationAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        expiration: Double,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithAuthParams(
        jsonUserAuthParams: String,
        jsonSynchronizedUsers: String?,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithAuthParamsAndSetupUI(
        jsonUserAuthParams: String,
        jsonSynchronizedUsers: String?,
        promise: Promise
    ): Unit = stub()

    open fun setUserWithParameters(jsonUserParameters: String, promise: Promise): Unit = stub()
    open fun setUserWithParametersAndSetupUI(jsonUserParameters: String, promise: Promise): Unit = stub()
    open fun showNotice(promise: Promise): Unit = stub()
    open fun forceShowNotice(promise: Promise): Unit = stub()
    open fun showPreferences(view: String?, promise: Promise): Unit = stub()
    open fun reset(promise: Promise): Unit = stub()
    open fun setUserAgreeToAll(promise: Promise): Unit = stub()
    open fun setUserDisagreeToAll(promise: Promise): Unit = stub()
    open fun setCurrentUserStatus(currentUserStatusAsString: String, promise: Promise): Unit = stub()

    open fun setUserStatus(
        purposesConsentStatus: Boolean,
        purposesLIStatus: Boolean,
        vendorsConsentStatus: Boolean,
        vendorsLIStatus: Boolean,
        promise: Promise
    ): Unit = stub()

    open fun setUserConsentStatus(
        enabledPurposeIds: ReadableArray,
        disabledPurposeIds: ReadableArray,
        enabledVendorIds: ReadableArray,
        disabledVendorIds: ReadableArray,
        promise: Promise
    ): Unit = stub()

    open fun setUserStatusSets(
        enabledConsentPurposeIds: ReadableArray,
        disabledConsentPurposeIds: ReadableArray,
        enabledLIPurposeIds: ReadableArray,
        disabledLIPurposeIds: ReadableArray,
        enabledConsentVendorIds: ReadableArray,
        disabledConsentVendorIds: ReadableArray,
        enabledLIVendorIds: ReadableArray,
        disabledLIVendorIds: ReadableArray,
        promise: Promise
    ): Unit = stub()

    open fun shouldConsentBeCollected(promise: Promise): Unit = stub()
    open fun updateSelectedLanguage(languageCode: String, promise: Promise): Unit = stub()
    open fun addListener(eventName: String): Unit = stub()
    open fun removeListeners(count: Double): Unit = stub()
    open fun listenToVendorStatus(vendorId: String, promise: Promise): Unit = stub()
    open fun stopListeningToVendorStatus(vendorId: String, promise: Promise): Unit = stub()

    open fun commitCurrentUserStatusTransaction(
        enabledPurposes: ReadableArray,
        disabledPurposes: ReadableArray,
        enabledVendors: ReadableArray,
        disabledVendors: ReadableArray,
        promise: Promise
    ): Unit = stub()

    open fun syncAcknowledged(callbackIndex: Double, promise: Promise): Unit = stub()
    open fun removeSyncAcknowledgedCallback(callbackIndex: Double, promise: Promise): Unit = stub()
}
