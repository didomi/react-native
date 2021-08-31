//
//  DidomiExampleUITests.swift
//  DidomiExampleUITests
//
//  Created by Gilles Grousset on 03/06/2021.
//

import XCTest

class DidomiExampleUITests: XCTestCase {
    
  let allPurposeIDs = "ad_delivery,advertising_personalization,analytics,content_personalization,cookies"
  let allVendorIDs = """
  1,10,100,101,102,104,105,108,109,11,110,111,112,113,114,115,118,119,12,120,122,124,125,126,127,128,129,13,130,131,132,133,134,136,137,138,139,14,140,141,142,143,144,145,147,148,149,15,150,151,152,153,154,155,156,157,158,159,16,160,161,162,163,164,165,167,168,169,17,170,171,173,174,175,177,178,179,18,180,182,183,184,185,188,189,19,190,191,192,193,194,195,196,197,198,199,2,20,200,201,202,203,205,206,208,209,21,210,211,212,213,214,215,216,217,218,22,221,223,224,225,226,227,228,229,23,230,231,232,234,235,236,237,238,239,24,240,241,242,243,244,245,246,248,249,25,250,251,252,253,254,255,256,257,258,259,26,260,261,262,263,264,265,266,268,269,27,270,272,273,274,275,276,277,278,279,28,280,281,282,284,285,287,288,29,290,291,293,294,295,297,298,299,3,30,301,302,303,304,308,31,310,311,312,314,315,316,317,318,319,32,320,321,323,325,326,328,329,33,330,331,333,334,335,336,337,338,339,34,340,341,343,344,345,346,347,349,35,350,351,354,357,358,359,36,360,361,362,365,366,368,369,37,371,373,374,375,376,377,378,38,380,381,382,384,385,387,388,389,39,390,392,394,395,397,398,4,40,400,402,403,404,405,407,408,409,41,410,412,413,415,416,418,42,421,422,423,424,425,426,427,428,429,43,430,434,435,436,438,439,44,440,442,443,444,447,448,449,45,450,452,454,455,458,459,46,461,462,464,465,466,467,468,469,47,471,473,474,475,476,478,479,48,480,482,484,486,487,489,49,490,491,492,493,494,495,496,497,498,499,50,500,501,502,504,505,506,507,509,51,511,512,513,514,515,516,517,518,519,52,520,521,522,523,524,525,527,528,529,53,530,531,534,535,536,537,539,540,541,542,543,544,545,546,547,548,549,55,550,551,553,554,556,559,56,560,561,565,568,569,57,570,571,572,573,574,576,577,578,579,58,580,581,584,587,59,590,591,592,593,596,597,598,599,6,60,601,602,604,606,607,608,609,61,610,612,613,614,615,617,618,619,62,620,621,623,624,625,626,627,628,63,631,635,638,639,64,644,645,646,647,648,649,65,650,652,653,654,655,656,657,658,659,66,660,662,663,664,665,666,667,668,67,670,671,672,673,674,675,676,677,678,68,681,682,684,685,686,687,688,69,690,691,697,7,70,702,706,707,708,709,71,712,713,715,716,717,718,719,72,720,721,723,724,728,729,73,731,732,733,734,736,737,738,739,74,740,741,742,743,744,746,747,748,749,75,754,756,758,759,76,760,761,763,764,765,766,768,77,770,773,775,78,79,8,80,81,82,83,84,85,86,87,88,89,9,90,91,92,93,94,95,97,98
  """
  
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
    
    // Restart the app to make shure last event is "on ready"
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
  
//  func testUpdateSelectedLanguage() throws {
//    let app = XCUIApplication()
//    testMethodCall(app: app, name: "updateSelectedLanguage")
//  }
  
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
  
//  func testShowPreferencesPurposes() throws {
//    let app = XCUIApplication()
//    testMethodCall(app: app, name: "showPreferences Purposes")
//
//    // Check opening of Purposes
//    let noticeButton = app.staticTexts["Save"]
//    let exists = NSPredicate(format: "exists == 1")
//    expectation(for: exists, evaluatedWith: noticeButton, handler: nil)
//    waitForExpectations(timeout: 5, handler: nil)
//
//    // Close notice
//    noticeButton.tap()
//
//    testLastEvent(app: app, name:"on_hide_notice")
//  }
  
//  func testShowPreferencesVendors() throws {
//    let app = XCUIApplication()
//    testMethodCall(app: app, name: "showPreferences Vendors")
//
//    // Check opening of Vendors
//    let noticeButton = app.staticTexts["Save"]
//    let closeButton = app.buttons.element(boundBy: 5)
//    let text = app.staticTexts["Select partners"]
//    let exists = NSPredicate(format: "exists == 1")
//    expectation(for: exists, evaluatedWith: text, handler: nil)
//    waitForExpectations(timeout: 5, handler: nil)
//
//    // Close notice
//    closeButton.tap()
//    noticeButton.tap()
//
//    testLastEvent(app: app, name:"on_hide_notice")
//  }
  
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
  
  // MARK: GETTERS WITH PARAMS
  
  func testGetPurposeWithId() throws {
    let app = XCUIApplication()
        
    tapButton(in: app, name: "getPurpose [ID = 'analytics']")
    
    let staticText = app.staticTexts["getPurpose [ID = 'analytics']-result"]
    staticText.wait()
    
    let actualRaw = staticText.label.removeNewLinesAndTrailingSpaces()
    let actual = decodePurpose(actualRaw)
    
    let expected = PurposeData(id: "analytics", name: "measurement_268d0e0", iabId: "5", description: "measurement_description_268d0e0")
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
    
    tapButton(in: app, name: "getUserConsentStatusForPurpose [ID = 'analytics']")
    assertResult(in: app, name: "getUserConsentStatusForPurpose [ID = 'analytics']", expected: "true")
  }
  
  func testGetUserConsentStatusForVendor() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserConsentStatusForVendor [ID = '1']")
    assertResult(in: app, name: "getUserConsentStatusForVendor [ID = '1']", expected: "true")
  }
  
  func testGetUserStatusForVendor() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserStatusForVendor [ID = '1']")
    assertResult(in: app, name: "getUserStatusForVendor [ID = '1']", expected: "true")
  }
  
  func testGetUserConsentStatusForVendorAndRequiredPurpose() throws {
    let app = XCUIApplication()
    
    agreeToAll(in: app)
    
    tapButton(in: app, name: "getUserConsentStatusForVendorAndRequiredPurposes [ID = '1']")
    assertResult(in: app, name: "getUserConsentStatusForVendorAndRequiredPurposes [ID = '1']", expected: "true")
  }
  
  func testGetUserLegitimateInterestStatusForPurpose() throws {
    let app = XCUIApplication()
    
    resetUserStatus(in: app)
    
    tapButton(in: app, name: "getUserLegitimateInterestStatusForPurpose [ID = 'analytics']")
    assertResult(in: app, name: "getUserLegitimateInterestStatusForPurpose [ID = 'analytics']", expected: "true")
  }
  
  func testGetUserLegitimateInterestStatusForVendor() throws {
    let app = XCUIApplication()
    
    resetUserStatus(in: app)
    
    tapButton(in: app, name: "getUserLegitimateInterestStatusForVendor [ID = '1']")
    assertResult(in: app, name: "getUserLegitimateInterestStatusForVendor [ID = '1']", expected: "true")
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
    
    tapButton(in: app, name: "getUserLegitimateInterestStatusForVendorAndRequiredPurposes [ID = '1']")
    assertResult(in: app, name: "getUserLegitimateInterestStatusForVendorAndRequiredPurposes [ID = '1']", expected: "true")
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
