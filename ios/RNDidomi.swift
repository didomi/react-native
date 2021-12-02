import Didomi
import AdSupport
import AppTrackingTransparency

@objc(Didomi)
class RNDidomi: RCTEventEmitter {
    
    var initialized = false
    let didomiEventListener = EventListener()
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    @objc(initialize:localConfigurationPath:remoteConfigurationURL:providerId:disableDidomiRemoteConfig:languageCode:noticeId:)
    func initialize(apiKey: String, localConfigurationPath: String?, remoteConfigurationURL: String?, providerId: String?, disableDidomiRemoteConfig: Bool = false, languageCode: String? = nil, noticeId: String? = nil) {
        onReady()
        onError()
        if !initialized {
            initEventListener()
            let newLanguageCode = Locale.current.languageCode ?? ""
            Didomi.shared.initialize(apiKey: apiKey, localConfigurationPath: localConfigurationPath, remoteConfigurationURL: remoteConfigurationURL, providerId: providerId, disableDidomiRemoteConfig: disableDidomiRemoteConfig, languageCode: languageCode != nil ? languageCode : newLanguageCode, noticeId: noticeId)
        }
        initialized = true
    }
    
    private func onReady() {
        Didomi.shared.onReady {
            self.sendEvent(withName: "on_ready_callback", body: nil)
        }
    }
    
    private func onError() {
        Didomi.shared.onError { error in
            self.sendEvent(withName: "on_error_callback", body: error.descriptionText)
        }
    }
    
    @objc(getQueryStringForWebView:reject:)
    func getQueryStringForWebView(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getQueryStringForWebView())
    }
    
    @objc(setUserAgent:version:)
    func setUserAgent(name: String, version: String) {
        Didomi.shared.setUserAgent(name: name, version: version)
    }
    
    @objc(setUserConsentStatus:disabledPurposeIds:enabledVendorIds:disabledVendorIds:resolve:reject:)
    func setUserConsentStatus(enabledPurposeIds: Set<String>, disabledPurposeIds: Set<String>, enabledVendorIds: Set<String>, disabledVendorIds: Set<String>, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserConsentStatus(enabledPurposeIds: Set(enabledPurposeIds), disabledPurposeIds: Set(disabledPurposeIds), enabledVendorIds: Set(enabledVendorIds), disabledVendorIds: Set(disabledVendorIds)))
    }
    
    @objc(isConsentRequired:reject:)
    func isConsentRequired(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isConsentRequired())
    }
    
    @objc(isUserConsentStatusPartial:reject:)
    func isUserConsentStatusPartial(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isUserConsentStatusPartial())
    }
    
    @objc(isUserLegitimateInterestStatusPartial:reject:)
    func isUserLegitimateInterestStatusPartial(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isUserLegitimateInterestStatusPartial())
    }
    
    @objc(getUserConsentStatusForPurpose:resolve:reject:)
    func getUserConsentStatusForPurpose(purposeId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let consentStatus = Didomi.shared.getUserConsentStatusForPurpose(purposeId: purposeId)
        resolve(consentStatus.rawValue.consentStatusBool)
    }
    
    @objc(getUserLegitimateInterestStatusForPurpose:resolve:reject:)
    func getUserLegitimateInterestStatusForPurpose(purposeId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let consentStatus = Didomi.shared.getUserLegitimateInterestStatusForPurpose(purposeId: purposeId)
        resolve(consentStatus.rawValue.consentStatusBool)
    }
    
    @objc(getUserStatus:reject:)
    func getUserStatus(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let userStatus = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getUserStatus())) as? [String: Any]
        resolve(userStatus)
    }
    
    @objc(getUserStatusForVendor:resolve:reject:)
    func getUserStatusForVendor(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let userStatus = Didomi.shared.getUserStatusForVendor(vendorId: vendorId)
        resolve(userStatus.rawValue.consentStatusBool)
    }
    
    @objc(getUserConsentStatusForVendor:resolve:reject:)
    func getUserConsentStatusForVendor(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let consentStatus = Didomi.shared.getUserConsentStatusForVendor(vendorId: vendorId)
        resolve(consentStatus.rawValue.consentStatusBool)
    }
    
    @objc(getUserLegitimateInterestStatusForVendor:resolve:reject:)
    func getUserLegitimateInterestStatusForPurpose(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let consentStatus = Didomi.shared.getUserLegitimateInterestStatusForVendor(vendorId: vendorId)
        resolve(consentStatus.rawValue.consentStatusBool)
    }
    
    @objc(getUserConsentStatusForVendorAndRequiredPurposes:resolve:reject:)
    func getUserConsentStatusForVendorAndRequiredPurposes(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let consentStatus = Didomi.shared.getUserConsentStatusForVendorAndRequiredPurposes(vendorId: vendorId)
        resolve(consentStatus.rawValue.consentStatusBool)
    }
    
    @objc(getUserLegitimateInterestStatusForVendorAndRequiredPurposes:resolve:reject:)
    func getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let consentStatus = Didomi.shared.getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId: vendorId)
        resolve(consentStatus.rawValue.consentStatusBool)
    }
    
    @objc(setUserStatus:purposesLIStatus:vendorsConsentStatus:vendorsLIStatus:resolve:reject:)
    func setUserStatus(purposesConsentStatus: Bool, purposesLIStatus: Bool, vendorsConsentStatus: Bool, vendorsLIStatus: Bool, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserStatus(purposesConsentStatus: purposesConsentStatus, purposesLIStatus: purposesLIStatus, vendorsConsentStatus: vendorsConsentStatus, vendorsLIStatus: vendorsLIStatus))
    }
    
    @objc(setUserStatusSets:disabledConsentPurposeIds:enabledLIPurposeIds:disabledLIPurposeIds:enabledConsentVendorIds:disabledConsentVendorIds:enabledLIVendorIds:disabledLIVendorIds:resolve:reject:)
    func setUserStatusSets(enabledConsentPurposeIds: Set<String>, disabledConsentPurposeIds: Set<String>, enabledLIPurposeIds: Set<String>, disabledLIPurposeIds: Set<String>, enabledConsentVendorIds: Set<String>, disabledConsentVendorIds: Set<String>, enabledLIVendorIds: Set<String>, disabledLIVendorIds: Set<String>, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserStatus(enabledConsentPurposeIds: Set(enabledConsentPurposeIds), disabledConsentPurposeIds: Set(disabledConsentPurposeIds), enabledLIPurposeIds: Set(enabledLIPurposeIds), disabledLIPurposeIds: Set(disabledLIPurposeIds), enabledConsentVendorIds: Set(enabledConsentVendorIds), disabledConsentVendorIds: Set(disabledConsentVendorIds), enabledLIVendorIds: Set(enabledLIVendorIds), disabledLIVendorIds: Set(disabledLIVendorIds)))
    }
    
    @objc(setUserAgreeToAll:reject:)
    func setUserAgreeToAll(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserAgreeToAll())
    }
    
    @objc(setUserDisagreeToAll:reject:)
    func setUserDisagreeToAll(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserDisagreeToAll())
    }
    
    @objc(reset)
    func reset() {
        Didomi.shared.reset()
    }
    
    @objc(getRequiredPurposeIds:reject:)
    func getRequiredPurposeIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getRequiredPurposeIds()))
    }
    
    @objc(getRequiredVendorIds:reject:)
    func getRequiredVendorIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getRequiredVendorIds()))
    }
    
    
    @objc(isReady:reject:)
    func isReady(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isReady())
    }
    
    @objc(getRequiredPurposes:reject:)
    func getRequiredPurposes(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getRequiredPurposes())) as? [[String: Any]]
        resolve(purposes)
    }
    
    
    
    @objc(getRequiredVendors:reject:)
    func getRequiredVendors(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getRequiredVendors())) as? [[String: Any]]
        resolve(vendors)
    }
    
    @objc(getEnabledPurposes:reject:)
    func getEnabledPurposes(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getEnabledPurposes())) as? [[String: Any]]
        resolve(purposes)
    }
    
    
    @objc(getEnabledPurposeIds:reject:)
    func getEnabledPurposeIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getEnabledPurposeIds()))
    }
    
    @objc(getDisabledPurposes:reject:)
    func getDisabledPurposes(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getDisabledPurposes())) as? [[String: Any]]
        resolve(purposes)
    }
    
    @objc(getDisabledPurposeIds:reject:)
    func getDisabledPurposeIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getDisabledPurposeIds()))
    }
    
    @objc(getEnabledVendors:reject:)
    func getEnabledVendors(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getEnabledVendors())) as? [[String: Any]]
        resolve(vendors)
    }
    
    @objc(getEnabledVendorIds:reject:)
    func getEnabledVendorIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getEnabledVendorIds()))
    }
    
    @objc(getDisabledVendors:reject:)
    func getDisabledVendors(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getDisabledVendors())) as? [[String: Any]]
        resolve(vendors)
    }
    
    @objc(getDisabledVendorIds:reject:)
    func getDisabledVendorIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Array(Didomi.shared.getDisabledVendorIds()))
    }
    
    @objc(getPurpose:resolve:reject:)
    func getPurpose(purposeId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getPurpose(purposeId: purposeId))) as? [String: Any]
        resolve(purposes)
    }
    
    @objc(getVendor:resolve:reject:)
    func getVendor(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let encoder = JSONEncoder()
        let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getVendor(vendorId: vendorId))) as? [String: Any]
        resolve(vendors)
    }
    
    @objc(getJavaScriptForWebView:resolve:reject:)
    func getJavaScriptForWebView(_ extra: String? = "", resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        if let extra = extra {
            resolve(Didomi.shared.getJavaScriptForWebView(extra: extra))
        } else {
            resolve(Didomi.shared.getJavaScriptForWebView())
        }
    }
    
    @objc(updateSelectedLanguage:)
    func updateSelectedLanguage(languageCode: String) {
        Didomi.shared.updateSelectedLanguage(languageCode: languageCode)
    }
    
    
    // MARK: ViewProviderDelegate
    
    @objc(getNoticeViewController:resolve:reject:)
    dynamic func getNoticeViewController(position: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getNoticeViewController(position: position))
    }
    
    @objc(getPreferencesViewController:reject:)
    dynamic func getPreferencesViewController(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getPreferencesViewController())
    }
    
    
    // MARK: Didomi extension
    
    @objc(setupUI)
    dynamic func setupUI() {
        
        DispatchQueue.main.async {
            if let containerController = RCTPresentedViewController() {
                Didomi.shared.setupUI(containerController: containerController)
            }
        }
    }
    
    @objc(forceShowNotice)
    dynamic func forceShowNotice() {
        DispatchQueue.main.async {
            Didomi.shared.forceShowNotice()
        }
    }
    
    @objc(showNotice)
    dynamic func showNotice() {
        DispatchQueue.main.async {
            Didomi.shared.showNotice()
        }
    }
    
    @objc(hideNotice)
    dynamic func hideNotice() {
        DispatchQueue.main.async {
            Didomi.shared.hideNotice()
        }
    }
    
    @objc(isNoticeVisible:reject:)
    dynamic func isNoticeVisible(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isNoticeVisible())
    }
    
    @objc(shouldConsentBeCollected:reject:)
    dynamic func shouldConsentBeCollected(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.shouldConsentBeCollected())
    }
    
    @objc(showPreferences:)
    dynamic func showPreferences(view: String?) {
        if let containerController = RCTPresentedViewController(){
            if view?.lowercased() == "vendors"{
                DispatchQueue.main.async {
                    Didomi.shared.showPreferences(controller: containerController, view: .vendors)
                }
            }
            else {
                DispatchQueue.main.async {
                    Didomi.shared.showPreferences(controller: containerController)
                }
            }
        }
    }
    
    @objc(hidePreferences)
    dynamic func hidePreferences() {
        DispatchQueue.main.async {
            Didomi.shared.hidePreferences()
        }
    }
    
    @objc(isPreferencesVisible:reject:)
    dynamic func isPreferencesVisible(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isPreferencesVisible())
    }
    
    @objc(getTranslatedText:resolve:reject:)
    dynamic func getTranslatedText(key: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getTranslatedText(key: key))
    }
    
    @objc(getText:resolve:reject:)
    dynamic func getText(key: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        let text = Didomi.shared.getText(key: key)
        resolve(text)
    }
    
    @objc(setLogLevel:)
    dynamic func setLogLevel(minLevel: UInt8) {
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
    }
    
    @objc(setUser:)
    dynamic func setUser(id: String) {
        Didomi.shared.setUser(id: id)
    }
    
    @objc(setUser:algorithm:secretId:salt:digest:)
    dynamic func setUser(id: String, algorithm: String, secretId: String, salt: String?, digest: String) {
        Didomi.shared.setUser(id: id, algorithm: algorithm, secretId: secretId, salt: salt, digest: digest)
    }
    
    @objc public enum Views : Int, RawRepresentable {
        
        case purposes = 0
        
        case vendors
    }
    
}

// MARK: Didomi Specific structs

/// Struct used to represent a Purpose.
public struct Purpose : Codable {
    
    /// ID of the purpose.
    public var id: String?
    
    /// IAB ID that the purpose should be mapped to (if the purpose is a custom purpose should be treated as an IAB purpose).
    public var iabId: String?
    
    /// Name of the purpose.
    public var name: String?
    
    /// Description of the purpose.
    public var description: String?
    
    /// Legal description of the purpose.
    public var descriptionLegal: String?
}

public struct Vendor : Codable {
    
    /// Unique ID of the vendor.
    public var id: String?
    
    /// Name of the vendor.
    public var name: String?
    
    /// URL to the privacy policy of the vendor.
    public var privacyPolicyUrl: String?
    
    /// Namespace of the vendor (IAB, didomi or custom).
    public var namespace: String?
    
    /// Namespaces of the vendor (IAB, didomi or custom) and their corresponding IDs.
    public var namespaces: [String : String]?
    
    /// Purpose IDs that the vendor is operating under the consent legal basis.
    public var purposeIds: Set<String>?
    
    /// Purpose IDs that the vendor is operating under the legitimate interest legal basis.
    public var legIntPurposeIds: Set<String>?
    
    /// IAB ID that the vendor should be mapped to (if vendor namespace is not **iab** and the vendor should be treated as an IAB vendor).
    public var iabId: String?
}

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
        return ["on_ready_callback",
                "on_error_callback",
                "on_consent_changed",
                "on_ready",
                "on_error",
                "on_hide_notice",
                "on_show_notice",
                "on_notice_click_agree",
                "on_notice_click_disagree",
                "on_notice_click_more_info",
                "on_notice_click_view_vendors",
                "on_notice_click_privacy_policy",
                "on_hide_preferences",
                "on_show_preferences",
                "on_preferences_click_agree_to_all",
                "on_preferences_click_disagree_to_all",
                "on_preferences_click_agree_to_all_purposes",
                "on_preferences_click_disagree_to_all_purposes",
                "on_preferences_click_reset_all_purposes",
                "on_preferences_click_agree_to_all_vendors",
                "on_preferences_click_disagree_to_all_vendors",
                "on_preferences_click_purpose_agree",
                "on_preferences_click_purpose_disagree",
                "on_preferences_click_category_agree",
                "on_preferences_click_category_disagree",
                "on_preferences_click_view_vendors",
                "on_preferences_click_view_purposes",
                "on_preferences_click_save_choices",
                "on_preferences_click_vendor_agree",
                "on_preferences_click_vendor_disagree",
                "on_preferences_click_vendor_save_choices",
                "on_sync_done"
        ]
    }
    
    
    private func initEventListener(){
        
        didomiEventListener.onConsentChanged = { event in
            self.sendEvent(withName: "on_consent_changed", body:"")
        }
        
        didomiEventListener.onReady = { event in
            self.sendEvent(withName: "on_ready", body: "")
        }
        
        didomiEventListener.onError = { event in
            self.sendEvent(withName: "on_error", body: "")
        }
        
        didomiEventListener.onShowNotice = { event in
            self.sendEvent(withName: "on_show_notice", body: "")
        }
        
        didomiEventListener.onHideNotice = { event in
            self.sendEvent(withName: "on_hide_notice", body: "")
        }
        
        didomiEventListener.onNoticeClickAgree = { event in
            self.sendEvent(withName: "on_notice_click_agree", body: "")
        }
        
        didomiEventListener.onNoticeClickDisagree = { event in
            self.sendEvent(withName: "on_notice_click_disagree", body: "")
        }
        
        didomiEventListener.onNoticeClickMoreInfo = { event in
            self.sendEvent(withName: "on_notice_click_more_info", body: "")
        }
        
        didomiEventListener.onNoticeClickViewVendors = { event in
            self.sendEvent(withName: "on_notice_click_view_vendors", body: "")
        }
        
        didomiEventListener.onNoticeClickPrivacyPolicy = { event in
            self.sendEvent(withName: "on_notice_click_privacy_policy", body: "")
        }
        
        didomiEventListener.onShowPreferences = { event in
            self.sendEvent(withName: "on_show_preferences", body: "")
        }
        
        didomiEventListener.onHidePreferences = { event in
            self.sendEvent(withName: "on_hide_preferences", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAll = { event in
            self.sendEvent(withName: "on_preferences_click_agree_to_all", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAll = { event in
            self.sendEvent(withName: "on_preferences_click_disagree_to_all", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAllPurposes = { event in
            self.sendEvent(withName: "on_preferences_click_agree_to_all_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAllPurposes = { event in
            self.sendEvent(withName: "on_preferences_click_disagree_to_all_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickResetAllPurposes = { event in
            self.sendEvent(withName: "on_preferences_click_reset_all_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAllVendors = { event in
            self.sendEvent(withName: "on_preferences_click_agree_to_all_vendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAllVendors = { event in
            self.sendEvent(withName: "on_preferences_click_disagree_to_all_vendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickPurposeAgree = { event, purposeId in
            self.sendEvent(withName: "on_preferences_click_purpose_agree", body: purposeId)
        }
        
        didomiEventListener.onPreferencesClickPurposeDisagree = { event, purposeId in
            self.sendEvent(withName: "on_preferences_click_purpose_disagree", body: purposeId)
        }
        
        didomiEventListener.onPreferencesClickCategoryAgree = { event, categoryId in
            self.sendEvent(withName: "on_preferences_click_category_agree", body: categoryId)
        }
        
        didomiEventListener.onPreferencesClickCategoryDisagree = { event, categoryId in
            self.sendEvent(withName: "on_preferences_click_category_disagree", body: categoryId)
        }
        
        didomiEventListener.onPreferencesClickViewVendors = { event in
            self.sendEvent(withName: "on_preferences_click_view_vendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickViewPurposes = { event in
            self.sendEvent(withName: "on_preferences_click_view_purposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickSaveChoices = { event in
            self.sendEvent(withName: "on_preferences_click_save_choices", body: "")
        }
        
        didomiEventListener.onPreferencesClickVendorAgree = { event, vendorId in
            self.sendEvent(withName: "on_preferences_click_vendor_agree", body: vendorId)
        }
        
        didomiEventListener.onPreferencesClickVendorDisagree = { event, vendorId in
            self.sendEvent(withName: "on_preferences_click_vendor_disagree", body: vendorId)
        }
        
        didomiEventListener.onPreferencesClickVendorSaveChoices = { event in
            self.sendEvent(withName: "on_preferences_click_vendor_save_choices", body: "")
        }
        
        didomiEventListener.onSyncDone = { event, organizationUserId in
            self.sendEvent(withName: "on_sync_done", body: organizationUserId)
        }
        
        Didomi.shared.addEventListener(listener: didomiEventListener)
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
