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
import io.didomi.sdk.Purpose
import io.didomi.sdk.events.*
import io.didomi.sdk.exceptions.DidomiNotReadyException


class DidomiModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    val gson = Gson()

    private val reactContext: ReactContext = reactContext
    private val eventListener = object : EventListener() {

        /**
         * SDK has been successfully initialized
         */
        override fun ready(event: ReadyEvent) = prepareEvent(EventTypes.READY.event, null)

        /**
         * An error occurred during the SDK initialization
         */
        override fun error(event: ErrorEvent) = prepareEvent(EventTypes.ERROR.event, event.errorMessage)

        /**
         * The consent status of the user has changed
         */
        override fun consentChanged(event: ConsentChangedEvent) = prepareEvent(EventTypes.CONSENT_CHANGED.event, null)

        /**
         * The notice is being hidden
         */
        override fun hideNotice(event: HideNoticeEvent) = prepareEvent(EventTypes.HIDE_NOTICE.event, null)

        /**
         * The notice is being shown
         */
        override fun showNotice(event: ShowNoticeEvent) = prepareEvent(EventTypes.SHOW_NOTICE.event, null)

        /**
         * Click on agree on notice
         */
        override fun noticeClickAgree(event: NoticeClickAgreeEvent) = prepareEvent(EventTypes.NOTICE_CLICK_AGREE.event, null)

        /**
         * Click on disagree on the notice
         */
        override fun noticeClickDisagree(event: NoticeClickDisagreeEvent) = prepareEvent(EventTypes.NOTICE_CLICK_DISAGREE.event, null)

        /**
         * Click on learn more on notice
         */
        override fun noticeClickMoreInfo(event: NoticeClickMoreInfoEvent) = prepareEvent(EventTypes.NOTICE_CLICK_MORE_INFO.event, null)

        /**
         * Click on partners on the notice
         */
        override fun noticeClickViewVendors(event: NoticeClickViewVendorsEvent) = prepareEvent(EventTypes.NOTICE_CLICK_VIEW_VENDORS.event, null)

        /**
         * Click on privacy policy on the notice
         */
        override fun noticeClickPrivacyPolicy(event: NoticeClickPrivacyPolicyEvent) = prepareEvent(EventTypes.NOTICE_CLICK_PRIVACY_POLICY.event, null)

        /**
         * Click on agree to all on preferences popup
         */
        override fun preferencesClickAgreeToAll(event: PreferencesClickAgreeToAllEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_AGREE_TO_ALL.event, null)

        /**
         * Click on disagree to all on preferences popup
         */
        override fun preferencesClickDisagreeToAll(event: PreferencesClickDisagreeToAllEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_DISAGREE_TO_ALL.event, null)

        /**
         * Click on agree to a purpose on preferences popup
         */
        override fun preferencesClickPurposeAgree(event: PreferencesClickPurposeAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_PURPOSE_AGREE.event, event.purposeId)

        /**
         * Click on disagree to a purpose on preferences popup
         */
        override fun preferencesClickPurposeDisagree(event: PreferencesClickPurposeDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_PURPOSE_DISAGREE.event, event.purposeId)

        /**
         * Click view vendors on purposes view on preferences popup
         */
        override fun preferencesClickViewVendors(event: PreferencesClickViewVendorsEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VIEW_VENDORS.event, null)

        /**
         * Click on save on the purposes view on preferences popup
         */
        override fun preferencesClickSaveChoices(event: PreferencesClickSaveChoicesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_SAVE_CHOICES.event, null)

        /**
         * Click on agree to a vendor on preferences popup
         */
        override fun preferencesClickVendorAgree(event: PreferencesClickVendorAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VENDOR_AGREE.event, event.vendorId)

        /**
         * Click on disagree to a vendor on preferences popup
         */
        override fun preferencesClickVendorDisagree(event: PreferencesClickVendorDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VENDOR_DISAGREE.event, event.vendorId)

        /**
         * Click on agree to a category on preferences popup
         */
        override fun preferencesClickCategoryAgree(event: PreferencesClickCategoryAgreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_CATEGORY_AGREE.event, event.categoryId)

        /**
         * Click on disagree to a category on preferences popup
         */
        override fun preferencesClickCategoryDisagree(event: PreferencesClickCategoryDisagreeEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_CATEGORY_DISAGREE.event, event.categoryId)

        /**
         * Click on agree to all vendors on preferences popup
         */
        override fun preferencesClickAgreeToAllVendors(event: PreferencesClickAgreeToAllVendorsEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_AGREE_TO_ALL_VENDORS.event, null)

        /**
         * Click on disagree to all vendors on preferences popup
         */
        override fun preferencesClickDisagreeToAllVendors(event: PreferencesClickDisagreeToAllVendorsEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_DISAGREE_TO_ALL_VENDORS.event, null)

        /**
         * Click on save on the vendors view on preferences popup
         */
        override fun preferencesClickVendorSaveChoices(event: PreferencesClickVendorSaveChoicesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VENDOR_SAVE_CHOICES.event, null)

        /**
         * Click on view purposes on the preferences popup
         */
        override fun preferencesClickViewPurposes(event: PreferencesClickViewPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_VIEW_PURPOSES.event, null)

        /**
         * Flip ON all purposes switch on the preferences popup
         */
        override fun preferencesClickAgreeToAllPurposes(event: PreferencesClickAgreeToAllPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_AGREE_TO_ALL_PURPOSES.event, null)

        /**
         * Flip OFF all purposes switch on the preferences popup
         */
        override fun preferencesClickDisagreeToAllPurposes(event: PreferencesClickDisagreeToAllPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_DISAGREE_TO_ALL_PURPOSES.event, null)

        /**
         * Click on reset purposes on the preferences popup
         */
        override fun preferencesClickResetAllPurposes(event: PreferencesClickResetAllPurposesEvent) = prepareEvent(EventTypes.PREFERENCES_CLICK_RESET_ALL_PURPOSES.event, null)

        /**
         * Sync finished
         */
        override fun syncDone(event: SyncDoneEvent) = prepareEvent(EventTypes.SYNC_DONE.event, event.organizationUserId)

//      override fun preferencesClickAgreeToAllVendors(event: PreferencesClickAgreeToAllVendorsEvent) {
//        // Flip ON all vendors switch on the preferences popup
//      }
//
//      override fun preferencesClickDisagreeToAllVendors(event: PreferencesClickDisagreeToAllVendorsEvent) {
//        // Flip OFF all vendors switch on the preferences popup
//      }
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

    private fun ReadableMap.toSet(): Set<String> {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }

    private fun setToWritableArray(set: Set<Any?>?): WritableArray {
        val array = WritableNativeArray()

        set?.forEach { array.pushMap(objectToWritableMap(it)) }
        return array
    }

    private fun objectToWritableMap(obj: Any?): WritableMap {
        var map = WritableNativeMap()
        obj.serializeToMap().entries.forEach { entry -> map.putString(entry.key, entry.value.toString()) }
        return map
    }

    private fun initialize(promise: Promise) {
        try {

            Didomi.getInstance().initialize(
                    currentActivity?.application,
                    "465ca0b2-b96f-43b4-a864-f87e18d2fd38",
                    null,
                    null,
                    null,
                    true
            )

            // Do not use the Didomi.getInstance() object here for anything else than registering your ready listener
            // The SDK might not be ready yet

//            Didomi.getInstance().onReady {
//                Log.d("App", "----------------OK------------------")
//                prepareEvent(EventTypes.READY.event, null)
//            }
//            Didomi.getInstance().onError {
//                Log.d("App", "----------------ERROR------------------")
//                prepareEvent(EventTypes.ERROR.event, null)
//                promise.reject("-2", "error")
//            }
        } catch (e: Exception) {
            Log.e("initialize", "Error while initializing the Didomi SDK", e);
            promise.reject("-1", "error")
        }
        promise.resolve(currentActivity?.application.toString())
    }

    @ReactMethod
    fun initialize(apiKey: String,
                   localConfigurationPath: String?,
                   remoteConfigurationUrl: String?,
                   providerId: String?,
                   disableDidomiRemoteConfig: Boolean?,
                   promise: Promise) {
        try {
            Didomi.getInstance().addEventListener(eventListener)

            if (apiKey.isEmpty())
                initialize(promise)
            else
                disableDidomiRemoteConfig?.let {
                    Didomi.getInstance().initialize(
                            currentActivity?.application,
                            apiKey,
                            localConfigurationPath,
                            remoteConfigurationUrl,
                            providerId,
                            disableDidomiRemoteConfig
                    )
                } ?: kotlin.run {
                    Didomi.getInstance().initialize(
                            currentActivity?.application,
                            apiKey,
                            localConfigurationPath,
                            remoteConfigurationUrl,
                            providerId,
                            true
                    )
                }

//            Didomi.getInstance().onReady {
//                Log.d("App", "----------------OK------------------")
//                prepareEvent(EventTypes.READY.event, null)
//            }
//            Didomi.getInstance().onError {
//                Log.d("App", "----------------ERROR------------------")
//                prepareEvent(EventTypes.ERROR.event, null)
//            }

        } catch (e: Exception) {
            Log.e("initialize", "Error while initializing the Didomi SDK", e);
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setupUI(promise: Promise) {
        currentActivity?.let {
            if (currentActivity is FragmentActivity) {
                try {
                    runOnUiThread {
                        Didomi.getInstance().setupUI(currentActivity as FragmentActivity)
                    }
                    promise.resolve(0)
                } catch (e: DidomiNotReadyException) {
                    Log.e("setupUI", "Didomi is not ready", e)
                    promise.reject(e)
                }
            } else
                promise.reject("2", "The current activity must be a FragmentActivity")
        } ?: kotlin.run {
            promise.reject("1", "The current activity is NULL")
        }
    }

    @ReactMethod
    fun setLogLevel(level: Int, promise: Promise) {
        Didomi.getInstance().setLogLevel(when (level) {
            0 -> Log.INFO
            1 -> Log.DEBUG
            2 -> Log.WARN
            3 -> Log.ERROR
            4 -> Log.VERBOSE
            else -> Log.WARN
        })
        promise.resolve(0)
    }

    @ReactMethod
    fun getDisabledPurposes(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().disabledPurposes))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getDisabledPurposeIds(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().disabledPurposeIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getDisabledVendors(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().disabledVendors))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getDisabledVendorIds(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().disabledVendorIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledPurposes(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().enabledPurposes))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledPurposeIds(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().enabledPurposeIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledVendors(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().enabledVendors))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getEnabledVendorIds(promise: Promise) {
        try {
            promise.resolve(setToWritableArray(Didomi.getInstance().enabledVendorIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getJavaScriptForWebView(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().javaScriptForWebView)
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
            promise.resolve(setToWritableArray(Didomi.getInstance().requiredPurposeIds))
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
            promise.resolve(setToWritableArray(Didomi.getInstance().requiredVendorIds))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getText(textKey: String, promise: Promise) {
        try {
            val map = Didomi.getInstance().getText(textKey)
            val writableMap = WritableNativeMap()

            for (elem in map) writableMap.putString(elem.key, elem.value)

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
            promise.resolve(Didomi.getInstance().getUserConsentStatusForPurpose(purposeId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserConsentStatusForVendor(vendorId: String, promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getUserConsentStatusForVendor(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserConsentStatusForVendorAndRequiredPurposes(vendorId: String, promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getUserConsentStatusForVendorAndRequiredPurposes(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserLegitimateInterestStatusForPurpose(purposeId: String, promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForPurpose(purposeId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserLegitimateInterestForVendor(vendorId: String, promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForVendor(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId: String, promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getUserStatusForVendor(vendorId: String, promise: Promise) {
        try {
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
            promise.resolve(Didomi.getInstance().isNoticeVisible)
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun isPreferencesVisible(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().isPreferencesVisible)
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
    fun setUser(organizationUserId: String,
                organizationUserIdAuthAlgorithm: String?,
                organizationUserIdAuthSid: String?,
                organizationUserIdAuthSalt: String?,
                organizationUserIdAuthDigest: String?,
                promise: Promise) {
        if (organizationUserIdAuthAlgorithm != null
                && organizationUserIdAuthSid != null
                && organizationUserIdAuthSalt != null
                && organizationUserIdAuthDigest != null)
            Didomi.getInstance().setUser(
                    organizationUserId,
                    organizationUserIdAuthAlgorithm,
                    organizationUserIdAuthSid,
                    organizationUserIdAuthSalt,
                    organizationUserIdAuthDigest
            )
        else
            Didomi.getInstance().setUser(organizationUserId)

        promise.resolve(0)
    }

    @ReactMethod
    fun showNotice(promise: Promise) {
        currentActivity?.let {
            if (currentActivity is FragmentActivity) {
                try {
                    runOnUiThread {
                        Didomi.getInstance().showNotice(currentActivity as FragmentActivity)
                    }
                    promise.resolve(0)
                } catch (e: DidomiNotReadyException) {
                    promise.reject(e)
                }
            } else
                promise.reject("2", "The current activity must be a FragmentActivity")
        } ?: kotlin.run {
            promise.reject("1", "The current activity is NULL")
        }
    }

    @ReactMethod
    fun showPreferences(view: String?, promise: Promise) {
        currentActivity?.let {
            if (currentActivity is FragmentActivity) {
                try {
                    view?.let {
                        Didomi.getInstance().showPreferences(currentActivity as FragmentActivity, view)
                    } ?: kotlin.run {
                        Didomi.getInstance().showPreferences(currentActivity as FragmentActivity)
                    }
                    promise.resolve(0)
                } catch (e: DidomiNotReadyException) {
                    promise.reject(e)
                }
            } else
                promise.reject("2", "The current activity must be a FragmentActivity")
        } ?: kotlin.run {
            promise.reject("1", "The current activity is NULL")
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
    fun setUserConsentStatus(enabledPurposeIds: ReadableMap,
                             disabledPurposeIds: ReadableMap,
                             enabledLegitimatePurposeIds: ReadableMap,
                             disabledLegitimatePurposeIds: ReadableMap,
                             enabledVendorIds: ReadableMap,
                             disabledVendorIds: ReadableMap,
                             enabledLegIntVendorIds: ReadableMap,
                             disabledLegIntVendorIds: ReadableMap,
                             promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().setUserConsentStatus(
                    enabledPurposeIds.toSet(),
                    disabledPurposeIds.toSet(),
                    enabledLegitimatePurposeIds.toSet(),
                    disabledLegitimatePurposeIds.toSet(),
                    enabledVendorIds.toSet(),
                    disabledVendorIds.toSet(),
                    enabledLegIntVendorIds.toSet(),
                    disabledLegIntVendorIds.toSet()))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserDisagreeToAll(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().setUserAgreeToAll())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserStatus(purposesConsentStatus: Boolean,
                      purposesLIStatus: Boolean,
                      vendorsConsentStatus: Boolean,
                      vendorsLIStatus: Boolean,
                      promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().setUserStatus(
                    purposesConsentStatus,
                    purposesLIStatus,
                    vendorsConsentStatus,
                    vendorsLIStatus))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserStatusSets(enabledConsentPurposeIds: ReadableMap,
                          disabledConsentPurposeIds: ReadableMap,
                          enabledLIPurposeIds: ReadableMap,
                          disabledLIPurposeIds: ReadableMap,
                          enabledConsentVendorIds: ReadableMap,
                          disabledConsentVendorIds: ReadableMap,
                          enabledLIVendorIds: ReadableMap,
                          disabledLIVendorIds: ReadableMap,
                          sendAPIEvent: Boolean?,
                          promise: Promise) {
        try {
            sendAPIEvent?.let {
                promise.resolve(Didomi.getInstance().setUserStatus(
                        enabledConsentPurposeIds.toSet(),
                        disabledConsentPurposeIds.toSet(),
                        enabledLIPurposeIds.toSet(),
                        disabledLIPurposeIds.toSet(),
                        enabledConsentVendorIds.toSet(),
                        disabledConsentVendorIds.toSet(),
                        enabledLIVendorIds.toSet(),
                        disabledLIVendorIds.toSet(),
                        it
                ))
            } ?: kotlin.run {
                promise.resolve(Didomi.getInstance().setUserStatus(
                        enabledConsentPurposeIds.toSet(),
                        disabledConsentPurposeIds.toSet(),
                        enabledLIPurposeIds.toSet(),
                        disabledLIPurposeIds.toSet(),
                        enabledConsentVendorIds.toSet(),
                        disabledConsentVendorIds.toSet(),
                        enabledLIVendorIds.toSet(),
                        disabledLIVendorIds.toSet()
                ))
            }
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

    private fun prepareEvent(eventName: String, params: String?) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
    }
}
