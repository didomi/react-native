//
//  DidomiExampleUITests.swift
//  DidomiExampleUITests
//
//  Created by Gilles Grousset on 03/06/2021.
//

import XCTest

class DidomiExampleUITests: XCTestCase {
    
  let allPurposeIDs = "cookies,create_ads_profile,geolocation_data,select_personalized_ads"
  let allVendorIDs = "28,google"
  
  override func setUpWithError() throws {
    // Put setup code here. This method is called before the invocation of each test method in the class.
    let app = XCUIApplication()
    app.launchArguments.append("--UITests")
    // In UI tests it is usually best to stop immediately when a failure occurs.
    continueAfterFailure = false
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
  
  func testAOnReadyEvent() throws {
    
    // Restart the app to make sure last event is "on ready"
    let app = XCUIApplication()
    app.terminate()
    app.activate()
    
    testLastEvent(app: app, name:"on_ready")
    
  }
  
  func testReset() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "reset")
    assertResult(in: app, name: "reset", expected: "reset-OK")
  }
  
  func testSetupUI() throws {
    let app = XCUIApplication()
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
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setLogLevel")
    assertResult(in: app, name: "setLogLevel", expected: "setLogLevel-OK")
  }
  
  func testShowNotice() throws {
    let app = XCUIApplication()
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
    let app = XCUIApplication()
    
    tapButton(in: app, name: "hideNotice")
    assertResult(in: app, name: "hideNotice", expected: "hideNotice-OK")
  }
  
  func testHidePreferences() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "hidePreferences")
    assertResult(in: app, name: "hidePreferences", expected: "hidePreferences-OK")
  }
  
  
  // MARK: GETTERS
  
  func testGetDisabledPurposes() throws {
    let app = XCUIApplication()
    
    disagreeToAll(in: app)
    
    tapButton(in: app, name: "getDisabledPurposes")
    assertResult(in: app, name: "getDisabledPurposes", expected: allPurposeIDs)
  }
  
  func testGetDisabledPurposeIds() throws {
    let app = XCUIApplication()
    
    disagreeToAll(in: app)
    
    tapButton(in: app, name: "getDisabledPurposeIds")
    assertResult(in: app, name: "getDisabledPurposeIds", expected: allPurposeIDs)
  }
  
  func testGetDisabledVendors() throws {
    let app = XCUIApplication()
    
    disagreeToAll(in: app)
    
    tapButton(in: app, name: "getDisabledVendors")
    assertResult(in: app, name: "getDisabledVendors", expected: allVendorIDs)
  }
  
  func testGetDisabledVendorIds() throws {
    let app = XCUIApplication()
    
    disagreeToAll(in: app)
    
    tapButton(in: app, name: "getDisabledVendorIds")
    assertResult(in: app, name: "getDisabledVendorIds", expected: allVendorIDs)
  }
  
  func testGetEnabledPurposes() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getEnabledPurposes")
    assertResult(in: app, name: "getEnabledPurposes", expected: allPurposeIDs)
  }
  
  func testGetEnabledPurposeIds() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getEnabledPurposeIds")
    assertResult(in: app, name: "getEnabledPurposeIds", expected: allPurposeIDs)
  }
  
  func testGetEnabledVendors() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getEnabledVendors")
    assertResult(in: app, name: "getEnabledVendors", expected: allVendorIDs)
  }
  
  func testGetEnabledVendorIds() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getEnabledVendorIds")
    assertResult(in: app, name: "getEnabledVendorIds", expected: allVendorIDs)
  }
  
  func testGetJavaScriptForWebView() throws {
    let app = XCUIApplication()
    
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
    let app = XCUIApplication()
        
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
  
  func testGetUserStatus() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "getUserStatus")
    
    let staticText = app.staticTexts["getUserStatus-result"]
    staticText.wait()
    
    let actual = staticText.label
    
    // The text might change every time we call the getUserStatus method
    // so we'll only assert the first level parameters of the resulting json string.
    XCTAssertTrue(actual.contains("\"addtl_consent\":\"\""))
    XCTAssertTrue(actual.contains("\"consent_string\":\"\""))
    XCTAssertTrue(actual.contains("\"purposes\":{\"legitimate_interest\":{\"enabled\":["))
    XCTAssertTrue(actual.contains("\"vendors\":{\"consent\":{\"enabled\":["))
    XCTAssertTrue(actual.contains("\"user_id\":\""))
    XCTAssertTrue(actual.contains("\"created\":\""))
    XCTAssertTrue(actual.contains("\"updated\":\""))

  }
  
  // MARK: GETTERS WITH PARAMS
  
  func testGetPurposeWithId() throws {
    let app = XCUIApplication()
        
    tapButton(in: app, name: "getPurpose [ID = 'cookies']")
    
    let staticText = app.staticTexts["getPurpose [ID = 'cookies']-result"]
    staticText.wait()
    
    let actualRaw = staticText.label.removeNewLinesAndTrailingSpaces()
    let actual = decodePurpose(actualRaw)
    
    let expected = PurposeData(id: "cookies", name: "purpose_1_name", iabId: "1", description: "purpose_1_description")
    assertEqual(actual, expected)
  }
  
  func testGetText() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "getText [Key = '0']")
    assertResult(in: app, name: "getText [Key = '0']", expected: "")
  }
  
  func testGetTranslatedText() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "getTranslatedText [Key = '0']")
    assertResult(in: app, name: "getTranslatedText [Key = '0']", expected: "\"0\"")
  }
  
  func testGetUserConsentStatusForPurpose() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserConsentStatusForPurpose [ID = 'cookies']")
    assertResult(in: app, name: "getUserConsentStatusForPurpose [ID = 'cookies']", expected: "true")
  }
  
  func testGetUserConsentStatusForVendor() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserConsentStatusForVendor [ID = '755']")
    assertResult(in: app, name: "getUserConsentStatusForVendor [ID = '755']", expected: "true")
  }
  
  func testGetUserStatusForVendor() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserStatusForVendor [ID = '755']")
    assertResult(in: app, name: "getUserStatusForVendor [ID = '755']", expected: "true")
  }
  
  func testGetUserConsentStatusForVendorAndRequiredPurpose() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserConsentStatusForVendorAndRequiredPurposes [ID = '755']")
    assertResult(in: app, name: "getUserConsentStatusForVendorAndRequiredPurposes [ID = '755']", expected: "true")
  }
  
  func testGetUserLegitimateInterestStatusForPurpose() throws {
    let app = XCUIApplication()
    
    resetUserStatus(in: app)
    
    tapButton(in: app, name: "getUserLegitimateInterestStatusForPurpose [ID = 'cookies']")
    assertResult(in: app, name: "getUserLegitimateInterestStatusForPurpose [ID = 'cookies']", expected: "true")
  }
  
  func testGetUserLegitimateInterestStatusForVendor() throws {
    let app = XCUIApplication()
    
    resetUserStatus(in: app)
    
    tapButton(in: app, name: "getUserLegitimateInterestStatusForVendor [ID = '755']")
    assertResult(in: app, name: "getUserLegitimateInterestStatusForVendor [ID = '755']", expected: "true")
  }
  
  func testGetJavaScriptForWebViewWithExtra() throws {
    let app = XCUIApplication()
    
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
  
  func testGetUserLegitimateInterestStatusForVendorAndRequiredPurposes() throws {
    let app = XCUIApplication()
    
    resetUserStatus(in: app)
    
    tapButton(in: app, name: "getUserLegitimateInterestStatusForVendorAndRequiredPurposes [ID = '755']")
    assertResult(in: app, name: "getUserLegitimateInterestStatusForVendorAndRequiredPurposes [ID = '755']", expected: "true")
  }
  
  // MARK: SETTERS
  
  func testSetUserStatusSets() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserStatusSets")
    assertResult(in: app, name: "setUserStatusSets", expected: "setUserStatusSets-OK")
  }
  
  func testSetUserAgreeToAll() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserAgreeToAll")
    assertResult(in: app, name: "setUserAgreeToAll", expected: "setUserAgreeToAll-OK")
  }
  
  func testSetUserDisagreeToAll() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserDisagreeToAll")
    assertResult(in: app, name: "setUserDisagreeToAll", expected: "setUserDisagreeToAll-OK")
  }
  
  func testSetUserWithId() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserWithId")
    assertResult(in: app, name: "setUserWithId", expected: "setUserWithId-OK")
  }
  
  func testSetUserWithHashAuth() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserWithHashAuth")
    assertResult(in: app, name: "setUserWithHashAuth", expected: "setUserWithHashAuth-OK")
  }
  
  func testSetUserWithHashAuthWithSaltAndExpiration() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserWithHashAuthWithSaltAndExpiration")
    assertResult(in: app, name: "setUserWithHashAuthWithSaltAndExpiration", expected: "setUserWithHashAuthWithSaltAndExpiration-OK")
  }
  
  func testSetUserWithEncryptionAuth() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserWithEncryptionAuth")
    assertResult(in: app, name: "setUserWithEncryptionAuth", expected: "setUserWithEncryptionAuth-OK")
  }
  
  func testSetUserWithEncryptionAuthWithExpiration() throws {
    let app = XCUIApplication()
    
    tapButton(in: app, name: "setUserWithEncryptionAuthWithExpiration")
    assertResult(in: app, name: "setUserWithEncryptionAuthWithExpiration", expected: "setUserWithEncryptionAuthWithExpiration-OK")
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
    XCTAssertEqual(purpose1.iabId, purpose2.iabId)
    XCTAssertEqual(purpose1.description, purpose2.description)
  }
}
