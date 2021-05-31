import Didomi

@objc(Didomi)
class RNDidomi: NSObject {
    
    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(a*b)
    }
    
    @objc(initialize:localConfigurationPath:remoteConfigurationURL:providerId:disableDidomiRemoteConfig:languageCode:)
    func initialize(apiKey: String, localConfigurationPath: String?, remoteConfigurationURL: String?, providerId: String?, disableDidomiRemoteConfig: Bool = true, languageCode: String? = nil) {
        Didomi.shared.initialize(apiKey: apiKey, localConfigurationPath: localConfigurationPath, remoteConfigurationURL: remoteConfigurationURL, providerId: providerId, disableDidomiRemoteConfig: disableDidomiRemoteConfig, languageCode: languageCode)
    }
    
    //    @objc(initialize:localConfigurationPath:remoteConfigurationURL:providerId:disableDidomiRemoteConfig:languageCode:noticeId:)
    //    func initialize(apiKey: String, localConfigurationPath: String?, remoteConfigurationURL: String?, providerId: String?, disableDidomiRemoteConfig: Bool = true, languageCode: String? = nil, noticeId: String? = nil) {
    //        Didomi.shared.initialize(apiKey: apiKey, localConfigurationPath: localConfigurationPath, remoteConfigurationURL: remoteConfigurationURL, providerId: providerId, disableDidomiRemoteConfig: disableDidomiRemoteConfig, languageCode: languageCode, noticeId: noticeId)
    //    }
    
    @objc(setUserAgent:version:)
    func setUserAgent(name: String, version: String) {
        Didomi.shared.setUserAgent(name: name, version: version)
    }
    
    @objc(setUserConsentStatus:disabledPurposeIds:enabledVendorIds:disabledVendorIds:resolve:reject:)
    func setUserConsentStatus(enabledPurposeIds: Set<String>, disabledPurposeIds: Set<String>, enabledVendorIds: Set<String>, disabledVendorIds: Set<String>, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserConsentStatus(enabledPurposeIds: enabledPurposeIds, disabledPurposeIds: disabledPurposeIds, enabledVendorIds: enabledVendorIds, disabledVendorIds: disabledVendorIds))
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
        resolve(Didomi.shared.getUserConsentStatusForPurpose(purposeId: purposeId))
    }
    
    @objc(getUserConsentStatusForVendor:resolve:reject:)
    func getUserConsentStatusForVendor(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getUserConsentStatusForVendor(vendorId: vendorId))
    }
    
    @objc(getUserConsentStatusForVendorAndRequiredPurposes:resolve:reject:)
    func getUserConsentStatusForVendorAndRequiredPurposes(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getUserConsentStatusForVendorAndRequiredPurposes(vendorId: vendorId))
    }
    
    @objc(setUserStatus:purposesLIStatus:vendorsConsentStatus:vendorsLIStatus:resolve:reject:)
    func setUserStatus(purposesConsentStatus: Bool, purposesLIStatus: Bool, vendorsConsentStatus: Bool, vendorsLIStatus: Bool, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserStatus(purposesConsentStatus: purposesConsentStatus, purposesLIStatus: purposesLIStatus, vendorsConsentStatus: vendorsConsentStatus, vendorsLIStatus: vendorsLIStatus))
    }
    
    @objc(setUserStatus:disabledConsentPurposeIds:enabledLIPurposeIds:disabledLIPurposeIds:enabledConsentVendorIds:disabledConsentVendorIds:enabledLIVendorIds:disabledLIVendorIds:sendAPIEvent:resolve:reject:)
    func setUserStatus(enabledConsentPurposeIds: Set<String>, disabledConsentPurposeIds: Set<String>, enabledLIPurposeIds: Set<String>, disabledLIPurposeIds: Set<String>, enabledConsentVendorIds: Set<String>, disabledConsentVendorIds: Set<String>, enabledLIVendorIds: Set<String>, disabledLIVendorIds: Set<String>, sendAPIEvent: Bool, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserStatus(enabledConsentPurposeIds: enabledConsentPurposeIds, disabledConsentPurposeIds: disabledConsentPurposeIds, enabledLIPurposeIds: enabledLIPurposeIds, disabledLIPurposeIds: disabledLIPurposeIds, enabledConsentVendorIds: enabledConsentVendorIds, disabledConsentVendorIds: disabledConsentVendorIds, enabledLIVendorIds: enabledLIVendorIds, disabledLIVendorIds: disabledLIVendorIds, sendAPIEvent: sendAPIEvent))
    }
    
    @objc(setUserAgreeToAll:reject:)
    func setUserAgreeToAll(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserAgreeToAll())
    }
    
    @objc(setUserDisagreeToAll:reject:)
    func setUserDisagreeToAll(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.setUserDisagreeToAll())
    }
    
    //    @objc(onReady:)
    //    func onReady(callback: @escaping RCTResponseSenderBlock) {
    //        Didomi.shared.onReady(callback: callback)
    //    }
    
    //    @objc(onError:)
    //    func onError(callback: @escaping (Didomi.DidomiErrorEvent)) {
    //        Didomi.shared.onError(callback: callback)
    //    }
    
    @objc(reset)
    func reset() {
        Didomi.shared.reset()
    }
    
    @objc(getRequiredPurposeIds:reject:)
    func getRequiredPurposeIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getRequiredPurposeIds())
    }
    
    @objc(getRequiredVendorIds:reject:)
    func getRequiredVendorIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getRequiredVendorIds())
    }
    
    @objc(addEventListener)
    func addEventListener() {
        let listener = createListener()
        
        Didomi.shared.addEventListener(listener: listener)
    }
    
    @objc(isReady:reject:)
    func isReady(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isReady())
    }
    
    @objc(getRequiredPurposes:reject:)
    func getRequiredPurposes(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getRequiredPurposes())) as? [[String: Any]] ?? []
            resolve(purposes)
        }
        catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    
    
    @objc(getRequiredVendors:reject:)
    func getRequiredVendors(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getRequiredVendors())) as? [[String: Any]] ?? []
            resolve(vendors)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    @objc(getEnabledPurposes:reject:)
    func getEnabledPurposes(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getEnabledPurposes())) as? [[String: Any]] ?? []
            resolve(purposes)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    
    @objc(getEnabledPurposeIds:reject:)
    func getEnabledPurposeIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getEnabledVendorIds())
    }
    
    @objc(getDisabledPurposes:reject:)
    func getDisabledPurposes(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getDisabledPurposes())) as? [[String: Any]] ?? []
            resolve(purposes)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    @objc(getDisabledPurposeIds:reject:)
    func getDisabledPurposeIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getDisabledPurposeIds())
    }
    
    @objc(getEnabledVendors:reject:)
    func getEnabledVendors(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getEnabledVendors())) as? [[String: Any]] ?? []
            resolve(vendors)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    @objc(getEnabledVendorIds:reject:)
    func getEnabledVendorIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getEnabledVendorIds())
    }
    
    @objc(getDisabledVendors:reject:)
    func getDisabledVendors(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getDisabledVendors())) as? [[String: Any]] ?? []
            resolve(vendors)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    @objc(getDisabledVendorIds:reject:)
    func getDisabledVendorIds(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getEnabledVendorIds())
    }
    
    @objc(getPurpose:resolve:reject:)
    func getPurpose(purposeId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let purposes = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getPurpose(purposeId: purposeId))) as? [[String: Any]] ?? []
            resolve(purposes)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    @objc(getVendor:resolve:reject:)
    func getVendor(vendorId: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        do {
            let encoder = JSONEncoder()
            let vendors = try? JSONSerialization.jsonObject(with: encoder.encode(Didomi.shared.getVendor(vendorId: vendorId))) as? [[String: Any]] ?? []
            resolve(vendors)
        } catch {
            reject("error",error.localizedDescription,error)
        }
    }
    
    @objc(getJavaScriptForWebView:resolve:reject:)
    func getJavaScriptForWebView(extra: String = "", resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getJavaScriptForWebView(extra: extra))
    }
    
    @objc(updateSelectedLanguage:)
    func updateSelectedLanguage(languageCode: String) {
        Didomi.shared.updateSelectedLanguage(languageCode: languageCode)
    }
    
    
    // MARK: ViewProviderDelegate
    // TODO: check if needed
    @objc(getNoticeViewController:resolve:reject:)
    dynamic func getNoticeViewController(position: String, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getNoticeViewController(position: position))
    }
    
    @objc(getPreferencesViewController:reject:)
    dynamic func getPreferencesViewController(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.getPreferencesViewController())
    }
    
    
    // MARK: Didomi extension
    
    @objc(setupUI:)
    dynamic func setupUI(containerController: UIViewController) {
        Didomi.shared.setupUI(containerController: containerController)
    }
    
    @objc(forceShowNotice)
    dynamic func forceShowNotice() {
        Didomi.shared.forceShowNotice()
    }
    
    @objc(showNotice)
    dynamic func showNotice() {
        Didomi.shared.showNotice()
    }
    
    @objc(hideNotice)
    dynamic func hideNotice() {
        Didomi.shared.hideNotice()
    }
    
    @objc(isNoticeVisible:reject:)
    dynamic func isNoticeVisible(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.isNoticeVisible())
    }
    
    @objc(shouldConsentBeCollected:reject:)
    dynamic func shouldConsentBeCollected(resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) {
        resolve(Didomi.shared.shouldConsentBeCollected())
    }
    
    //    @objc(showPreferences:view:)
    //    dynamic func showPreferences(controller: UIViewController? = nil, view: Didomi.Didomi.Views = .purposes) {
    //        Didomi.shared.showPreferences(controller: controller, view: view)
    //    }
    
    @objc(hidePreferences)
    dynamic func hidePreferences() {
        Didomi.shared.hidePreferences()
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
        resolve(Didomi.shared.getText(key: key))
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
    
    // MARK: Listeners
    private func createListener() -> EventListener{
        let didomiEventListener = EventListener()
        let emiter = RCTEventEmitter()
        
        didomiEventListener.onConsentChanged = { event in
            emiter.sendEvent(withName: "onConsentChanged", body: "")
        }
        
        didomiEventListener.onHideNotice = { event in
            emiter.sendEvent(withName: "onHideNotice", body: "")
        }
        
        didomiEventListener.onReady = { event in
            emiter.sendEvent(withName: "onReady", body: "")
        }
        
        didomiEventListener.onError = { event in
            emiter.sendEvent(withName: "onError", body: "")
        }
        
        didomiEventListener.onShowNotice = { event in
            emiter.sendEvent(withName: "onShowNotice", body: "")
        }
        
        didomiEventListener.onNoticeClickAgree = { event in
            emiter.sendEvent(withName: "onNoticeClickAgree", body: "")
        }
        
        didomiEventListener.onNoticeClickDisagree = { event in
            emiter.sendEvent(withName: "onNoticeClickDisagree", body: "")
        }
        
        didomiEventListener.onNoticeClickMoreInfo = { event in
            emiter.sendEvent(withName: "onNoticeClickMoreInfo", body: "")
        }
        
        didomiEventListener.onNoticeClickViewVendors = { event in
            emiter.sendEvent(withName: "onNoticeClickViewVendors", body: "")
        }
        
        didomiEventListener.onNoticeClickPrivacyPolicy = { event in
            emiter.sendEvent(withName: "onNoticeClickPrivacyPolicy", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAll = { event in
            emiter.sendEvent(withName: "onPreferencesClickAgreeToAll", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAll = { event in
            emiter.sendEvent(withName: "onPreferencesClickDisagreeToAll", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAllPurposes = { event in
            emiter.sendEvent(withName: "onPreferencesClickAgreeToAllPurposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAllPurposes = { event in
            emiter.sendEvent(withName: "onPreferencesClickDisagreeToAllPurposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickResetAllPurposes = { event in
            emiter.sendEvent(withName: "onPreferencesClickResetAllPurposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickAgreeToAllVendors = { event in
            emiter.sendEvent(withName: "onPreferencesClickAgreeToAllVendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickDisagreeToAllVendors = { event in
            emiter.sendEvent(withName: "onPreferencesClickDisagreeToAllVendors", body: "")
        }
        
        //        didomiEventListener.onPreferencesClickPurposeAgree = { event in
        //            emiter.sendEvent(withName: "onPreferencesClickPurposeAgree", body: "")
        //        }
        //
        //        didomiEventListener.onPreferencesClickPurposeDisagree = { event in
        //            emiter.sendEvent(withName: "onPreferencesClickPurposeDisagree", body: "")
        //        }
        //
        //        didomiEventListener.onPreferencesClickCategoryAgree = { event in
        //            emiter.sendEvent(withName: "onPreferencesClickCategoryAgree", body: "")
        //        }
        //
        //        didomiEventListener.onPreferencesClickCategoryDisagree = { event in
        //            emiter.sendEvent(withName: "onPreferencesClickCategoryDisagree", body: "")
        //        }
        
        didomiEventListener.onPreferencesClickViewVendors = { event in
            emiter.sendEvent(withName: "onPreferencesClickViewVendors", body: "")
        }
        
        didomiEventListener.onPreferencesClickViewPurposes = { event in
            emiter.sendEvent(withName: "onPreferencesClickViewPurposes", body: "")
        }
        
        didomiEventListener.onPreferencesClickSaveChoices = { event in
            emiter.sendEvent(withName: "onPreferencesClickSaveChoices", body: "")
        }
        
        //        didomiEventListener.onPreferencesClickVendorAgree = { event in
        //            emiter.sendEvent(withName: "onPreferencesClickVendorAgree", body: "")
        //        }
        //
        //        didomiEventListener.onPreferencesClickVendorDisagree = { event in
        //            emiter.sendEvent(withName: "onPreferencesClickVendorDisagree", body: "")
        //        }
        
        didomiEventListener.onPreferencesClickVendorSaveChoices = { event in
            emiter.sendEvent(withName: "onPreferencesClickVendorSaveChoices", body: "")
        }
        
        //        didomiEventListener.onSyncDone = { event in
        //            emiter.sendEvent(withName: "onSyncDone", body: "")
        //        }
        
        return didomiEventListener
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
