package com.reactnativedidomi

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.*
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.didomi.sdk.Didomi
import io.didomi.sdk.events.*
import io.didomi.sdk.exceptions.DidomiNotReadyException


class DidomiModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  val gson = Gson()

  private val reactContext: ReactContext
  private val eventListener = object : EventListener() {

    val activatedEvents = mutableMapOf(
      Pair("on_ready", false),
      Pair("on_error", false),
      Pair("on_consent_changed", false),
      Pair("on_hide_notice", false),
      Pair("on_show_notice", false),
      Pair("on_notice_click_agree", false),
      Pair("on_notice_click_disagree", false),
      Pair("on_notice_click_more_info", false),
      Pair("on_notice_click_view_vendors", false),
      Pair("on_notice_click_privacy_policy", false),
      Pair("on_preferences_click_agree_to_all", false),
      Pair("on_preferences_click_disagree_to_all", false),
      Pair("on_preferences_click_purpose_agree", false),
      Pair("on_preferences_click_purpose_disagree", false),
      Pair("on_preferences_click_view_vendors", false),
      Pair("on_preferences_click_save_choices", false),
      Pair("on_preferences_click_vendor_agree", false),
      Pair("on_preferences_click_vendor_disagree", false),
      Pair("on_preferences_click_category_agree", false),
      Pair("on_preferences_click_category_disagree", false),
      Pair("on_preferences_click_agree_to_all_vendors", false),
      Pair("on_preferences_click_disagree_to_all_vendors", false),
      Pair("on_preferences_click_vendor_save_choices", false),
      Pair("on_preferences_click_view_purposes", false),
      Pair("on_preferences_click_agree_to_all_purposes", false),
      Pair("on_preferences_click_disagree_to_all_purposes", false),
      Pair("on_preferences_click_reset_all_purposes", false),
      Pair("on_sync_done", false)
    )

    /**
     * SDK has been successfully initialized
     */
    override fun ready(event: ReadyEvent?) {
      if (activatedEvents.getValue("on_ready"))
        prepareEvent("on_ready", null)
    }

    /**
     * An error occurred during the SDK initialization
     */
    override fun error(event: ErrorEvent?) {
      if (activatedEvents.getValue("on_error"))
        prepareEvent("on_error", objectToWritableMap(event))
    }

    /**
     * The consent status of the user has changed
     */
    override fun consentChanged(event: ConsentChangedEvent) {
      if (activatedEvents.getValue("on_consent_changed"))
        prepareEvent("on_consent_changed", objectToWritableMap(event))
    }

    /**
     * The notice is being hidden
     */
    override fun hideNotice(event: HideNoticeEvent) {
      if (activatedEvents.getValue("on_hide_notice"))
        prepareEvent("on_hide_notice", objectToWritableMap(event))
    }

    /**
     * The notice is being shown
     */
    override fun showNotice(event: ShowNoticeEvent) {
      if (activatedEvents.getValue("on_show_notice"))
        prepareEvent("on_show_notice", objectToWritableMap(event))
    }

    /**
     * Click on agree on notice
     */
    override fun noticeClickAgree(event: NoticeClickAgreeEvent) {
      if (activatedEvents.getValue("on_notice_click_agree"))
        prepareEvent("on_notice_click_agree", objectToWritableMap(event))
    }

    /**
     * Click on disagree on the notice
     */
    override fun noticeClickDisagree(event: NoticeClickDisagreeEvent) {
      if (activatedEvents.getValue("on_notice_click_disagree"))
        prepareEvent("on_notice_click_disagree", objectToWritableMap(event))
    }

    /**
     * Click on learn more on notice
     */
    override fun noticeClickMoreInfo(event: NoticeClickMoreInfoEvent) {
      if (activatedEvents.getValue("on_notice_click_more_info"))
        prepareEvent("on_notice_click_more_info", objectToWritableMap(event))
    }

    /**
     * Click on partners on the notice
     */
    override fun noticeClickViewVendors(event: NoticeClickViewVendorsEvent) {
      if (activatedEvents.getValue("on_notice_click_view_vendors"))
        prepareEvent("on_notice_click_view_vendors", objectToWritableMap(event))
    }

    /**
     * Click on privacy policy on the notice
     */
    override fun noticeClickPrivacyPolicy(event: NoticeClickPrivacyPolicyEvent) {
      if (activatedEvents.getValue("on_notice_click_privacy_policy"))
        prepareEvent("on_notice_click_privacy_policy", objectToWritableMap(event))
    }

    /**
     * Click on agree to all on preferences popup
     */
    override fun preferencesClickAgreeToAll(event: PreferencesClickAgreeToAllEvent) {
      if (activatedEvents.getValue("on_preferences_click_agree_to_all"))
        prepareEvent("on_preferences_click_agree_to_all", objectToWritableMap(event))
    }

    /**
     * Click on disagree to all on preferences popup
     */
    override fun preferencesClickDisagreeToAll(event: PreferencesClickDisagreeToAllEvent) {
      if (activatedEvents.getValue("on_preferences_click_disagree_to_all"))
        prepareEvent("on_preferences_click_disagree_to_all", objectToWritableMap(event))
    }

    /**
     * Click on agree to a purpose on preferences popup
     */
    override fun preferencesClickPurposeAgree(event: PreferencesClickPurposeAgreeEvent) {
      if (activatedEvents.getValue("on_preferences_click_purpose_agree"))
        prepareEvent("on_preferences_click_purpose_agree", objectToWritableMap(event))
    }

    /**
     * Click on disagree to a purpose on preferences popup
     */
    override fun preferencesClickPurposeDisagree(event: PreferencesClickPurposeDisagreeEvent) {
      if (activatedEvents.getValue("on_preferences_click_purpose_disagree"))
        prepareEvent("on_preferences_click_purpose_disagree", objectToWritableMap(event))
    }

    /**
     * Click view vendors on purposes view on preferences popup
     */
    override fun preferencesClickViewVendors(event: PreferencesClickViewVendorsEvent) {
      if (activatedEvents.getValue("on_preferences_click_view_vendors"))
        prepareEvent("on_preferences_click_view_vendors", objectToWritableMap(event))
    }

    /**
     * Click on save on the purposes view on preferences popup
     */
    override fun preferencesClickSaveChoices(event: PreferencesClickSaveChoicesEvent) {
      if (activatedEvents.getValue("on_preferences_click_save_choices"))
        prepareEvent("on_preferences_click_save_choices", objectToWritableMap(event))
    }

    /**
     * Click on agree to a vendor on preferences popup
     */
    override fun preferencesClickVendorAgree(event: PreferencesClickVendorAgreeEvent) {
      if (activatedEvents.getValue("on_preferences_click_vendor_agree"))
        prepareEvent("on_preferences_click_vendor_agree", objectToWritableMap(event))
    }

    /**
     * Click on disagree to a vendor on preferences popup
     */
    override fun preferencesClickVendorDisagree(event: PreferencesClickVendorDisagreeEvent) {
      if (activatedEvents.getValue("on_preferences_click_vendor_disagree"))
        prepareEvent("on_preferences_click_vendor_disagree", objectToWritableMap(event))
    }

    /**
     * Click on agree to a category on preferences popup
     */
    override fun preferencesClickCategoryAgree(event: PreferencesClickCategoryAgreeEvent?) {
      if (activatedEvents.getValue("on_preferences_click_category_agree"))
        prepareEvent("on_preferences_click_category_agree", objectToWritableMap(event))
    }

    /**
     * Click on disagree to a category on preferences popup
     */
    override fun preferencesClickCategoryDisagree(event: PreferencesClickCategoryDisagreeEvent?) {
      if (activatedEvents.getValue("on_preferences_click_category_disagree"))
        prepareEvent("on_preferences_click_category_disagree", objectToWritableMap(event))
    }

    /**
     * Click on agree to all vendors on preferences popup
     */
    override fun preferencesClickAgreeToAllVendors(event: PreferencesClickAgreeToAllVendorsEvent) {
      if (activatedEvents.getValue("on_preferences_click_agree_to_all_vendors"))
        prepareEvent("on_preferences_click_agree_to_all_vendors", objectToWritableMap(event))
    }

    /**
     * Click on disagree to all vendors on preferences popup
     */
    override fun preferencesClickDisagreeToAllVendors(event: PreferencesClickDisagreeToAllVendorsEvent) {
      if (activatedEvents.getValue("on_preferences_click_disagree_to_all_vendors"))
        prepareEvent("on_preferences_click_disagree_to_all_vendors", objectToWritableMap(event))
    }

    /**
     * Click on save on the vendors view on preferences popup
     */
    override fun preferencesClickVendorSaveChoices(event: PreferencesClickVendorSaveChoicesEvent) {
      if (activatedEvents.getValue("on_preferences_click_vendor_save_choices"))
        prepareEvent("on_preferences_click_vendor_save_choices", objectToWritableMap(event))
    }

    /**
     * Click on view purposes on the preferences popup
     */
    override fun preferencesClickViewPurposes(event: PreferencesClickViewPurposesEvent) {
      if (activatedEvents.getValue("on_preferences_click_view_purposes"))
        prepareEvent("on_preferences_click_view_purposes", objectToWritableMap(event))
    }

    /**
     * Flip ON all purposes switch on the preferences popup
     */
    override fun preferencesClickAgreeToAllPurposes(event: PreferencesClickAgreeToAllPurposesEvent) {
      if (activatedEvents.getValue("on_preferences_click_agree_to_all_purposes"))
        prepareEvent("on_preferences_click_agree_to_all_purposes", objectToWritableMap(event))
    }

    /**
     * Flip OFF all purposes switch on the preferences popup
     */
    override fun preferencesClickDisagreeToAllPurposes(event: PreferencesClickDisagreeToAllPurposesEvent) {
      if (activatedEvents.getValue("on_preferences_click_disagree_to_all_purposes"))
        prepareEvent("on_preferences_click_disagree_to_all_purposes", objectToWritableMap(event))
    }

    /**
     * Click on reset purposes on the preferences popup
     */
    override fun preferencesClickResetAllPurposes(event: PreferencesClickResetAllPurposesEvent?) {
      if (activatedEvents.getValue("on_preferences_click_reset_all_purposes"))
        prepareEvent("on_preferences_click_reset_all_purposes", objectToWritableMap(event))
    }

    override fun syncDone(event: SyncDoneEvent) {
      if (activatedEvents.getValue("on_sync_done"))
        prepareEvent("on_sync_done", objectToWritableMap(event))
    }

//      override fun preferencesClickAgreeToAllVendors(event: PreferencesClickAgreeToAllVendorsEvent) {
//        // Flip ON all vendors switch on the preferences popup
//      }
//
//      override fun preferencesClickDisagreeToAllVendors(event: PreferencesClickDisagreeToAllVendorsEvent) {
//        // Flip OFF all vendors switch on the preferences popup
//      }
  }

  init {
    this.reactContext = reactContext
  }

  override fun getName() = "Didomi"

  override fun getConstants(): MutableMap<String, Any> {
    return hashMapOf("count" to 1)
  }

  //convert a data class to a map
  private fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
  }

  //convert a map to a data class
  inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
  }

  fun ReadableMap.toSet(): Set<String> {
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

      Didomi.getInstance().onReady {
        Log.d("App", "----------------OK------------------")
        promise.resolve(0)
      }
      Didomi.getInstance().onError {
        Log.d("App", "----------------ERROR------------------")
        promise.reject("-2", "error")
      }
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
  fun addEventListener(eventKey: String, promise: Promise) {
    eventListener.activatedEvents[eventKey] = true
    promise.resolve(0)
  }

  @ReactMethod
  fun removeEventListener(eventKey: String, promise: Promise) {
    eventListener.activatedEvents[eventKey] = false
    promise.resolve(0)
  }

  @ReactMethod
  fun getDisabledPurposes(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().disabledPurposes))
  }

  @ReactMethod
  fun getDisabledPurposeIds(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().disabledPurposeIds))
  }

  @ReactMethod
  fun getDisabledVendors(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().disabledVendors))
  }

  @ReactMethod
  fun getDisabledVendorIds(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().disabledVendorIds))
  }

  @ReactMethod
  fun getEnabledPurposes(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().enabledPurposes))
  }

  @ReactMethod
  fun getEnabledPurposeIds(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().enabledPurposeIds))
  }

  @ReactMethod
  fun getEnabledVendors(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().enabledVendors))
  }

  @ReactMethod
  fun getEnabledVendorIds(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().enabledVendorIds))
  }

  @ReactMethod
  fun getJavaScriptForWebView(promise: Promise) {
    promise.resolve(Didomi.getInstance().javaScriptForWebView)
  }

  @ReactMethod
  fun getQueryStringForWebView(promise: Promise) {
    promise.resolve(Didomi.getInstance().queryStringForWebView)
  }

  @ReactMethod
  fun getPurpose(purposeId: String, promise: Promise) {
    promise.resolve(objectToWritableMap(Didomi.getInstance().getPurpose(purposeId)))
  }

  @ReactMethod
  fun getRequiredPurposes(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().requiredPurposes))
  }

  @ReactMethod
  fun getRequiredPurposeIds(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().requiredPurposeIds))
  }

  @ReactMethod
  fun getRequiredVendors(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().requiredVendors))
  }

  @ReactMethod
  fun getRequiredVendorIds(promise: Promise) {
    promise.resolve(setToWritableArray(Didomi.getInstance().requiredVendorIds))
  }

  @ReactMethod
  fun getText(textKey: String, promise: Promise) {
    val map = Didomi.getInstance().getText(textKey)
    val writableMap = WritableNativeMap()

    for (elem in map) writableMap.putString(elem.key, elem.value)

    promise.resolve(writableMap)
  }

  @ReactMethod
  fun getTranslatedText(key: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getTranslatedText(key))
  }

  @ReactMethod
  fun getUserConsentStatusForPurpose(purposeId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserConsentStatusForPurpose(purposeId))
  }

  @ReactMethod
  fun getUserConsentStatusForVendor(vendorId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserConsentStatusForVendor(vendorId))
  }

  @ReactMethod
  fun getUserConsentStatusForVendorAndRequiredPurposes(vendorId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserConsentStatusForVendorAndRequiredPurposes(vendorId))
  }

  @ReactMethod
  fun getUserLegitimateInterestStatusForPurpose(purposeId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForPurpose(purposeId))
  }

  @ReactMethod
  fun getUserLegitimateInterestForVendor(vendorId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForVendor(vendorId))
  }

  @ReactMethod
  fun getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId))
  }

  @ReactMethod
  fun getUserStatusForVendor(vendorId: String, promise: Promise) {
    promise.resolve(Didomi.getInstance().getUserStatusForVendor(vendorId))
  }

  @ReactMethod
  fun getVendor(vendorId: String, promise: Promise) {
    promise.resolve(objectToWritableMap(Didomi.getInstance().getVendor(vendorId)))
  }

  @ReactMethod
  fun hideNotice(promise: Promise) {
    Didomi.getInstance().hideNotice()
    promise.resolve(0)
  }

  @ReactMethod
  fun hidePreferences(promise: Promise) {
    Didomi.getInstance().hidePreferences()
    promise.resolve(0)
  }

  @ReactMethod
  fun isConsentRequired(promise: Promise) {
    promise.resolve(Didomi.getInstance().isConsentRequired)
  }

  @ReactMethod
  fun isUserConsentStatusPartial(promise: Promise) {
    promise.resolve(Didomi.getInstance().isUserConsentStatusPartial)
  }

  @ReactMethod
  fun isNoticeVisible(promise: Promise) {
    promise.resolve(Didomi.getInstance().isNoticeVisible)
  }

  @ReactMethod
  fun isPreferencesVisible(promise: Promise) {
    promise.resolve(Didomi.getInstance().isPreferencesVisible)
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
  fun onError(promise: Promise) {
    eventListener.activatedEvents["on_error"] = true
    promise.resolve(0)
  }

  @ReactMethod
  fun onReady(promise: Promise) {
    eventListener.activatedEvents["on_ready"] = true
    promise.resolve(0)
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
        runOnUiThread {
          Didomi.getInstance().showNotice(currentActivity as FragmentActivity)
        }
        promise.resolve(0)
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
        view?.let {
          Didomi.getInstance().showPreferences(currentActivity as FragmentActivity, view)
        } ?: kotlin.run {
          Didomi.getInstance().showPreferences(currentActivity as FragmentActivity)
        }
        promise.resolve(0)
      } else
        promise.reject("2", "The current activity must be a FragmentActivity")
    } ?: kotlin.run {
      promise.reject("1", "The current activity is NULL")
    }
  }

  @ReactMethod
  fun reset(promise: Promise) {
    Didomi.getInstance().reset()
    promise.resolve(0)
  }

  @ReactMethod
  fun setUserAgreeToAll(promise: Promise) {
    promise.resolve(Didomi.getInstance().setUserAgreeToAll())
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
    promise.resolve(Didomi.getInstance().setUserConsentStatus(
      enabledPurposeIds.toSet(),
      disabledPurposeIds.toSet(),
      enabledLegitimatePurposeIds.toSet(),
      disabledLegitimatePurposeIds.toSet(),
      enabledVendorIds.toSet(),
      disabledVendorIds.toSet(),
      enabledLegIntVendorIds.toSet(),
      disabledLegIntVendorIds.toSet()))
  }

  @ReactMethod
  fun setUserDisagreeToAll(promise: Promise) {
    promise.resolve(Didomi.getInstance().setUserAgreeToAll())
  }

  @ReactMethod
  fun setUserStatus(purposesConsentStatus: Boolean,
                    purposesLIStatus: Boolean,
                    vendorsConsentStatus: Boolean,
                    vendorsLIStatus: Boolean,
                    promise: Promise) {
    promise.resolve(Didomi.getInstance().setUserStatus(
      purposesConsentStatus,
      purposesLIStatus,
      vendorsConsentStatus,
      vendorsLIStatus))
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
  }

  @ReactMethod
  fun shouldConsentBeCollected(promise: Promise) {
    promise.resolve(Didomi.getInstance().shouldConsentBeCollected())
  }

  @ReactMethod
  fun updateSelectedLanguage(languageCode: String, promise: Promise) {
    Didomi.getInstance().updateSelectedLanguage(languageCode)
    promise.resolve(0)
  }

  private fun sendEvent(eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }

  private fun prepareEvent(eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
