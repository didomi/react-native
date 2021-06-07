//
//  DidomiExampleUITests.swift
//  DidomiExampleUITests
//
//  Created by Gilles Grousset on 03/06/2021.
//

import XCTest

class DidomiExampleUITests: XCTestCase {
  
  override func setUpWithError() throws {
    // Put setup code here. This method is called before the invocation of each test method in the class.
    let app = XCUIApplication()
    app.launchArguments.append("--UITests")
    // In UI tests it is usually best to stop immediately when a failure occurs.
    continueAfterFailure = false
    
    // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
  }
  
  override func tearDownWithError() throws {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
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
  
  private func testMethodCall(app: XCUIApplication,name: String) {
    app.buttons[name].tap()
    let resultLabel = app.staticTexts[name + "-OK"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: resultLabel, handler: nil)
    waitForExpectations(timeout: 7, handler: nil)
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
    testMethodCall(app: app, name: "reset")
  }
  
  func testSetupUI() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "setupUI")
    
    // Check opening of notice
    let noticeButton = app.staticTexts["Agree & Close"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: noticeButton, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
    
    testLastEvent(app: app, name:"on_show_notice")
    
    // Close notice
    noticeButton.tap()
    
    testLastEvent(app: app, name:"on_notice_click_agree")
  }
  
  func testSetLogLevel() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "setLogLevel")
  }
  
  func testUpdateSelectedLanguage() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "updateSelectedLanguage")
  }
  
  func testShowNotice() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "showNotice")
    
    // Check opening of notice
    let noticeButton = app.staticTexts["Agree & Close"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: noticeButton, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
    
    testLastEvent(app: app, name:"on_show_notice")
    
    // Close notice
    noticeButton.tap()
    
    testLastEvent(app: app, name:"on_notice_click_agree")
  }
  
  func testHideNotice() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "hideNotice")
  }
  
  func testShowPreferencesPurposes() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "showPreferences Purposes")
    
    // Check opening of Purposes
    let noticeButton = app.staticTexts["Save"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: noticeButton, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
    
    // Close notice
    noticeButton.tap()
    
    testLastEvent(app: app, name:"on_hide_notice")
  }
  
  func testShowPreferencesVendors() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "showPreferences Vendors")
    
    // Check opening of Vendors
    let noticeButton = app.staticTexts["Save"]
    let closeButton = app.buttons.element(boundBy: 4)
    let text = app.staticTexts["Select partners"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: text, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
    
    // Close notice
    closeButton.tap()
    noticeButton.tap()
    
    testLastEvent(app: app, name:"on_hide_notice")
  }
  
  func testHidePreferences() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "hidePreferences")
  }
  
  
  // MARK: GETTERS
  
  func testGetDisabledPurposes() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getDisabledPurposes")
  }
  
  func testGetDisabledPurposeIds() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getDisabledPurposeIds")
  }
  
  func testGetDisabledVendors() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getDisabledVendors")
  }
  
  func testGetDisabledVendorIds() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getDisabledVendorIds")
  }
  
  func testGetEnabledPurposes() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getDisabledVendorIds")
  }
  
  func testGetEnabledPurposeIds() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getDisabledVendorIds")
  }
  
  func testGetEnabledVendors() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getEnabledVendors")
  }
  
  func testGetenabledVendorIds() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getEnabledVendorIds")
  }
  
  
  // MARK: GETTERS WITH PARAMS
  
  func testGetPurposeWithId() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getPurpose [ID = 'analytics']")
  }
  
  func testGetText() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getText [Key = '0']")
  }
  
  func testGetTranslatedText() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getTranslatedText [Key = '0']")
  }
  
  func testGetUserConsentStatusForPurpose() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getUserConsentStatusForPurpose [ID = 'analytics']")
  }
  
  func testGetUserConsentStatusForVendor() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getUserConsentStatusForVendor [ID = '0']")
  }
  
  func testGetUserConsentStatusForVendorAndRequiredPurpose() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "getUserConsentStatusForVendorAndRequiredPurposes [ID = '1']")
  }
  
  
  // MARK: SETTERS
  
  func testSetUserStatusSets() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "setUserStatusSets")
  }
  
  func testSetUserAgreeToAll() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "setUserAgreeToAll")
  }
  
  func testSetUserDisagreeToAll() throws {
    let app = XCUIApplication()
    testMethodCall(app: app, name: "setUserDisagreeToAll")
  }
}
