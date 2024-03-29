import Didomi
import AdSupport
import AppTrackingTransparency

@objc(Didomi)
class RNDidomi: RCTEventEmitter {
    
    var didomiEventListener: EventListener
    var vendorStatusListeners: Set<String> = Set()

    override init() {
        didomiEventListener = EventListener()
        super.init()
        initEventListener()
    }

    override static func requiresMainQueueSetup() -> Bool {
        return true
    }

    @objc(initialize:userAgentVersion:apiKey:localConfigurationPath:remoteConfigurationURL:providerId:disableDidomiRemoteConfig:languageCode:noticeId:androidTvNoticeId:androidTvEnabled:resolve:reject:)
    func initialize(userAgentName: String, userAgentVersion: String, apiKey: String, localConfigurationPath: String?, remoteConfigurationURL: String?, providerId: String?, disableDidomiRemoteConfig: Bool = false, languageCode: String? = nil, noticeId: String? = nil, androidTvNoticeId: String? = nil, androidTvEnabled: Bool = false, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.setUserAgent(name: userAgentName, version: userAgentVersion)
        
        Didomi.shared.initialize(DidomiInitializeParameters(
            apiKey: apiKey,
            localConfigurationPath: localConfigurationPath,
            remoteConfigurationURL: remoteConfigurationURL,
            providerID: providerId,
            disableDidomiRemoteConfig: disableDidomiRemoteConfig,
            languageCode: languageCode ?? Locale.current.languageCode ?? "",
            noticeID: noticeId
        ))

        resolve(0)
    }

    @objc(onReady:reject:)
    func onReady(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.onReady {
            self.dispatchEvent(withName: "on_ready_callback", body: nil)
        }
        resolve(0)
    }

    @objc(onError:reject:)
    func onError(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.onError { error in
            self.dispatchEvent(withName: "on_error_callback", body: error.descriptionText)
        }
        resolve(0)
    }

    @objc(getQueryStringForWebView:reject:)
    func getQueryStringForWebView(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getQueryStringForWebView())
    }
    
    @objc(setUserConsentStatus:disabledPurposeIds:enabledVendorIds:disabledVendorIds:resolve:reject:)
    func setUserConsentStatus(enabledPurposeIds: Set<String>, disabledPurposeIds: Set<String>, enabledVendorIds: Set<String>, disabledVendorIds: Set<String>, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserConsentStatus(enabledPurposeIds: Set(enabledPurposeIds), disabledPurposeIds: Set(disabledPurposeIds), enabledVendorIds: Set(enabledVendorIds), disabledVendorIds: Set(disabledVendorIds)))
    }
    
    @objc(isConsentRequired:reject:)
    func isConsentRequired(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isConsentRequired())
    }

    @objc(shouldUserStatusBeCollected:reject:)
    func shouldUserStatusBeCollected(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.shouldUserStatusBeCollected())
    }
    
    @objc(isUserConsentStatusPartial:reject:)
    func isUserConsentStatusPartial(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isUserConsentStatusPartial())
    }

    @objc(isUserStatusPartial:reject:)
    func isUserStatusPartial(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isUserStatusPartial())
    }
    
    @objc(isUserLegitimateInterestStatusPartial:reject:)
    func isUserLegitimateInterestStatusPartial(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isUserLegitimateInterestStatusPartial())
    }
    
    @objc(getCurrentUserStatus:reject:)
    func getCurrentUserStatus(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let currentUserStatus = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getCurrentUserStatus())) as? [String: Any]
        resolve(currentUserStatus)
    }
    
    @objc(getUserStatus:reject:)
    func getUserStatus(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let userStatus = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getUserStatus())) as? [String: Any]
        resolve(userStatus)
    }
    
    @objc(setCurrentUserStatus:resolve:reject:)
    func setCurrentUserStatus(currentUserStatusAsString: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        guard let jsonData = currentUserStatusAsString.data(using: .utf8) else {
            print("Failed to convert JSON string to Data")
            resolve(false)
            return
        }
        do {
            let currentUserStatus = try JSONDecoder().decode(CurrentUserStatus.self, from: jsonData)
            resolve(Didomi.shared.setCurrentUserStatus(currentUserStatus: currentUserStatus))
        } catch {
            print("Error decoding JSON: \(error.localizedDescription)")
            resolve(false)
        }
    }
    
    @objc(setUserStatus:purposesLIStatus:vendorsConsentStatus:vendorsLIStatus:resolve:reject:)
    func setUserStatus(purposesConsentStatus: Bool, purposesLIStatus: Bool, vendorsConsentStatus: Bool, vendorsLIStatus: Bool, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserStatus(purposesConsentStatus: purposesConsentStatus, purposesLIStatus: purposesLIStatus, vendorsConsentStatus: vendorsConsentStatus, vendorsLIStatus: vendorsLIStatus))
    }
    
    @objc(setUserStatusSets:disabledConsentPurposeIds:enabledLIPurposeIds:disabledLIPurposeIds:enabledConsentVendorIds:disabledConsentVendorIds:enabledLIVendorIds:disabledLIVendorIds:resolve:reject:)
    func setUserStatusSets(enabledConsentPurposeIds: Set<String>, disabledConsentPurposeIds: Set<String>, enabledLIPurposeIds: Set<String>, disabledLIPurposeIds: Set<String>, enabledConsentVendorIds: Set<String>, disabledConsentVendorIds: Set<String>, enabledLIVendorIds: Set<String>, disabledLIVendorIds: Set<String>, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserStatus(enabledConsentPurposeIds: Set(enabledConsentPurposeIds), disabledConsentPurposeIds: Set(disabledConsentPurposeIds), enabledLIPurposeIds: Set(enabledLIPurposeIds), disabledLIPurposeIds: Set(disabledLIPurposeIds), enabledConsentVendorIds: Set(enabledConsentVendorIds), disabledConsentVendorIds: Set(disabledConsentVendorIds), enabledLIVendorIds: Set(enabledLIVendorIds), disabledLIVendorIds: Set(disabledLIVendorIds)))
    }
    
    @objc(setUserAgreeToAll:reject:)
    func setUserAgreeToAll(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserAgreeToAll())
    }

    @objc(setUserDisagreeToAll:reject:)
    func setUserDisagreeToAll(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserDisagreeToAll())
    }

    @objc(reset:reject:)
    func reset(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.reset()
        resolve(0)
    }

    @objc(getRequiredPurposeIds:reject:)
    func getRequiredPurposeIds(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getRequiredPurposeIds()))
    }

    @objc(getRequiredVendorIds:reject:)
    func getRequiredVendorIds(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getRequiredVendorIds()))
    }

    @objc(isReady:reject:)
    func isReady(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isReady())
    }

    @objc(getRequiredPurposes:reject:)
    func getRequiredPurposes(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getRequiredPurposes())) as? [[String: Any]]
        resolve(purposes)
    }

    @objc(getRequiredVendors:reject:)
    func getRequiredVendors(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getRequiredVendors())) as? [[String: Any]]
        resolve(vendors)
    }
    
    @objc(getPurpose:resolve:reject:)
    func getPurpose(purposeId: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getPurpose(purposeId: purposeId))) as? [String: Any]
        resolve(purposes)
    }
    
    @objc(getVendor:resolve:reject:)
    func getVendor(vendorId: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getVendor(vendorId: vendorId))) as? [String: Any]
        resolve(vendors)
    }
    
    @objc(getJavaScriptForWebView:resolve:reject:)
    func getJavaScriptForWebView(_ extra: String? = "", resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        if let extra = extra {
            resolve(Didomi.shared.getJavaScriptForWebView(extra: extra))
        } else {
            resolve(Didomi.shared.getJavaScriptForWebView())
        }
    }

    @objc(updateSelectedLanguage:resolve:reject:)
    func updateSelectedLanguage(languageCode: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.updateSelectedLanguage(languageCode: languageCode)
        resolve(0)
    }
    
    // MARK: Didomi extension

    @objc(setupUI:reject:)
    dynamic func setupUI(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {

        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                Didomi.shared.setupUI(containerController: containerController)
            }
        }
        resolve(0)
    }

    @objc(forceShowNotice:reject:)
    dynamic func forceShowNotice(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            Didomi.shared.forceShowNotice()
        }
        resolve(0)
    }

    @objc(showNotice:reject:)
    dynamic func showNotice(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            Didomi.shared.showNotice()
        }
        resolve(0)
    }

    @objc(hideNotice:reject:)
    dynamic func hideNotice(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            Didomi.shared.hideNotice()
        }
        resolve(0)
    }
    
    @objc(isNoticeVisible:reject:)
    dynamic func isNoticeVisible(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isNoticeVisible())
    }
    
    @objc(shouldConsentBeCollected:reject:)
    dynamic func shouldConsentBeCollected(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.shouldConsentBeCollected())
    }

    @objc(showPreferences:resolve:reject:)
    dynamic func showPreferences(view: String?, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        if let containerController = RCTPresentedViewController() {
            DispatchQueue.main.async {
                if let view = view {
                    var preferencesView: Didomi.Views
                    switch view.lowercased() {
                    case "sensitive-personal-information":
                        preferencesView = .sensitivePersonalInformation
                    case "vendors":
                        preferencesView = .vendors
                    default:
                        preferencesView = .purposes
                    }
                    Didomi.shared.showPreferences(controller: containerController, view: preferencesView)
                } else {
                    Didomi.shared.showPreferences(controller: containerController)
                }
            }
        }
        
        resolve(0)
    }

    @objc(hidePreferences:reject:)
    dynamic func hidePreferences(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            Didomi.shared.hidePreferences()
        }
        resolve(0)
    }
    
    @objc(isPreferencesVisible:reject:)
    dynamic func isPreferencesVisible(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isPreferencesVisible())
    }
    
    @objc(getTranslatedText:resolve:reject:)
    dynamic func getTranslatedText(key: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getTranslatedText(key: key))
    }
    
    @objc(getText:resolve:reject:)
    dynamic func getText(key: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let text = Didomi.shared.getText(key: key)
        resolve(text)
    }

    @objc(setLogLevel:resolve:reject:)
    dynamic func setLogLevel(minLevel: UInt8, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        var level: UInt8
        
        switch minLevel {
        case 0:
            level = 1
        case 1:
            level = 2
        case 2:
            level = 17
        case 3:
            level = 16
        default:
            level = 17
        }
        
        Didomi.shared.setLogLevel(minLevel: level)
        resolve(0)
    }

    @objc(clearUser:reject:)
    func clearUser(resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.clearUser()
        resolve(0)
    }

    @objc(setUser:resolve:reject:)
    dynamic func setUser(id: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.setUser(id: id)
        resolve(0)
    }

    @objc(setUserAndSetupUI:resolve:reject:)
    dynamic func setUserAndSetupUI(id: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                Didomi.shared.setUser(id: id, containerController: containerController)
            } else {
                Didomi.shared.setUser(id: id)
            }
        }
        resolve(0)
    }

    @objc(setUserWithHashAuth:algorithm:secretId:digest:salt:resolve:reject:)
    dynamic func setUserWithHashAuth(id: String, algorithm: String, secretId: String, digest: String, salt: String?, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.setUser(
            userAuthParams: UserAuthWithHashParams(
                id: id,
                algorithm: algorithm,
                secretID: secretId,
                digest: digest,
                salt: salt))
        resolve(0)
    }

    @objc(setUserWithHashAuthAndSetupUI:algorithm:secretId:digest:salt:resolve:reject:)
    dynamic func setUserWithHashAuthAndSetupUI(id: String, algorithm: String, secretId: String, digest: String, salt: String?, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let userAuthParams = UserAuthWithHashParams(
            id: id,
            algorithm: algorithm,
            secretID: secretId,
            digest: digest,
            salt: salt
        )
        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                    Didomi.shared.setUser(
                        userAuthParams: userAuthParams,
                        containerController: containerController
                    )
            } else {
                Didomi.shared.setUser(userAuthParams: userAuthParams)
            }
        }
        resolve(0)
    }

    @objc(setUserWithHashAuthWithExpiration:algorithm:secretId:digest:salt:expiration:resolve:reject:)
    dynamic func setUserWithHashAuthWithExpiration(id: String, algorithm: String, secretId: String, digest: String, salt: String?, expiration: Double, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.setUser(
            userAuthParams: UserAuthWithHashParams(
                id: id,
                algorithm: algorithm,
                secretID: secretId,
                digest: digest,
                salt: salt,
                legacyExpiration: expiration))
        resolve(0)
    }

    @objc(setUserWithHashAuthWithExpirationAndSetupUI:algorithm:secretId:digest:salt:expiration:resolve:reject:)
    dynamic func setUserWithHashAuthWithExpirationAndSetupUI(id: String, algorithm: String, secretId: String, digest: String, salt: String?, expiration: Double, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let userAuthParams = UserAuthWithHashParams(
            id: id,
            algorithm: algorithm,
            secretID: secretId,
            digest: digest,
            salt: salt,
            legacyExpiration: expiration
        )
        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                    Didomi.shared.setUser(
                        userAuthParams: userAuthParams,
                        containerController: containerController
                    )
            } else {
                Didomi.shared.setUser(userAuthParams: userAuthParams)
            }
        }
        resolve(0)
    }

    @objc(setUserWithEncryptionAuth:algorithm:secretId:initializationVector:resolve:reject:)
    dynamic func setUserWithEncryptionAuth(id: String, algorithm: String, secretId: String, initializationVector: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.setUser(
            userAuthParams: UserAuthWithEncryptionParams(
                id: id,
                algorithm: algorithm,
                secretID: secretId,
                initializationVector: initializationVector))
        resolve(0)
    }

    @objc(setUserWithEncryptionAuthAndSetupUI:algorithm:secretId:initializationVector:resolve:reject:)
    dynamic func setUserWithEncryptionAuthAndSetupUI(id: String, algorithm: String, secretId: String, initializationVector: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let userAuthParams = UserAuthWithEncryptionParams(
            id: id,
            algorithm: algorithm,
            secretID: secretId,
            initializationVector: initializationVector
        )
        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                    Didomi.shared.setUser(
                        userAuthParams: userAuthParams,
                        containerController: containerController
                    )
            } else {
                Didomi.shared.setUser(userAuthParams: userAuthParams)
            }
        }
        resolve(0)
    }

    @objc(setUserWithEncryptionAuthWithExpiration:algorithm:secretId:initializationVector:expiration:resolve:reject:)
    dynamic func setUserWithEncryptionAuthWithExpiration(id: String, algorithm: String, secretId: String, initializationVector: String, expiration: Double, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        Didomi.shared.setUser(
            userAuthParams: UserAuthWithEncryptionParams(
                id: id,
                algorithm: algorithm,
                secretID: secretId,
                initializationVector: initializationVector,
                legacyExpiration: expiration))
        resolve(0)
    }

    @objc(setUserWithEncryptionAuthWithExpirationAndSetupUI:algorithm:secretId:initializationVector:expiration:resolve:reject:)
    dynamic func setUserWithEncryptionAuthWithExpirationAndSetupUI(id: String, algorithm: String, secretId: String, initializationVector: String, expiration: Double, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        let userAuthParams = UserAuthWithEncryptionParams(
            id: id,
            algorithm: algorithm,
            secretID: secretId,
            initializationVector: initializationVector,
            legacyExpiration: expiration
        )
        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                    Didomi.shared.setUser(
                        userAuthParams: userAuthParams,
                        containerController: containerController
                    )
            } else {
                Didomi.shared.setUser(userAuthParams: userAuthParams)
            }
        }
        resolve(0)
    }
    
    @objc(listenToVendorStatus:resolve:reject:)
    dynamic func listenToVendorStatus(vendorId: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        if (!vendorStatusListeners.contains(vendorId)) {
            vendorStatusListeners.insert(vendorId)
            Didomi.shared.addVendorStatusListener(id: vendorId) { vendorStatus in
                let encoder = JSONEncoder()
                let statusJson = try? JSONSerialization.jsonObject(with: encoder.encode(vendorStatus)) as? [String: Any]
                self.dispatchEvent(withName: "on_vendor_status_change_\(vendorId)", body: statusJson)
            }
        }
        resolve(0)
    }

    @objc(stopListeningToVendorStatus:resolve:reject:)
    dynamic func stopListeningToVendorStatus(vendorId: String, resolve:RCTPromiseResolveBlock, reject:RCTPromiseRejectBlock) {
        if (vendorStatusListeners.contains(vendorId)) {
            Didomi.shared.removeVendorStatusListener(id: vendorId)
            vendorStatusListeners.remove(vendorId)
        }
        resolve(0)
    }
    
    @objc(commitCurrentUserStatusTransaction:disabledPurposes:enabledVendors:disabledVendors:resolve:reject:)
    dynamic func commitCurrentUserStatusTransaction(
       enabledPurposes: [String],
       disabledPurposes: [String],
       enabledVendors: [String],
       disabledVendors: [String],
       resolve:RCTPromiseResolveBlock,
       reject:RCTPromiseRejectBlock
    ) {
        let transaction = Didomi.shared.openCurrentUserStatusTransaction()
        transaction.enablePurposes(enabledPurposes)
        transaction.disablePurposes(disabledPurposes)
        transaction.enableVendors(enabledVendors)
        transaction.disableVendors(disabledVendors)
        resolve(transaction.commit())
   }
}

// MARK: Didomi Specific structs

@objc public enum ConsentStatus : Int {
    
    case enable
    
    case disable
    
    case unknown
    
    /// The raw type that can be used to represent all values of the conforming
    /// type.
    ///
    /// Every distinct value of the conforming type has a corresponding unique
    /// value of the `RawValue` type, but there may be values of the `RawValue`
    /// type that don't have a corresponding value of the conforming type.
    public typealias RawValue = Int
}

extension RNDidomi {
    
    @objc(supportedEvents)
    override func supportedEvents() -> [String]! {
        var eventsSet = Set([
                // Consent
                "on_consent_changed",
                // SDK lifecycle events
                "on_error",
                "on_error_callback",
                "on_ready",
                "on_ready_callback",
                // Notice
                "on_hide_notice",
                "on_show_notice",
                "on_notice_click_agree",
                "on_notice_click_disagree",
                "on_notice_click_view_vendors",
                "on_notice_click_view_spi_purposes",
                "on_notice_click_more_info",
                "on_notice_click_privacy_policy",
                // Preferences
                "on_hide_preferences",
                "on_show_preferences",
                // Preferences - Views
                "on_preferences_click_view_purposes",
                "on_preferences_click_view_vendors",
                "on_preferences_click_view_spi_purposes",
                // Preferences - Purpose
                "on_preferences_click_agree_to_all",
                "on_preferences_click_disagree_to_all",
                "on_preferences_click_agree_to_all_purposes",
                "on_preferences_click_disagree_to_all_purposes",
                "on_preferences_click_reset_all_purposes",
                "on_preferences_click_purpose_agree",
                "on_preferences_click_purpose_disagree",
                "on_preferences_click_category_agree",
                "on_preferences_click_category_disagree",
                "on_preferences_click_save_choices",
                // Preferences - Vendor
                "on_preferences_click_agree_to_all_vendors",
                "on_preferences_click_disagree_to_all_vendors",
                "on_preferences_click_vendor_agree",
                "on_preferences_click_vendor_disagree",
                "on_preferences_click_vendor_save_choices",
                // Preferences - Sensitive Personal Information
                "on_preferences_click_spi_purpose_agree",
                "on_preferences_click_spi_purpose_disagree",
                "on_preferences_click_spi_category_agree",
                "on_preferences_click_spi_category_disagree",
                "on_preferences_click_spi_purpose_save_choices",
                // Sync
                "on_sync_done",
                "on_sync_error",
                // Language
                "on_language_updated",
                "on_language_update_failed"
        ])
        vendorStatusListeners.forEach {
            eventsSet.insert("on_vendor_status_change_\($0)")
        }
        return Array(eventsSet)
    }

    private func initEventListener() {
    
        didomiEventListener.onConsentChanged = { event in
            self.dispatchEvent(withName: "on_consent_changed", body: "")
        }
        
        didomiEventListener.onError = { event in
            self.dispatchEvent(withName: "on_error", body: event.descriptionText)
        }
        
        didomiEventListener.onReady = { event in
            self.dispatchEvent(withName: "on_ready", body: "")
        }
        
        didomiEventListener.onHideNotice = { event in
            self.dispatchEvent(withName: "on_hide_notice", body: "")
        }
        
        didomiEventListener.onShowNotice = { event in
            self.dispatchEvent(withName: "on_show_notice", body: "")
        }
        
        didomiEventListener.onNoticeClickAgree = { event in
            self.dispatchEvent(withName: "on_notice_click_agree", body: "")
        }
        
        didomiEventListener.onNoticeClickDisagree = { event in
            self.dispatchEvent(withName: "on_notice_click_disagree", body: "")
        }

        didomiEventListener.onNoticeClickViewVendors = { event in
            self.dispatchEvent(withName: "on_notice_click_view_vendors", body: "")
        }

        didomiEventListener.onNoticeClickViewSPIPurposes = { event in
            self.dispatchEvent(withName: "on_notice_click_view_spi_purposes", body: "")
        }
        
        didomiEventListener.onNoticeClickMoreInfo = { event in
            self.dispatchEvent(withName: "on_notice_click_more_info", body: "")
        }

        didomiEventListener.onNoticeClickPrivacyPolicy = { event in
            self.dispatchEvent(withName: "on_notice_click_privacy_policy", body: "")
        }
        
        didomiEventListener.onHidePreferences = { event in
            self.dispatchEvent(withName: "on_hide_preferences", body: "")
        }
        
        didomiEventListener.onShowPreferences = { event in
            self.dispatchEvent(withName: "on_show_preferences", body: "")
        }
        
        didomiEventListener.onPreferencesClickViewPurposes = { event in
            self.dispatchEvent(withName: "on_preferences_click_view_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickViewVendors = { event in
            self.dispatchEvent(withName: "on_preferences_click_view_vendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickViewSPIPurposes = { event in
            self.dispatchEvent(withName: "on_preferences_click_view_spi_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAll = { event in
            self.dispatchEvent(withName: "on_preferences_click_agree_to_all", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAll = { event in
            self.dispatchEvent(withName: "on_preferences_click_disagree_to_all", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAllPurposes = { event in
            self.dispatchEvent(withName: "on_preferences_click_agree_to_all_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAllPurposes = { event in
            self.dispatchEvent(withName: "on_preferences_click_disagree_to_all_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickResetAllPurposes = { event in
            self.dispatchEvent(withName: "on_preferences_click_reset_all_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickPurposeAgree = { event, purposeId in
            self.dispatchEvent(withName: "on_preferences_click_purpose_agree", body: purposeId)
        }
        
        didomiEventListener.onPreferencesClickPurposeDisagree = { event, purposeId in
            self.dispatchEvent(withName: "on_preferences_click_purpose_disagree", body: purposeId)
        }
        
        didomiEventListener.onPreferencesClickCategoryAgree = { event, categoryId in
            self.dispatchEvent(withName: "on_preferences_click_category_agree", body: categoryId)
        }
        
        didomiEventListener.onPreferencesClickCategoryDisagree = { event, categoryId in
            self.dispatchEvent(withName: "on_preferences_click_category_disagree", body: categoryId)
        }
        
        didomiEventListener.onPreferencesClickSaveChoices = { event in
            self.dispatchEvent(withName: "on_preferences_click_save_choices", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAllVendors = { event in
            self.dispatchEvent(withName: "on_preferences_click_agree_to_all_vendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAllVendors = { event in
            self.dispatchEvent(withName: "on_preferences_click_disagree_to_all_vendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickVendorAgree = { event, vendorId in
            self.dispatchEvent(withName: "on_preferences_click_vendor_agree", body: vendorId)
        }
        
        didomiEventListener.onPreferencesClickVendorDisagree = { event, vendorId in
            self.dispatchEvent(withName: "on_preferences_click_vendor_disagree", body: vendorId)
        }
        
        didomiEventListener.onPreferencesClickVendorSaveChoices = { event in
            self.dispatchEvent(withName: "on_preferences_click_vendor_save_choices", body: "")
        }
        
        didomiEventListener.onPreferencesClickSPIPurposeAgree = { event, purposeId in
            self.dispatchEvent(withName: "on_preferences_click_spi_purpose_agree", body: purposeId)
        }
        
        didomiEventListener.onPreferencesClickSPIPurposeDisagree = { event, purposeId in
            self.dispatchEvent(withName: "on_preferences_click_spi_purpose_disagree", body: purposeId)
        }
        
        didomiEventListener.onPreferencesClickSPICategoryAgree = { event, categoryId in
            self.dispatchEvent(withName: "on_preferences_click_spi_category_agree", body: categoryId)
        }
        
        didomiEventListener.onPreferencesClickSPICategoryDisagree = { event, categoryId in
            self.dispatchEvent(withName: "on_preferences_click_spi_category_disagree", body: categoryId)
        }
        
        didomiEventListener.onPreferencesClickSPIPurposeSaveChoices = { event in
            self.dispatchEvent(withName: "on_preferences_click_spi_purpose_save_choices", body: "")
        }
        
        didomiEventListener.onSyncDone = { event, organizationUserId in
            self.dispatchEvent(withName: "on_sync_done", body: organizationUserId)
        }

        didomiEventListener.onSyncError = { event, error in
            self.dispatchEvent(withName: "on_sync_error", body: error)
        }
        
        didomiEventListener.onLanguageUpdated = { event, languageCode in
            self.dispatchEvent(withName: "on_language_updated", body: languageCode)
        }

        didomiEventListener.onLanguageUpdateFailed = { event, reason in
            self.dispatchEvent(withName: "on_language_update_failed", body: reason)
        }

        Didomi.shared.addEventListener(listener: didomiEventListener)
    }

    /// Sends the specified event only if the react-native bridge is still valid
    private func dispatchEvent(withName: String, body: Any?) {
        if self.bridge != nil {
            self.sendEvent(withName: withName, body: body)
        } else {
            // Event emitter is not valid anymore, remove event listener
            Didomi.shared.removeEventListener(listener: didomiEventListener)
        }
    }
}

extension Int {
    var consentStatusBool: Bool? {
        if self == 2 {
            return nil
        } else {
            return self == 0
        }
    }
}
