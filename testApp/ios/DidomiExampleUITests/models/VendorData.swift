//
//  VendorData.swift
//  DidomiExampleUITests
//
//  Created by Nicolas Chaix on 21/09/2022.
//

import Foundation

// Type used for testing vendor related scenarios
struct VendorData: Decodable {
  let id: String
  let name: String
  let iabId: String
  let namespace: String
  let deviceStorageDisclosureUrl: String
  let policyUrl: String
}
