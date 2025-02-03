import Foundation

extension String {
    func toJSON() throws -> [String: Any] {
        guard let data = self.data(using: .utf8),
              let json = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] else {
            throw NSError(domain: "Didomi", code: 400, userInfo: [NSLocalizedDescriptionKey: "Invalid JSON format"])
        }
        return json
    }

    func toJSONArray() throws -> [[String: Any]] {
        guard let data = self.data(using: .utf8),
              let json = try JSONSerialization.jsonObject(with: data, options: []) as? [[String: Any]] else {
            throw NSError(domain: "Didomi", code: 400, userInfo: [NSLocalizedDescriptionKey: "Invalid JSON array format"])
        }
        return json
    }
    
    func getJSONValue(for key: String) throws -> Any? {
        let json = try self.toJSON()
        return json[key]
    }
}
