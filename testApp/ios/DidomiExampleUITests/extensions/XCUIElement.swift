//
//  XCUIElement.swift
//  DidomiExampleUITests
//
//  Created by Felipe Saez V on 11/08/21.
//

import Foundation
import XCTest

extension XCUIElement {
  func waitAndTap(timeout: TimeInterval = 10) {
    let buttonExists = waitForExistence(timeout: timeout)
    XCTAssert(buttonExists)
    tap()
  }
  
  func wait(timeout: TimeInterval = 10) {
    let buttonExists = waitForExistence(timeout: timeout)
    XCTAssert(buttonExists)
  }
}
