//
//  VendorData.swift
//  DidomiUITests
//
//  Created by Nicolas Chaix on 21/09/2022.
//

import Foundation

// Type used for testing vendor related scenarios
struct VendorData: Decodable {
  let id: String
  let name: String
  let policyUrl: String?
}

// Vendor URLs structure since TCF 2.2
struct VendorURLData: Decodable {
  let langId: String
  let privacy: String
  let legIntClaim: String
}
