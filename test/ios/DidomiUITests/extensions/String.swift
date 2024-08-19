//
//  String.swift
//  DidomiUITests
//
//  Created by Felipe Saez V on 11/08/21.
//

import Foundation

extension String {
  func removeNewLinesAndTrailingSpaces() -> String {
    var newString = trimmingCharacters(in: .whitespacesAndNewlines)
    newString = newString.replacingOccurrences(of: "\n", with: "")
    newString = newString.replacingOccurrences(of: "\\", with: "")
    newString = newString.replacingOccurrences(of: "^\\s*", with: "", options: .regularExpression)
    return newString
  }
  
  func removeJSONProperty(name: String) -> String {
    var newString = removeNewLinesAndTrailingSpaces()
    newString = newString.replacingOccurrences(of: "\"\(name)\":\\s*\"[^\"]+\"", with: "\"\(name)\":\"\"", options: .regularExpression)
    return newString
  }
  
  func removeJSONProperties(_ names: [String]) -> String {
    return names.reduce(self) { $0.removeJSONProperty(name: $1) }
  }
}
