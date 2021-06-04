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

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
  
  private func testMethodCall(app: XCUIApplication,name: String) {
    app.buttons[name].tap()
    let resultLabel = app.staticTexts[name + "-OK"]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: resultLabel, handler: nil)
    waitForExpectations(timeout: 10, handler: nil)
  }
  
  private func testLastEvent(app: XCUIApplication, name: String) {
    let event = app.staticTexts["LAST RECEIVED EVENT: " + name]
    let exists = NSPredicate(format: "exists == 1")
    expectation(for: exists, evaluatedWith: event, handler: nil)
    waitForExpectations(timeout: 5, handler: nil)
  }
  
  func testOnReadyEvent() throws {
    
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
  

}
