@file:Suppress("unused")
package io.didomi.reactnative

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
import io.didomi.sdk.DidomiMultiUserParameters
import io.didomi.sdk.DidomiUserParameters
import io.didomi.sdk.events.*
import io.didomi.sdk.exceptions.DidomiNotReadyException
import io.didomi.sdk.models.CurrentUserStatus
import io.didomi.sdk.user.model.UserAuthParams
import io.didomi.sdk.user.model.UserAuth
import io.didomi.sdk.user.model.UserAuthWithEncryptionParams
import io.didomi.sdk.user.model.UserAuthWithHashParams
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

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
        @Deprecated("Use syncReady instead", replaceWith = ReplaceWith("syncReady()"))
        override fun syncDone(event: SyncDoneEvent) = prepareEvent(EventTypes.SYNC_DONE.event, event.organizationUserId)

        /**
         * Sync error
         */
        override fun syncError(event: SyncErrorEvent) = prepareEvent(EventTypes.SYNC_ERROR.event, event.error)

        /**
         * Sync ready
         */
        override fun syncReady(event: SyncReadyEvent) = prepareSyncReadyEvent(event)

        /**
         * Language updated
         */
        override fun languageUpdated(event: LanguageUpdatedEvent) = prepareEvent(EventTypes.LANGUAGE_UPDATED.event, event.languageCode)

        /**
         * Language update failed
         */
        override fun languageUpdateFailed(event: LanguageUpdateFailedEvent) = prepareEvent(EventTypes.LANGUAGE_UPDATE_FAILED.event, event.reason)

        /*
         * Error while using an external SDK integration
         */
        override fun integrationError(event: IntegrationErrorEvent) = prepareIntegrationErrorEvent(event)
    }

    private val vendorStatusListeners: MutableSet<String> = mutableSetOf()
    private val syncAcknowledgedCallbacks: MutableMap<Int, () -> Boolean> = mutableMapOf()
    private var syncAcknowledgedCallbackIndex = 0

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
            getString(i)?.also { set.add(it) }
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

                is Boolean -> map.putBoolean(entry.key, value)
                is Int -> map.putInt(entry.key, value)
                is Double -> map.putDouble(entry.key, value)
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
        countryCode: String?,
        regionCode: String?,
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
                androidTvEnabled,
                countryCode,
                regionCode,
                false
            )

            val application = reactContext.currentActivity?.application ?: throw IllegalStateException("No activity present")
            Didomi.getInstance().initialize(application, parameters)

            promise.resolve(0)

        } catch (e: Exception) {
            Log.e("initialize", "Error while initializing the Didomi SDK", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun initializeWithParameters(
        userAgentName: String,
        userAgentVersion: String,
        jsonParameters: String,
        promise: Promise
    ) {
        try {
            Didomi.getInstance().addEventListener(eventListener)

            Didomi.getInstance().setUserAgent(userAgentName, userAgentVersion)

            val parameters = buildDidomiInitializeParameters(jsonParameters)

            val application = reactContext.currentActivity?.application ?: throw IllegalStateException("No activity present")
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
                Didomi.getInstance().setupUI(reactContext.currentActivity as? FragmentActivity)
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
    fun getVendor(vendorId: String, promise: Promise) {
        try {
            promise.resolve(objectToWritableMap(Didomi.getInstance().getVendor(vendorId)))
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getTotalVendorCount(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getTotalVendorCount())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getIabVendorCount(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getIabVendorCount())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun getNonIabVendorCount(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().getNonIabVendorCount())
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
    fun getCurrentUserStatus(promise: Promise) {
        try {
            promise.resolve(objectToWritableMap(Didomi.getInstance().currentUserStatus))
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
    fun getApplicableRegulation(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().applicableRegulation.value)
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
    fun shouldUserStatusBeCollected(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().shouldUserStatusBeCollected())
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
    fun isUserStatusPartial(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().isUserStatusPartial)
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
    fun setUser(organizationUserId: String, promise: Promise) {
        Didomi.getInstance().setUser(organizationUserId)
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserAndSetupUI(organizationUserId: String, promise: Promise) {
        Didomi.getInstance().setUser(organizationUserId, reactContext.currentActivity as? FragmentActivity)
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
            userAuthParams = UserAuthWithHashParams(
                organizationUserId,
                algorithm,
                secretId,
                digest,
                salt
            ),
            activity = reactContext.currentActivity as? FragmentActivity
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
            userAuthParams = UserAuthWithHashParams(
                organizationUserId,
                algorithm,
                secretId,
                digest,
                salt,
                expiration.toLong()
            ),
            activity = reactContext.currentActivity as? FragmentActivity
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
            userAuthParams = UserAuthWithEncryptionParams(
                organizationUserId,
                algorithm,
                secretId,
                initializationVector
            ),
            activity = reactContext.currentActivity as? FragmentActivity
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
            userAuthParams = UserAuthWithEncryptionParams(
                organizationUserId,
                algorithm,
                secretId,
                initializationVector,
                expiration.toLong()
            ),
            activity = reactContext.currentActivity as? FragmentActivity
        )
        promise.resolve(0)
    }

    @ReactMethod
    fun setUserWithAuthParams(
        jsonUserAuthParams: String,
        jsonSynchronizedUsers: String?,
        promise: Promise
    ) {
        try {
            val userAuthParams = buildUserAuthParams(JSONObject(jsonUserAuthParams))

            val synchronizedUsers = jsonSynchronizedUsers?.let {
                JSONArray(it).run {
                    List(length()) { i -> buildUserAuthParams(getJSONObject(i)) }
                }
            } ?: emptyList()

            Didomi.getInstance().setUser(
                userAuthParams = userAuthParams,
                synchronizedUsers = synchronizedUsers,
            )

            promise.resolve(0)
        } catch (e: JSONException) {
            Log.e("setUserWithAuthParams", "Error while parsing user with auth params", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserWithAuthParamsAndSetupUI(
        jsonUserAuthParams: String,
        jsonSynchronizedUsers: String?,
        promise: Promise
    ) {
        try {
            val userAuthParams = buildUserAuthParams(JSONObject(jsonUserAuthParams))

            val synchronizedUsers = jsonSynchronizedUsers?.let {
                JSONArray(it).run {
                    List(length()) { i -> buildUserAuthParams(getJSONObject(i)) }
                }
            } ?: emptyList()

            Didomi.getInstance().setUser(
                userAuthParams = userAuthParams,
                synchronizedUsers = synchronizedUsers,
                activity = reactContext.currentActivity as? FragmentActivity,
            )

            promise.resolve(0)
        } catch (e: JSONException) {
            Log.e("setUserWithAuthParams", "Error while parsing user with auth params", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserWithParameters(
        jsonUserParameters: String,
        promise: Promise
    ) {
        try {
            val userParameters = buildDidomiUserParameters(JSONObject(jsonUserParameters))
            Didomi.getInstance().setUser(userParameters)
            promise.resolve(0)
        } catch (e: JSONException) {
            Log.e("setUserWithParameters", "Error while parsing user with parameters", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setUserWithParametersAndSetupUI(
        jsonUserParameters: String,
        promise: Promise
    ) {
        try {
            val userParameters = buildDidomiUserParameters(JSONObject(jsonUserParameters), reactContext.currentActivity as? FragmentActivity)

            Didomi.getInstance().setUser(userParameters)
            promise.resolve(0)
        } catch (e: JSONException) {
            Log.e("setUserWithParameters", "Error while parsing user with parameters", e)
            promise.reject(e)
        }
    }

    @ReactMethod
    fun showNotice(promise: Promise) {
        try {
            runOnUiThread {
                Didomi.getInstance().showNotice(reactContext.currentActivity as? FragmentActivity)
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
                    Didomi.getInstance().showPreferences(reactContext.currentActivity as? FragmentActivity, view)
                } ?: Didomi.getInstance().showPreferences(reactContext.currentActivity as? FragmentActivity)
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
    fun setUserDisagreeToAll(promise: Promise) {
        try {
            promise.resolve(Didomi.getInstance().setUserDisagreeToAll())
        } catch (e: DidomiNotReadyException) {
            promise.reject(e)
        }
    }

    @ReactMethod
    fun setCurrentUserStatus(
        currentUserStatusAsString: String,
        promise: Promise
    ) {
        try {
            val currentUserStatus = gson.fromJson(currentUserStatusAsString, CurrentUserStatus::class.java)
            promise.resolve(
                Didomi.getInstance().setCurrentUserStatus(currentUserStatus)
            )
        } catch (e: Exception) {
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
            @Suppress("DEPRECATION")
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

    @ReactMethod
    fun listenToVendorStatus(vendorId: String) {
        if (!vendorStatusListeners.contains(vendorId)) {
            Didomi.getInstance().addVendorStatusListener(vendorId) { vendorStatus ->
                val statusAsMap = objectToWritableMap(vendorStatus)
                prepareEvent("${EventTypes.VENDOR_STATUS_CHANGE_PREFIX.event}$vendorId", statusAsMap)
            }
            vendorStatusListeners.add(vendorId)
        }
    }

    @ReactMethod
    fun stopListeningToVendorStatus(vendorId: String) {
        if (vendorStatusListeners.contains(vendorId)) {
            Didomi.getInstance().removeVendorStatusListener(vendorId)
            vendorStatusListeners.remove(vendorId)
        }
    }

    @ReactMethod
    fun commitCurrentUserStatusTransaction(
        enabledPurposes: ReadableArray,
        disabledPurposes: ReadableArray,
        enabledVendors: ReadableArray,
        disabledVendors: ReadableArray,
        promise: Promise
    ) {
        val transaction = Didomi.getInstance().openCurrentUserStatusTransaction()
        transaction.enablePurposes(*readableArrayToStringArray(enabledPurposes))
        transaction.disablePurposes(*readableArrayToStringArray(disabledPurposes))
        transaction.enableVendors(*readableArrayToStringArray(enabledVendors))
        transaction.disableVendors(*readableArrayToStringArray(disabledVendors))
        promise.resolve(transaction.commit())
    }

    @ReactMethod
    fun syncAcknowledged(callbackIndex: Int, promise: Promise) {
        val result = syncAcknowledgedCallbacks[callbackIndex]?.let { it() }
        syncAcknowledgedCallbacks.remove(callbackIndex)
        if (result != null) {
            promise.resolve(result)
        } else {
            promise.reject(java.lang.IllegalStateException("SyncAcknowledged: Native callback not found. The method can be called only once."))
        }
    }

    @ReactMethod
    fun removeSyncAcknowledgedCallback(callbackIndex: Int, promise: Promise) {
        syncAcknowledgedCallbacks.remove(callbackIndex)
        promise.resolve(0)
    }

    private fun prepareEvent(eventName: String, params: Any?) {
        Log.d("prepareEvent", "Sending $eventName")
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    private fun prepareSyncReadyEvent(event: SyncReadyEvent) {
        val eventName = EventTypes.SYNC_READY.event
        Log.d("prepareEvent", "Sending $eventName")
        val callbackIndex = syncAcknowledgedCallbackIndex++
        syncAcknowledgedCallbacks[callbackIndex] = event.syncAcknowledged
        val params = WritableNativeMap().apply {
            putString("organizationUserId", event.organizationUserId)
            putBoolean("statusApplied", event.statusApplied)
            putInt("syncAcknowledgedIndex", callbackIndex)
        }
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    private fun prepareIntegrationErrorEvent(event: IntegrationErrorEvent) {
        val eventName = EventTypes.INTEGRATION_ERROR_EVENT.event
        Log.d("prepareEvent", "Sending $eventName")
        val params = WritableNativeMap().apply {
            putString("integrationName", event.integrationName)
            putString("reason", event.reason)
        }
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    // Required to transform from array to variadic.
    private fun readableArrayToStringArray(readableArray: ReadableArray) = readableArray.toSet().toTypedArray()

    private fun buildDidomiInitializeParameters(jsonParameters: String): DidomiInitializeParameters {
        val jsonObject = JSONObject(jsonParameters)
        
        return DidomiInitializeParameters(
            apiKey = jsonObject.getString("apiKey"),
            localConfigurationPath = jsonObject.opt("localConfigurationPath")?.toString(),
            remoteConfigurationUrl = jsonObject.opt("remoteConfigurationUrl")?.toString(),
            providerId = jsonObject.opt("providerId")?.toString(),
            disableDidomiRemoteConfig = (jsonObject.opt("disableDidomiRemoteConfig") as? Boolean) ?: false,
            languageCode = jsonObject.opt("languageCode")?.toString(),
            noticeId = jsonObject.opt("noticeId")?.toString(),
            tvNoticeId = jsonObject.opt("androidTvNoticeId")?.toString(),
            androidTvEnabled = (jsonObject.opt("androidTvEnabled") as? Boolean) ?: false,
            countryCode = jsonObject.opt("countryCode")?.toString(),
            regionCode = jsonObject.opt("regionCode")?.toString(),
            isUnderage = (jsonObject.opt("isUnderage") as? Boolean) ?: false
        )
    }

    private fun buildDidomiUserParameters(
        jsonParameters: JSONObject,
        activity: FragmentActivity? = null
    ): DidomiUserParameters {
        val userAuth = if (jsonParameters.has("userAuth")) {
            buildUserAuthParams(jsonParameters.getJSONObject("userAuth"))
        } else null
        
        val dcsUserAuth = if (jsonParameters.has("dcsUserAuth")) {
            buildUserAuthParams(jsonParameters.getJSONObject("dcsUserAuth"))
        } else null

        val isUnderage = jsonParameters.opt("isUnderage") as? Boolean

        if (jsonParameters.has("synchronizedUsers")) {
            val synchronizedUsers = buildUserAuthParamsArray(jsonParameters.getJSONArray("synchronizedUsers"))
            return DidomiMultiUserParameters(
                userAuth = userAuth as UserAuth,
                dcsUserAuth = dcsUserAuth,
                synchronizedUsers = synchronizedUsers,
                activity = activity,
                isUnderage = isUnderage
            )
        } else {
            return DidomiUserParameters(
                userAuth = userAuth as UserAuth,
                dcsUserAuth = dcsUserAuth,
                activity = activity,
                isUnderage = isUnderage
            )
        }
    }

    private fun buildUserAuthParams(jsonParameters: JSONObject): UserAuthParams {

        val id = jsonParameters.getString("id")
        val algorithm = jsonParameters.getString("algorithm")
        val secretId = jsonParameters.getString("secretId")

        return if (jsonParameters.has("initializationVector")) {
            val expiration = if (jsonParameters.has("expiration")) jsonParameters.getLong("expiration") else null
            val initializationVector = jsonParameters.getString("initializationVector")
            UserAuthWithEncryptionParams(
                id = id,
                algorithm = algorithm,
                secretId = secretId,
                initializationVector = initializationVector,
                expiration = expiration
            )
        } else {
            val expiration = if (jsonParameters.has("expiration")) jsonParameters.getLong("expiration") else null
            val digest = jsonParameters.getString("digest")
            val salt = if (jsonParameters.has("salt")) jsonParameters.getString("salt") else null
            UserAuthWithHashParams(
                id = id,
                algorithm = algorithm,
                secretId = secretId,
                digest = digest,
                salt = salt,
                expiration = expiration
            )
        }
    }

    private fun buildUserAuthParamsArray(jsonArray: JSONArray): Array<UserAuthParams> {
        return Array(jsonArray.length()) { i -> buildUserAuthParams(jsonArray.getJSONObject(i)) }
    }
}
