@file:Suppress("unused")

package com.reactnativedidomi

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.*
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.facebook.react.common.MapBuilder
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.didomi.sdk.Didomi
import io.didomi.sdk.DidomiInitializeParameters
import io.didomi.sdk.events.*
import io.didomi.sdk.exceptions.DidomiNotReadyException
import io.didomi.sdk.user.UserAuthWithEncryptionParams
import io.didomi.sdk.user.UserAuthWithHashParams

class DidomiModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    val gson = Gson()

    private val reactContext: ReactContext = reactContext
    private val eventListener = object : EventListener() {

        /**
         * The consent status of the user has changed
         */
        override fun consentChanged(event: ConsentChangedEvent) = prepareEvent(EventTypes.CONSENT_CHANGED.event, null)

        /**
         * SDK has been successfully initialized
         */
        override fun ready(event: ReadyEvent) = prepareEvent(EventTypes.READY.event, null)

        /**
         * An error occurred during the SDK initialization
         */
        override fun error(event: ErrorEvent) = prepareEvent(EventTypes.ERROR.event, event.errorMessage)

        /**
         * The notice screen is being hidden
         */
        override fun hideNotice(event: HideNoticeEvent) = prepareEvent(EventTypes.HIDE_NOTICE.event, null)

        /**
         * The notice screen is being shown
         */
        override fun showNotice(event: ShowNoticeEvent) = prepareEvent(EventTypes.SHOW_NOTICE.event, null)

        /**
         * Click on agree from notice
         */
        override fun noticeClickAgree(event: NoticeClickAgreeEvent) = prepareEvent(EventTypes.NOTICE_CLICK_AGREE.event, null)

        /**
         * Click on disagree from the notice
         */
        override fun noticeClickDisagree(event: NoticeClickDisagreeEvent) = prepareEvent(EventTypes.NOTICE_CLICK_DISAGREE.event, null)

        /**
         * Click on partners from the notice
         */
        override fun noticeClickViewVendors(event: NoticeClickViewVendorsEvent) = prepareEvent(EventTypes.NOTICE_CLICK_VIEW_VENDORS.event, null)

        /**
         * Click on SPI from the notice
         */
        override fun noticeClickViewSPIPurposes(event: NoticeClickViewSPIPurposesEvent) = prepareEvent(EventTypes.NOTICE_CLICK_VIEW_SPI_PURPOSES.event, null)

        /**
         * Click on learn more from notice
         */
        override fun noticeClickMoreInfo(event: NoticeClickMoreInfoEvent) = prepareEvent(EventTypes.NOTICE_CLICK_MORE_INFO.event, null)

        /**
         * Click on privacy policy from the notice
         */
        override fun noticeClickPrivacyPolicy(event: NoticeClickPrivacyPolicyEvent) = prepareEvent(EventTypes.NOTICE_CLICK_PRIVACY_POLICY.event, null)

        /**
         * The preferences screen is being hidden
         */
        override fun hidePreferences(event: HidePreferencesEvent) = prepareEvent(EventTypes.HIDE_PREFERENCES.event, null)

        /**
         * The preferences screen is being shown
         */
        override fun showPreferences(event: ShowPreferencesEvent) = prepareEvent(EventTypes.SHOW_PREFERENCES.event, null)

        /**
         * Click on view purposes from the preferences popup
         */
        override fun preferencesClickViewPurposes(event: PreferencesClickViewPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VIEW_PURPOSES.event, null)

        /**
         * Click view vendors on purposes view from preferences popup
         */
        override fun preferencesClickViewVendors(event: PreferencesClickViewVendorsEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VIEW_VENDORS.event, null)

        /**
         * Click on view SPI purposes from the preferences popup
         */
        override fun preferencesClickViewSPIPurposes(event: PreferencesClickViewSPIPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VIEW_SPI_PURPOSES.event, null)

        /**
         * Click on agree to all from preferences popup
         */
        override fun preferencesClickAgreeToAll(event: PreferencesClickAgreeToAllEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_AGREE_TO_ALL.event, null)

        /**
         * Click on disagree to all from preferences popup
         */
        override fun preferencesClickDisagreeToAll(event: PreferencesClickDisagreeToAllEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_DISAGREE_TO_ALL.event, null)

        /**
         * Flip ON all purposes switch from the preferences popup
         */
        override fun preferencesClickAgreeToAllPurposes(event: PreferencesClickAgreeToAllPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_AGREE_TO_ALL_PURPOSES.event, null)

        /**
         * Flip OFF all purposes switch from the preferences popup
         */
        override fun preferencesClickDisagreeToAllPurposes(event: PreferencesClickDisagreeToAllPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_DISAGREE_TO_ALL_PURPOSES.event, null)

        /**
         * Click on reset purposes from the preferences popup
         */
        override fun preferencesClickResetAllPurposes(event: PreferencesClickResetAllPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_RESET_ALL_PURPOSES.event, null)

        /**
         * Click on agree to a purpose from preferences popup
         */
        override fun preferencesClickPurposeAgree(event: PreferencesClickPurposeAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_PURPOSE_AGREE.event, event.purposeId)

        /**
         * Click on disagree to a purpose from preferences popup
         */
        override fun preferencesClickPurposeDisagree(event: PreferencesClickPurposeDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_PURPOSE_DISAGREE.event, event.purposeId)

        /**
         * Click on agree to a category from preferences popup
         */
        override fun preferencesClickCategoryAgree(event: PreferencesClickCategoryAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_CATEGORY_AGREE.event, event.categoryId)

        /**
         * Click on disagree to a category from preferences popup
         */
        override fun preferencesClickCategoryDisagree(event: PreferencesClickCategoryDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_CATEGORY_DISAGREE.event, event.categoryId)

        /**
         * Click on save on the purposes view from preferences popup
         */
        override fun preferencesClickSaveChoices(event: PreferencesClickSaveChoicesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SAVE_CHOICES.event, null)

        /**
         * Click on agree to all vendors from preferences popup
         */
        override fun preferencesClickAgreeToAllVendors(event: PreferencesClickAgreeToAllVendorsEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_AGREE_TO_ALL_VENDORS.event, null)

        /**
         * Click on disagree to all vendors from preferences popup
         */
        override fun preferencesClickDisagreeToAllVendors(event: PreferencesClickDisagreeToAllVendorsEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_DISAGREE_TO_ALL_VENDORS.event, null)

        /**
         * Click on agree to a vendor from preferences popup
         */
        override fun preferencesClickVendorAgree(event: PreferencesClickVendorAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VENDOR_AGREE.event, event.vendorId)

        /**
         * Click on disagree to a vendor from preferences popup
         */
        override fun preferencesClickVendorDisagree(event: PreferencesClickVendorDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VENDOR_DISAGREE.event, event.vendorId)

        /**
         * Click on save on the vendors view from preferences popup
         */
        override fun preferencesClickVendorSaveChoices(event: PreferencesClickVendorSaveChoicesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VENDOR_SAVE_CHOICES.event, null)

        /**
         * Click on agree to a SPI purpose from preferences popup
         */
        override fun preferencesClickSPIPurposeAgree(event: PreferencesClickSPIPurposeAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SPI_PURPOSE_AGREE.event, event.purposeId)

        /**
         * Click on disagree to a SPI purpose from preferences popup
         */
        override fun preferencesClickSPIPurposeDisagree(event: PreferencesClickSPIPurposeDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SPI_PURPOSE_DISAGREE.event, event.purposeId)

        /**
         * Click on agree to a SPI category from preferences popup
         */
        override fun preferencesClickSPICategoryAgree(event: PreferencesClickSPICategoryAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SPI_CATEGORY_AGREE.event, event.categoryId)

        /**
         * Click on disagree to a SPI category from preferences popup
         */
        override fun preferencesClickSPICategoryDisagree(event: PreferencesClickSPICategoryDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SPI_CATEGORY_DISAGREE.event, event.categoryId)

        /**
         * Click on save on the SPI purposes view from preferences popup
         */
        override fun preferencesClickSPIPurposeSaveChoices(event: PreferencesClickSPIPurposeSaveChoicesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SPI_PURPOSE_SAVE_CHOICES.event, null)

        /**
         * Sync finished
         */
        override fun syncDone(event: SyncDoneEvent) = prepareEvent(EventTypes.SYNC_DONE.event, event.organizationUserId)

        /**
         * Sync error
         */
        override fun syncError(event: SyncErrorEvent) = prepareEvent(EventTypes.SYNC_ERROR.event, event.error)

        /**
         * Language updated
         */
        override fun languageUpdated(event: LanguageUpdatedEvent) = prepareEvent(EventTypes.LANGUAGE_UPDATED.event, event.languageCode)

        /**
         * Language update failed
         */
        override fun languageUpdateFailed(event: LanguageUpdateFailedEvent) = prepareEvent(EventTypes.LANGUAGE_UPDATE_FAILED.event, event.reason)
    }

    override fun getName() = "Didomi"

    override fun getConstants(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()

        EventTypes.values().forEach {
            map[it.event] = MapBuilder.of("registrationName", it.event)
        }

        return map
    }

    //convert a data class to a map
    private fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }

    //convert a map to a data class
    inline fun <reified T> Map<String, Any>.toDataClass(): T {
        return convert()
    }

    private fun ReadableArray.toSet(): Set<String> {
        val set = mutableSetOf<String>()

        for (i in 0 until size()) {
            set.add(getString(i).orEmpty())
        }

        return set
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }

    private fun setOfStringToWritableArray(set: Set<String?>?): WritableArray {
        val array = WritableNativeArray()

        set?.forEach { array.pushString(it) }

        return array
    }

    private fun setToWritableArray(set: Set<Any?>?): WritableArray {
        val array = WritableNativeArray()

        set?.forEach { array.pushMap(objectToWritableMap(it)) }

        return array
    }

    private fun objectToWritableMap(obj: Any?): WritableMap {
        val map = WritableNativeMap()
        obj?.serializeToMap()?.entries?.forEach { entry ->
            when (val value = entry.value) {
                is Map<*, *> -> map.putMap(entry.key, objectToWritableMap(value))
                is List<*> -> {
                    if (value.size > 0 && value[0] is String) {
                        // String values
                        map.putArray(entry.key, Arguments.fromList(value))
                    } else {
                        // Other objects
                        val listValues = value.map { objectToWritableMap(it) }
                        map.putArray(entry.key, Arguments.makeNativeArray(listValues))
                    }
                }
                else -> map.putString(entry.key, value.toString())
            }
        }
        return map
    }

    @ReactMethod
    fun initialize(
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
        promise: Promise
    ) {
        try {
            Didomi.getInstance().addEventListener(eventListener)

            Didomi.getInstance().setUserAgent(userAgentName, userAgentVersion)

            val parameters = DidomiInitializeParameters(
                apiKey,
                localConfigurationPath,
                remoteConfigurationUrl,
                providerId,
                disableDidomiRemoteConfig,
                languageCode,
                noticeId,
                androidTvNoticeId,
                androidTvEnabled
            )

            val application = currentActivity?.application ?: throw IllegalStateException("No activity present")
            Didomi.getInstance().initialize(application, parameters)

            promise.resolve(0)

        } catch (e: Exception) {
            Log.e("initialize", "Error while initializing the Didomi SDK", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun onReady(promise: Promise) {
        Didomi.getInstance().onReady { prepareEvent(EventTypes.READY_CALLBACK.event, null) }
        promise.resolve(0)
    }

    @ReactMethod
    fun onError(promise: Promise) {
        Didomi.getInstance().onError { prepareEvent(EventTypes.ERROR_CALLBACK.event, null) }
        promise.resolve(0)
    }

    @ReactMethod
    fun setupUI(promise: Promise) {
        try {
            runOnUiThread {
                Didomi.getInstance().setupUI(currentActivity as? FragmentActivity)
            }
            promise.resolve(0)
        } catch (e: Exception) {
            Log.e("setupUI", "An error occurred while setting up the UI", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setLogLevel(level: Int, promise: Promise) {
        Didomi.getInstance().setLogLevel(
            when (level) {
                0 -> Log.INFO
                1 -> Log.DEBUG
                2 -> Log.WARN
                3 -> Log.ERROR
                4 -> Log.VERBOSE
                else -> Log.WARN
            }
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun getDisabledPurposes(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setToWritableArray(Didomi.getInstance().disabledPurposes))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getDisabledPurposeIds(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setOfStringToWritableArray(Didomi.getInstance().disabledPurposeIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getDisabledVendors(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setToWritableArray(Didomi.getInstance().disabledVendors))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getDisabledVendorIds(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setOfStringToWritableArray(Didomi.getInstance().disabledVendorIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledPurposes(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setToWritableArray(Didomi.getInstance().enabledPurposes))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledPurposeIds(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setOfStringToWritableArray(Didomi.getInstance().enabledPurposeIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledVendors(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setToWritableArray(Didomi.getInstance().enabledVendors))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledVendorIds(promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(setOfStringToWritableArray(Didomi.getInstance().enabledVendorIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getJavaScriptForWebView(extra: String?, promise: Promise) {
        try {
            extra?.also {
                promise.resolve(Didomi.getInstance().getJavaScriptForWebView(extra))
            } ?: run {
                promise.resolve(Didomi.getInstance().getJavaScriptForWebView())
            }
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getQueryStringForWebView(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().queryStringForWebView)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getPurpose(purposeId: String, promise: Promise) {
        try {
            promise.resolve(objectToWritableMap(Didomi.getInstance().getPurpose(purposeId)))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getRequiredPurposes(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().requiredPurposes))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getRequiredPurposeIds(promise: Promise) {
        try {
            promise.resolve(setOfStringToWritableArray(Didomi.getInstance().requiredPurposeIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getRequiredVendors(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().requiredVendors))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getRequiredVendorIds(promise: Promise) {
        try {
            promise.resolve(setOfStringToWritableArray(Didomi.getInstance().requiredVendorIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getText(textKey: String, promise: Promise) {
        try {
            val map = Didomi.getInstance().getText(textKey)
            val writableMap = WritableNativeMap()

            map?.also {
                for (elem in it) writableMap.putString(elem.key, elem.value)
            }

            promise.resolve(writableMap)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getTranslatedText(key: String, promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getTranslatedText(key))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserConsentStatusForPurpose(purposeId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserConsentStatusForPurpose(purposeId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserConsentStatusForVendor(vendorId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserConsentStatusForVendor(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserConsentStatusForVendorAndRequiredPurposes(vendorId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserConsentStatusForVendorAndRequiredPurposes(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserLegitimateInterestStatusForPurpose(purposeId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForPurpose(purposeId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserLegitimateInterestForVendor(vendorId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForVendor(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserStatus(promise: Promise) {
        try {
            promise.resolve(objectToWritableMap(Didomi.getInstance().userStatus))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserStatusForVendor(vendorId: String, promise: Promise) {
        try {
            @Suppress("DEPRECATION")
            promise.resolve(Didomi.getInstance().getUserStatusForVendor(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getVendor(vendorId: String, promise: Promise) {
        try {
            promise.resolve(objectToWritableMap(Didomi.getInstance().getVendor(vendorId)))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun hideNotice(promise: Promise) {
        try {
            Didomi.getInstance().hideNotice()
            promise.resolve(0)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun hidePreferences(promise: Promise) {
        try {
            Didomi.getInstance().hidePreferences()
            promise.resolve(0)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun isConsentRequired(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().isConsentRequired)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun isUserConsentStatusPartial(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().isUserConsentStatusPartial)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun isNoticeVisible(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().isNoticeVisible())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun isPreferencesVisible(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().isPreferencesVisible())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun isError(promise: Promise) {
        promise.resolve(Didomi.getInstance().isError)
    }

    @ReactMethod
    fun isReady(promise: Promise) {
        promise.resolve(Didomi.getInstance().isReady)
    }

    @ReactMethod
    fun clearUser(promise: Promise) {
        Didomi.getInstance().clearUser()
        promise.resolve(0)
    }

    @ReactMethod
    fun setUser(
        organizationUserId: String,
        organizationUserIdAuthAlgorithm: String?,
        organizationUserIdAuthSid: String?,
        organizationUserIdAuthSalt: String?,
        organizationUserIdAuthDigest: String?,
        promise: Promise
    ) {
        if (organizationUserIdAuthAlgorithm != null
            && organizationUserIdAuthSid != null
            && organizationUserIdAuthSalt != null
            && organizationUserIdAuthDigest != null
        ) {
            @Suppress("DEPRECATION")
            Didomi.getInstance().setUser(
                organizationUserId,
                organizationUserIdAuthAlgorithm,
                organizationUserIdAuthSid,
                organizationUserIdAuthSalt,
                organizationUserIdAuthDigest
            )
        } else {
            Didomi.getInstance().setUser(organizationUserId)
        }

        promise.resolve(0)
    }

    @ReactMethod
    fun setUserAndSetupUI(organizationUserId: String, promise: Promise) {
        Didomi.getInstance().setUser(organizationUserId, currentActivity as? FragmentActivity)
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithHashAuth(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithHashParams(
                organizationUserId,
                algorithm,
                secretId,
                digest,
                salt
            )
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithHashAuthAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithHashParams(
                organizationUserId,
                algorithm,
                secretId,
                digest,
                salt
            ),
            currentActivity as? FragmentActivity
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithHashAuthWithExpiration(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        expiration: Int,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithHashParams(
                organizationUserId,
                algorithm,
                secretId,
                digest,
                salt,
                expiration.toLong()
            )
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithHashAuthWithExpirationAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        digest: String,
        salt: String?,
        expiration: Int,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithHashParams(
                organizationUserId,
                algorithm,
                secretId,
                digest,
                salt,
                expiration.toLong()
            ),
            currentActivity as? FragmentActivity
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithEncryptionAuth(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithEncryptionParams(
                organizationUserId,
                algorithm,
                secretId,
                initializationVector
            )
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithEncryptionAuthAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithEncryptionParams(
                organizationUserId,
                algorithm,
                secretId,
                initializationVector
            ),
            currentActivity as? FragmentActivity
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithEncryptionAuthWithExpiration(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        expiration: Int,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithEncryptionParams(
                organizationUserId,
                algorithm,
                secretId,
                initializationVector,
                expiration.toLong()
            )
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithEncryptionAuthWithExpirationAndSetupUI(
        organizationUserId: String,
        algorithm: String,
        secretId: String,
        initializationVector: String,
        expiration: Int,
        promise: Promise
    ) {
        Didomi.getInstance().setUser(
            UserAuthWithEncryptionParams(
                organizationUserId,
                algorithm,
                secretId,
                initializationVector,
                expiration.toLong()
            ),
            currentActivity as? FragmentActivity
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun showNotice(promise: Promise) {
        try {
            runOnUiThread {
                Didomi.getInstance().showNotice(currentActivity as? FragmentActivity)
            }
            promise.resolve(0)
        } catch (e: Exception) {
            Log.e("showNotice", "An error occurred while showing the notice", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun showPreferences(view: String?, promise: Promise) {
        try {
            runOnUiThread {
                view?.also {
                    Didomi.getInstance().showPreferences(currentActivity as? FragmentActivity, view)
                } ?: Didomi.getInstance().showPreferences(currentActivity as? FragmentActivity)
            }
            promise.resolve(0)
        } catch (e: Exception) {
            Log.e("showPreferences", "An error occurred while showing the notice", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun reset(promise: Promise) {
        try {
            Didomi.getInstance().reset()
            promise.resolve(0)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserAgreeToAll(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().setUserAgreeToAll())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    @Deprecated("Deprecated in the Didomi API")
    fun setUserConsentStatus(
        enabledPurposeIds: ReadableArray,
        disabledPurposeIds: ReadableArray,
        enabledLegitimatePurposeIds: ReadableArray,
        disabledLegitimatePurposeIds: ReadableArray,
        enabledVendorIds: ReadableArray,
        disabledVendorIds: ReadableArray,
        enabledLegIntVendorIds: ReadableArray,
        disabledLegIntVendorIds: ReadableArray,
        promise: Promise
    ) {
        try {
            promise.resolve(
                Didomi.getInstance().setUserStatus(
                    enabledPurposeIds.toSet(),
                    disabledPurposeIds.toSet(),
                    enabledLegitimatePurposeIds.toSet(),
                    disabledLegitimatePurposeIds.toSet(),
                    enabledVendorIds.toSet(),
                    disabledVendorIds.toSet(),
                    enabledLegIntVendorIds.toSet(),
                    disabledLegIntVendorIds.toSet()
                )
            )
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserDisagreeToAll(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().setUserDisagreeToAll())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserStatus(
        purposesConsentStatus: Boolean,
        purposesLIStatus: Boolean,
        vendorsConsentStatus: Boolean,
        vendorsLIStatus: Boolean,
        promise: Promise
    ) {
        try {
            promise.resolve(
                Didomi.getInstance().setUserStatus(
                    purposesConsentStatus,
                    purposesLIStatus,
                    vendorsConsentStatus,
                    vendorsLIStatus
                )
            )
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserStatusSets(
        enabledConsentPurposeIds: ReadableArray,
        disabledConsentPurposeIds: ReadableArray,
        enabledLIPurposeIds: ReadableArray,
        disabledLIPurposeIds: ReadableArray,
        enabledConsentVendorIds: ReadableArray,
        disabledConsentVendorIds: ReadableArray,
        enabledLIVendorIds: ReadableArray,
        disabledLIVendorIds: ReadableArray,
        promise: Promise
    ) {
        try {
            promise.resolve(
                Didomi.getInstance().setUserStatus(
                    enabledConsentPurposeIds.toSet(),
                    disabledConsentPurposeIds.toSet(),
                    enabledLIPurposeIds.toSet(),
                    disabledLIPurposeIds.toSet(),
                    enabledConsentVendorIds.toSet(),
                    disabledConsentVendorIds.toSet(),
                    enabledLIVendorIds.toSet(),
                    disabledLIVendorIds.toSet()
                )
            )
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun shouldConsentBeCollected(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().shouldConsentBeCollected())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun updateSelectedLanguage(languageCode: String, promise: Promise) {
        try {
            Didomi.getInstance().updateSelectedLanguage(languageCode)
            promise.resolve(0)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    @ReactMethod
    fun addListener(eventName: String) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @Suppress("UNUSED_PARAMETER")
    @ReactMethod
    fun removeListeners(count: Int) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    private fun prepareEvent(eventName: String, params: String?) {
        Log.d("prepareEvent", "Sending $eventName")
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}
