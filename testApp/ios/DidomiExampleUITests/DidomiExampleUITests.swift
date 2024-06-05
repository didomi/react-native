//
//  DidomiExampleUITests.swift
//  DidomiExampleUITests
//
//  Created by Gilles Grousset on 03/06/2021.
//

import XCTest

class DidomiExampleUITests: XCTestCase {
    
  let allPurposeIDs = "cookies,create_ads_profile,device_characteristics,geolocation_data,improve_products,market_research,measure_ad_performance,measure_content_performance,select_basic_ads,select_personalized_ads,use_limited_data_to_select_content"
  let allVendorIDs = "1111,217,272"
  
  override func setUpWithError() throws {
    // Put setup code here. This method is called before the invocation of each test method in the class.
    let app = XCUIApplication()
    app.launchArguments.append("--UITests")
    // In UI tests it is usually best to stop immediately when a failure occurs.
    continueAfterFailure = false
  }
  
  private func initApp() -> XCUIApplication {
    let app = XCUIApplication()
    app.activate()
    return app
  }
  
  private func waitUntilElementExists(element: XCUIElement, timeout: TimeInterval) {
    let startTime = Date.timeIntervalSinceReferenceDate
    while !element.exists {
      if (Date.timeIntervalSinceReferenceDate - startTime) > timeout {
        XCTFail("Timed out waiting for element to exist")
        return
      }
      CFRunLoopRunInMode(.defaultMode, 0.1, false)
    }
  }
  
  private func testLastEvent(app: XCUIApplication, name: String) {
    let event = app.staticTexts["LAST RECEIVED EVENT: " + name]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: event, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
  }
  
  func testOnReadyEvent() throws {
    let app = initApp()
    
    // Restart the app to make sure last event is "on ready"
    app.terminate()
    app.activate()
    
    testLastEvent(app: app, name:"on_ready")
  }

  func testSdkReady() throws {
    let app = initApp()

    assertResult(in: app, name: "ready", expected: "SDK STATUS: READY")
  }
  
  func testInitializeParameters() throws {
    let app = initApp()
    
    tapButton(in: app, name: "Initialize FR")
    assertResult(in: app, name: "Initialize FR", expected: "Initialize FR-OK")
    
    assertResult(in: app, name: "ready", expected: "SDK STATUS: READY")
    
    tapButton(in: app, name: "getUserStatus regulation")
    assertResult(in: app, name: "getUserStatus regulation", expected: "\"gdpr\"")

    tapButton(in: app, name: "Initialize US CA")
    assertResult(in: app, name: "Initialize US CA", expected: "Initialize US CA-OK")
    
    assertResult(in: app, name: "ready", expected: "SDK STATUS: READY")
    
    tapButton(in: app, name: "getUserStatus regulation")
    assertResult(in: app, name: "getUserStatus regulation", expected: "\"cpra\"")
    
    tapButton(in: app, name: "Initialize default notice")
    assertResult(in: app, name: "Initialize default notice", expected: "Initialize default notice-OK")
    assertResult(in: app, name: "ready", expected: "SDK STATUS: READY")
  }
  
  func testReset() throws {
    let app = initApp()

    tapButton(in: app, name: "reset")
    assertResult(in: app, name: "reset", expected: "reset-OK")
  }
  
  func testSetupUI() throws {
    let app = initApp()
    tapButton(in: app, name: "reset")
    tapButton(in: app, name: "setupUI")
    assertResult(in: app, name: "setupUI", expected: "setupUI-OK")
    
    // Check opening of notice
    let noticeButton = app.staticTexts["Agree & Close"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: noticeButton, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
    
    testLastEvent(app: app, name:"on_show_notice")
    
    // Close notice
    noticeButton.tap()
    testLastEvent(app: app, name:"on_hide_notice")
  }
  
  func testSetLogLevel() throws {
    let app = initApp()

    tapButton(in: app, name: "setLogLevel")
    assertResult(in: app, name: "setLogLevel", expected: "setLogLevel-OK")
  }
  
  func testUpdateSelectedLanguage() throws {
    let app = initApp()

    tapButton(in: app, name: "updateSelectedLanguage")
    assertResult(in: app, name: "updateSelectedLanguage", expected: "updateSelectedLanguage-OK")

    testLastEvent(app: app, name:"on_language_updated\"fr\"")
  }
  
  func testShowNotice() throws {
    let app = initApp()
    tapButton(in: app, name: "reset")
    tapButton(in: app, name: "showNotice")
    assertResult(in: app, name: "showNotice", expected: "showNotice-OK")
    
    // Check opening of notice
    let noticeButton = app.staticTexts["Agree & Close"]
    noticeButton.wait()
    
    testLastEvent(app: app, name:"on_show_notice")
    
    // Close notice
    noticeButton.tap()
    
    testLastEvent(app: app, name:"on_hide_notice")
  }
  
  func testHideNotice() throws {
    let app = initApp()

    tapButton(in: app, name: "hideNotice")
    assertResult(in: app, name: "hideNotice", expected: "hideNotice-OK")
  }
  
  func testHidePreferences() throws {
    let app = initApp()

    tapButton(in: app, name: "hidePreferences")
    assertResult(in: app, name: "hidePreferences", expected: "hidePreferences-OK")
  }
  
  func testVendorStatusListener() throws {
    let app = initApp()
    tapButton(in: app, name: "reset")

    tapButton(in: app, name: "Listen ipromote Vendor status")
    assertResult(in: app, name: "Listen ipromote Vendor status", expected: "Listen ipromote Vendor status-OK")
    
    tapButton(in: app, name: "setUserAgreeToAll")
    testLastEvent(app: app, name:"Vendor status ipromote -> true")

    tapButton(in: app, name: "Restore event listeners")
    assertResult(in: app, name: "Restore event listeners", expected: "Restore event listeners-OK")
    
    tapButton(in: app, name: "setUserDisagreeToAll")
    testLastEvent(app: app, name:"on_consent_changed")
  }
  
  // MARK: GETTERS
  
  func testGetJavaScriptForWebView() throws {
    let app = initApp()

    resetUserStatus(in: app)
    
    let expected = """
    \"window.didomiOnReady = window.didomiOnReady || [];window.didomiOnReady.push(function (Didomi) {
    Didomi.notice.hide();Didomi.setUserStatus({"purposes":{"consent":{"enabled":[],"disabled":[]},"legitimate_interest":{"enabled":[],"disabled":
    []}},"vendors":{"consent":{"enabled":[],"disabled":[]},"legitimate_interest":{"enabled":[],"disabled":
    []}},"user_id":"","created":"","updated":"","source":{"type":"app","domain":"io.didomi.reactnativeapp"},"action":"webview"});});\"
    """.removeNewLinesAndTrailingSpaces()
    
    tapButton(in: app, name: "getJavaScriptForWebView")
    
    let staticText = app.staticTexts["getJavaScriptForWebView-result"]
    staticText.wait()
    
    let actual = staticText.label.removeJSONProperties(["updated", "created", "user_id"])
    
    XCTAssertEqual(actual, expected)
  }
  
  func testGetQueryStringForWebView() throws {
    let app = initApp()

    resetUserStatus(in: app)
    
    let expected = "\"didomiConfig.user.externalConsent.value".removeNewLinesAndTrailingSpaces()
    
    tapButton(in: app, name: "getQueryStringForWebView")
    
    let staticText = app.staticTexts["getQueryStringForWebView-result"]
    staticText.wait()
    
    let actual = staticText.label
    // The text might change every time we call the getQueryStringForWebView method
    // so we'll only assert the beginning of the resulting string.
    let actualPrefix = String(actual.prefix(40))
    
    XCTAssertEqual(actualPrefix, expected)
  }
  
  func testIsUserStatusPartial() throws {
    let app = initApp()
    
    tapButton(in: app, name: "reset")

    tapButton(in: app, name: "isUserStatusPartial")
    assertResult(in: app, name: "isUserStatusPartial", expected: "true")

    disagreeToAll(in: app)
    
    tapButton(in: app, name: "isUserStatusPartial")
    assertResult(in: app, name: "isUserStatusPartial", expected: "false")
  }
  
  func testShouldUserStatusBeCollected() throws {
    let app = initApp()
    
    tapButton(in: app, name: "reset")

    tapButton(in: app, name: "shouldUserStatusBeCollected")
    assertResult(in: app, name: "shouldUserStatusBeCollected", expected: "true")

    disagreeToAll(in: app)
    
    tapButton(in: app, name: "shouldUserStatusBeCollected")
    assertResult(in: app, name: "shouldUserStatusBeCollected", expected: "false")
  }

  // MARK: GetUserStatus
  
  func testGetUserStatus() throws {
    let app = initApp()

    tapButton(in: app, name: "getUserStatus")
    
    let staticText = app.staticTexts["getUserStatus-result"]
    staticText.wait()
    
    let actual = staticText.label
    
    // The text might change every time we call the getUserStatus method
    // so we'll only assert the first level parameters of the resulting json string.
    XCTAssertTrue(actual.contains("\"addtl_consent\":\"\""))
    XCTAssertTrue(actual.contains("\"consent_string\":\""))
    XCTAssertTrue(actual.contains("\"purposes\":{"))
    XCTAssertTrue(actual.contains("\"legitimate_interest\":{\"enabled\":[")) // Purposes child - can change position
    XCTAssertTrue(actual.contains("\"consent\":{\"enabled\":[")) // Purposes child - can change position
    XCTAssertTrue(actual.contains("\"global\":{\"enabled\":[")) // Purposes child - can change position
    XCTAssertTrue(actual.contains("\"vendors\":{"))
    XCTAssertTrue(actual.contains("\"consent\":{\"enabled\":[")) // Vendors child - can change position
    XCTAssertTrue(actual.contains("\"global\":{\"enabled\":[")) // Vendors child - can change position
    XCTAssertTrue(actual.contains("\"global_consent\":{\"enabled\":[")) // Vendors child - can change position
    XCTAssertTrue(actual.contains("\"global_li\":{\"enabled\":[")) // Vendors child - can change position
    XCTAssertTrue(actual.contains("\"legitimate_interest\":{\"enabled\":[")) // Vendors child - can change position
    XCTAssertTrue(actual.contains("\"user_id\":\""))
    XCTAssertTrue(actual.contains("\"created\":\""))
    XCTAssertTrue(actual.contains("\"updated\":\""))
    XCTAssertTrue(actual.contains("\"didomi_dcs\":\"\"")) // DCS feature flag is disabled (empty string)
    XCTAssertTrue(actual.contains("\"regulation\":\"gdpr\""))
  }
  
  func testGetUserStatus_Purposes() throws {
    let app = initApp()

    tapButton(in: app, name: "getUserStatus purposes")
    
    let staticText = app.staticTexts["getUserStatus purposes-result"]
    staticText.wait()
    
    let actual = staticText.label
    
    // The text might change every time we call the getUserStatus method
    // so we'll only assert the first level parameters of the resulting json string.
    XCTAssertTrue(actual.contains("{\"legitimate_interest\":{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"global\":{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"essential\":["))
    XCTAssertTrue(actual.contains(",\"consent\":{\"enabled\":["))
  }
  
  func testGetUserStatus_Vendors() throws {
    let app = initApp()

    tapButton(in: app, name: "getUserStatus vendors")
    
    let staticText = app.staticTexts["getUserStatus vendors-result"]
    staticText.wait()
    
    let actual = staticText.label
    
    // The text might change every time we call the getUserStatus method
    // so we'll only assert the first level parameters of the resulting json string.
    XCTAssertTrue(actual.contains("{\"consent\":{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"global_li\":{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"global_consent\":{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"legitimate_interest\":{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"global\":{\"enabled\":["))
  }
  
  func testGetUserStatus_Vendors_global_consent() throws {
    let app = initApp()

    tapButton(in: app, name: "getUserStatus vendors global_consent")
    
    let staticText = app.staticTexts["getUserStatus vendors global_consent-result"]
    staticText.wait()
    
    let actual = staticText.label
    
    // The text might change every time we call the getUserStatus method
    // so we'll only assert the first level parameters of the resulting json string.
    XCTAssertTrue(actual.contains("{\"enabled\":["))
    XCTAssertTrue(actual.contains(",\"disabled\":["))
    XCTAssertTrue(actual.contains("]}"))
  }

  // MARK: GetCurrentUserStatus
  
  func testGetCurrentUserStatus() throws {
    let app = initApp()

    tapButton(in: app, name: "getCurrentUserStatus")

    let staticText = app.staticTexts["getCurrentUserStatus-result"]
    staticText.wait()
    
    let actual = staticText.label
    
    // The text might change every time we call the getCurrentUserStatus method
    // so we'll only assert the first level parameters of the resulting json string.
    XCTAssertTrue(actual.contains("\"addtl_consent\":\"\""))
    XCTAssertTrue(actual.contains("\"consent_string\":\""))
    XCTAssertTrue(actual.contains("\"purposes\":{\""))
    XCTAssertTrue(actual.contains("\"measure_content_performance\":")) // Key
    XCTAssertTrue(actual.contains("\"id\":\"measure_content_performance\"")) // Value - id only because id/enabled can change position
    XCTAssertTrue(actual.contains("\"vendors\":{\""))
    XCTAssertTrue(actual.contains("\"ipromote\":")) // Key
    XCTAssertTrue(actual.contains("\"id\":\"ipromote\"")) // Value - id only because id/enabled can change position
    XCTAssertTrue(actual.contains("\"user_id\":\""))
    XCTAssertTrue(actual.contains("\"created\":\""))
    XCTAssertTrue(actual.contains("\"updated\":\""))
    XCTAssertTrue(actual.contains("\"didomi_dcs\":\"\"")) // DCS feature flag is disabled (empty string)
    XCTAssertTrue(actual.contains("\"regulation\":\"gdpr\""))
  }
  
  // MARK: GETTERS WITH PARAMS
  
  func testGetPurposeWithId() throws {
    let app = initApp()

    tapButton(in: app, name: "getPurpose [ID = 'cookies']")
    
    let staticText = app.staticTexts["getPurpose [ID = 'cookies']-result"]
    staticText.wait()
    
    let actualRaw = staticText.label.removeNewLinesAndTrailingSpaces()
    let actual = decodePurpose(actualRaw)
    
    let expected = PurposeData(id: "cookies", name: "Store and/or access information on a device", description: "Cookies, device or similar online identifiers (e.g. login-based identifiers, randomly assigned identifiers, network based identifiers) together with other information (e.g. browser type and information, language, screen size, supported technologies etc.) can be stored or read on your device to recognise it each time it connects to an app or to a website, for one or several of the purposes presented here.")
    assertEqual(actual, expected)
  }
  
  func testGetVendorWithId() throws {
    let app = initApp()

    tapButton(in: app, name: "getVendor [ID = '217']")
    
    let staticText = app.staticTexts["getVendor [ID = '217']-result"]
    staticText.wait()
    
    let actualRaw = staticText.label.removeNewLinesAndTrailingSpaces()
    let actual = decodeVendor(actualRaw)
    
    let expected = VendorData(id: "ipromote", name: "2KDirect, Inc. (dba iPromote)", policyUrl: nil)
    assertEqual(actual, expected)
  }
  
  func testGetVendorWithId_urls() throws {
    let app = initApp()

    tapButton(in: app, name: "getVendor [ID = '217'] urls[0]")

    let staticText = app.staticTexts["getVendor [ID = '217'] urls[0]-result"]
    staticText.wait()
    
    let actualRaw = staticText.label.removeNewLinesAndTrailingSpaces()
    let actual = decodeVendorURLData(actualRaw)

    let expected = VendorURLData(langId: "en", privacy: "https://www.ipromote.com/privacy-policy/", legIntClaim: "https://www.ipromote.com/privacy-policy/")
    assertEqual(actual, expected)
  }
  
  func testGetText() throws {
    let app = initApp()

    tapButton(in: app, name: "getText [Key = '0']")
    assertResult(in: app, name: "getText [Key = '0']", expected: "")
  }
  
  func testGetTranslatedText() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "getTranslatedText [Key = '0']")
    assertResult(in: app, name: "getTranslatedText [Key = '0']", expected: "\"0\"")
  }
  
  func testGetJavaScriptForWebViewWithExtra() throws {
    let app = initApp()

    resetUserStatus(in: app)
    
    let expected = """
    \"window.didomiOnReady = window.didomiOnReady || [];window.didomiOnReady.push(function (Didomi) {
    Didomi.notice.hide();Didomi.setUserStatus({"purposes":{"consent":{"enabled":[],"disabled":[]},"legitimate_interest":{"enabled":[],"disabled":
    []}},"vendors":{"consent":{"enabled":[],"disabled":[]},"legitimate_interest":{"enabled":[],"disabled":
    []}},"user_id":"","created":"","updated":"","source":{"type":"app","domain":"io.didomi.reactnativeapp"},"action":"webview"});
    console.log('extra JS!');});\"
    """.removeNewLinesAndTrailingSpaces()
    
    tapButton(in: app, name: "getJavaScriptForWebViewWithExtra")
    
    let staticText = app.staticTexts["getJavaScriptForWebViewWithExtra-result"]
    staticText.wait()
    
    let actual = staticText.label.removeJSONProperties(["updated", "created", "user_id"])
    
    XCTAssertEqual(actual, expected)
  }
  
  // MARK: SETTERS
  
  func testSetCurrentUserStatusFails() throws {
    let app = initApp()

    tapButton(in: app, name: "setCurrentUserStatus-Fails")
    assertResult(in: app, name: "setCurrentUserStatus-Fails", expected: "\"setCurrentUserStatus-Fails-false\"")
  }
  
  func testSetCurrentUserStatusSucceeds() throws {
    let app = initApp()

    tapButton(in: app, name: "setCurrentUserStatus-Succeeds")
    assertResult(in: app, name: "setCurrentUserStatus-Succeeds", expected: "\"setCurrentUserStatus-Succeeds-true\"")
  }
  
  func testSetUserStatusSets() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserStatusSets")
    assertResult(in: app, name: "setUserStatusSets", expected: "setUserStatusSets-OK")
  }
  
  func testSetUserAgreeToAll() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserAgreeToAll")
    assertResult(in: app, name: "setUserAgreeToAll", expected: "setUserAgreeToAll-OK")
  }
  
  func testSetUserDisagreeToAll() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserDisagreeToAll")
    assertResult(in: app, name: "setUserDisagreeToAll", expected: "setUserDisagreeToAll-OK")
  }
  
  // MARK: CurrentUserStatusTransaction - single purpose
  func testEnablePurposeTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "enablePurpose[cookies]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-true\"")
  }
  
  func testEnablePurposeTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "enablePurpose[cookies]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-true\"")
  }
  
  func testDisablePurposeTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "disablePurpose[cookies]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-false\"")
  }
  
  func testDisablePurposeTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "disablePurpose[cookies]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-false\"")
  }
  
  // MARK: CurrentUserStatusTransaction - multiple purposes
  func testEnablePurposesTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "enablePurposes[cookies]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-true\"")
  }
  
  func testEnablePurposesTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "enablePurposes[cookies]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-true\"")
  }
  
  func testDisablePurposesTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "disablePurposes[cookies]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-false\"")
  }
  
  func testDisablePurposesTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "disablePurposes[cookies]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-false\"")
  }

  // MARK: CurrentUserStatusTransaction - single vendors
  func testEnableVendorTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "enableVendor[ipromote]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-true\"")
  }
  
  func testEnableVendorTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "enableVendor[ipromote]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-true\"")
  }
  
  func testDisableVendorTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "disableVendor[ipromote]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-false\"")
  }
  
  func testDisableVendorTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "disableVendor[ipromote]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-false\"")
  }
  
  // MARK: CurrentUserStatusTransaction - multiple vendors
  func testEnableVendorsTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "enableVendors[ipromote]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-true\"")
  }
  
  func testEnableVendorsTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "enableVendors[ipromote]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-true\"")
  }
  
  func testDisableVendorsTransactionWithChanges() throws {
    let app = initApp()
    let buttonName = "disableVendors[ipromote]-transaction"
    tapButton(in: app, name: "setUserAgreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-true-enabled-false\"")
  }
  
  func testDisableVendorsTransactionWithoutChanges() throws {
    let app = initApp()
    let buttonName = "disableVendors[ipromote]-transaction"
    tapButton(in: app, name: "setUserDisagreeToAll")
    tapButton(in: app, name: buttonName)
    assertResult(in: app, name: buttonName, expected: "\"\(buttonName)-updated-false-enabled-false\"")
  }
  
  // MARK: SET USER
  
  func testClearUser() throws {
    let app = initApp()

    tapButton(in: app, name: "clearUser")
    assertResult(in: app, name: "clearUser", expected: "clearUser-OK")
  }
  
  func testSetUserWithId() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithId")
    assertResult(in: app, name: "setUserWithId", expected: "setUserWithId-OK")
  }
  
  func testSetUserWithIdAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithIdAndSetupUI")
    assertResult(in: app, name: "setUserWithIdAndSetupUI", expected: "setUserWithIdAndSetupUI-OK")
  }
  
  func testSetUserWithHashAuth() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithHashAuth")
    assertResult(in: app, name: "setUserWithHashAuth", expected: "setUserWithHashAuth-OK")
  }
  
  func testSetUserWithHashAuthAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithHashAuthAndSetupUI")
    assertResult(in: app, name: "setUserWithHashAuthAndSetupUI", expected: "setUserWithHashAuthAndSetupUI-OK")
  }
  
  func testSetUserWithHashAuthWithSaltAndExpiration() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithHashAuthWithSaltAndExpiration")
    assertResult(in: app, name: "setUserWithHashAuthWithSaltAndExpiration", expected: "setUserWithHashAuthWithSaltAndExpiration-OK")
  }
  
  func testSetUserWithHashAuthWithSaltAndExpirationAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithHashAuthWithSaltAndExpirationAndSetupUI")
    assertResult(in: app, name: "setUserWithHashAuthWithSaltAndExpirationAndSetupUI", expected: "setUserWithHashAuthWithSaltAndExpirationAndSetupUI-OK")
  }
  
  func testSetUserWithEncryptionAuth() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithEncryptionAuth")
    assertResult(in: app, name: "setUserWithEncryptionAuth", expected: "setUserWithEncryptionAuth-OK")
  }
  
  func testSetUserWithEncryptionAuthAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithEncryptionAuthAndSetupUI")
    assertResult(in: app, name: "setUserWithEncryptionAuthAndSetupUI", expected: "setUserWithEncryptionAuthAndSetupUI-OK")
  }
  
  func testSetUserWithEncryptionAuthWithExpiration() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithEncryptionAuthWithExpiration")
    assertResult(in: app, name: "setUserWithEncryptionAuthWithExpiration", expected: "setUserWithEncryptionAuthWithExpiration-OK")
  }
  
  func testSetUserWithEncryptionAuthWithExpirationAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithEncryptionAuthWithExpirationAndSetupUI")
    assertResult(in: app, name: "setUserWithEncryptionAuthWithExpirationAndSetupUI", expected: "setUserWithEncryptionAuthWithExpirationAndSetupUI-OK")
  }
  
  func testSetUserWithHashAuthWithSynchronizedUsers() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithHashAuthWithSynchronizedUsers")
    assertResult(in: app, name: "setUserWithHashAuthWithSynchronizedUsers", expected: "setUserWithHashAuthWithSynchronizedUsers-OK")
  }
  
  func testSetUserWithHashAuthWithSynchronizedUsersAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithHashAuthWithSynchronizedUsersAndSetupUI")
    assertResult(in: app, name: "setUserWithHashAuthWithSynchronizedUsersAndSetupUI", expected: "setUserWithHashAuthWithSynchronizedUsersAndSetupUI-OK")
  }
  
  func testSetUserWithEncryptionAuthWithSynchronizedUsers() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithEncryptionAuthWithSynchronizedUsers")
    assertResult(in: app, name: "setUserWithEncryptionAuthWithSynchronizedUsers", expected: "setUserWithEncryptionAuthWithSynchronizedUsers-OK")
  }
  
  func testSetUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI() throws {
    let app = initApp()

    tapButton(in: app, name: "setUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI")
    assertResult(in: app, name: "setUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI", expected: "setUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI-OK")
  }
  
  func testSyncReadyEvent() throws {
    let app = initApp()

    tapButton(in: app, name: "Listen user sync")
    assertResult(in: app, name: "Listen user sync", expected: "Listen user sync-OK")

    tapButton(in: app, name: "setUserWithId")
    assertResult(in: app, name: "setUserWithId", expected: "setUserWithId-OK")
    
    testLastEvent(app: app, name: "Sync Ready, status applied? true, acknowledged? true")
    
    tapButton(in: app, name: "setUserWithEncryptionAuthWithExpirationAndSetupUI")
    assertResult(in: app, name: "setUserWithEncryptionAuthWithExpirationAndSetupUI", expected: "setUserWithEncryptionAuthWithExpirationAndSetupUI-OK")
    
    tapButton(in: app, name: "Restore event listeners")
    assertResult(in: app, name: "Restore event listeners", expected: "Restore event listeners-OK")
  }
}

// Utility methods
extension DidomiExampleUITests {
  private func disagreeToAll(in app: XCUIApplication) {
    tapButton(in: app, name: "setUserDisagreeToAll")
    assertResult(in: app, name: "setUserDisagreeToAll", expected: "setUserDisagreeToAll-OK")
  }
  
  private func agreeToAll(in app: XCUIApplication) {
    tapButton(in: app, name: "setUserAgreeToAll")
    assertResult(in: app, name: "setUserAgreeToAll", expected: "setUserAgreeToAll-OK")
  }
  
  private func resetUserStatus(in app: XCUIApplication) {
    tapButton(in: app, name: "reset")
    assertResult(in: app, name: "reset", expected: "reset-OK")
  }
  
  private func tapButton(in app: XCUIApplication, name: String) {
    let button = app.buttons[name]
    button.waitAndTap()
  }
  
  private func assertResult(in app: XCUIApplication, name: String, expected expectedRaw: String) {
    let staticText = app.staticTexts[name + "-result"]
    staticText.wait()
    
    let actual = staticText.label.removeNewLinesAndTrailingSpaces()
    let expected = expectedRaw.removeNewLinesAndTrailingSpaces()
    XCTAssertEqual(actual, expected)
  }
  
  func decodePurpose(_ string: String) -> PurposeData {
    let data = string.data(using: .utf8)
    let jsonDecoder = JSONDecoder()
    return try! jsonDecoder.decode(PurposeData.self, from: data!)
  }
  
  func assertEqual(_ purpose1: PurposeData, _ purpose2: PurposeData) {
    XCTAssertEqual(purpose1.name, purpose2.name)
    XCTAssertEqual(purpose1.id, purpose2.id)
    XCTAssertEqual(purpose1.description, purpose2.description)
  }
  
  func decodeVendor(_ string: String) -> VendorData {
    let data = string.data(using: .utf8)
    let jsonDecoder = JSONDecoder()
    return try! jsonDecoder.decode(VendorData.self, from: data!)
  }
  
  func decodeVendorURLData(_ string: String) -> VendorURLData {
    let data = string.data(using: .utf8)
    let jsonDecoder = JSONDecoder()
    return try! jsonDecoder.decode(VendorURLData.self, from: data!)
  }

  func assertEqual(_ vendor1: VendorData, _ vendor2: VendorData) {
    XCTAssertEqual(vendor1.name, vendor2.name)
    XCTAssertEqual(vendor1.id, vendor2.id)
    XCTAssertEqual(vendor1.policyUrl, vendor2.policyUrl)
  }

  func assertEqual(_ url1: VendorURLData, _ url2: VendorURLData) {
    XCTAssertEqual(url1.langId, url2.langId)
    XCTAssertEqual(url1.privacy, url2.privacy)
    XCTAssertEqual(url1.legIntClaim, url2.legIntClaim)
  }
}
