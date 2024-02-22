//
//  PurposeData.swift
//  DidomiExampleUITests
//
//  Created by Felipe Saez V on 12/08/21.
//

import Foundation

// Type used for testing purpose related scenarios
struct PurposeData: Decodable {
  let id: String
  let name: String
  let description: String
}
