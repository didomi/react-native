package io.didomi.reactnative

import io.didomi.sdk.Purpose
import io.didomi.sdk.Regulation
import io.didomi.sdk.Vendor
import io.didomi.sdk.apiEvents.Source
import io.didomi.sdk.models.CurrentUserStatus
import io.didomi.sdk.models.UserStatus
import io.didomi.sdk.user.model.UserContext
import org.json.JSONArray
import org.json.JSONObject

/**
 * Converts Didomi entities from and to types that can be sent through the React Native bridge.
 *
 * Entities are mapped field by field (instead of relying on the JSON annotations of the native SDK)
 * so that the keys expected by the TypeScript types (`src/DidomiTypes.ts`) don't depend on how the native SDK serializes its models.
 * Null values are omitted, as they were when the entities were serialized with Gson.
 * The maps only contain values supported by `Arguments.makeNativeMap` / `Arguments.makeNativeArray`.
 */
internal object EntitiesHelper {

    /** Convert a set of purposes into a list of maps. */
    @JvmName("toPurposeList")
    fun toList(purposes: Set<Purpose>): List<Map<String, Any?>> = purposes.map { toMap(it) }

    /** Convert a set of vendors into a list of maps. */
    @JvmName("toVendorList")
    fun toList(vendors: Set<Vendor>): List<Map<String, Any?>> = vendors.map { toMap(it) }

    /** Convert a purpose into a map. */
    fun toMap(purpose: Purpose): Map<String, Any?> = mapOf(
        "id" to purpose.id,
        "name" to purpose.name,
        "description" to purpose.descriptionText,
    )

    /** Convert a vendor into a map. */
    fun toMap(vendor: Vendor): Map<String, Any?> = buildMap {
        put("id", vendor.id)
        put("name", vendor.name)
        vendor.namespaces?.let { put("namespaces", toMap(it)) }
        vendor.policyUrl?.let { put("policyUrl", it) }
        put("purposeIds", vendor.purposeIds)
        put("legIntPurposeIds", vendor.legIntPurposeIds)
        put("featureIds", vendor.featureIds)
        put("flexiblePurposeIds", vendor.flexiblePurposeIds)
        put("specialFeatureIds", vendor.specialFeatureIds)
        put("specialPurposeIds", vendor.specialPurposeIds)
        vendor.urls?.let { urls -> put("urls", urls.map { toMap(it) }) }
        put("isIAB", vendor.isIAB)
    }

    private fun toMap(namespaces: Vendor.Namespaces): Map<String, Any?> = buildMap {
        namespaces.iab2?.let { put("iab2", it) }
        namespaces.num?.let { put("num", it) }
        namespaces.didomi?.let { put("didomi", it) }
        namespaces.sp?.let { put("sp", it) }
    }

    private fun toMap(url: Vendor.Url): Map<String, Any?> = buildMap {
        url.langId?.let { put("langId", it) }
        url.privacy?.let { put("privacy", it) }
        url.legIntClaim?.let { put("legIntClaim", it) }
    }

    /** Convert the current user status into a map. */
    fun toMap(status: CurrentUserStatus): Map<String, Any?> = mapOf(
        "user_id" to status.userId,
        "created" to status.created,
        "updated" to status.updated,
        "consent_string" to status.consentString,
        "addtl_consent" to status.additionalConsent,
        "didomi_dcs" to status.didomiDcs,
        "gpp_string" to status.gppString,
        // Regulation of the status object ("custom" for a regulation configuration, the configuration contains the regulation name)
        "regulation" to status.regulation.value,
        "regulation_configuration" to status.regulationConfiguration?.let { toMap(it) },
        "purposes" to status.purposes.mapValues { (_, purposeStatus) -> toMap(purposeStatus) },
        "vendors" to status.vendors.mapValues { (_, vendorStatus) -> toMap(vendorStatus) },
        "widget" to status.widget?.let { toMap(it) },
    )

    /** Convert a purpose status into a map. */
    fun toMap(purposeStatus: CurrentUserStatus.PurposeStatus): Map<String, Any?> = mapOf(
        "id" to purposeStatus.id,
        "enabled" to purposeStatus.enabled,
    )

    /** Convert a vendor status into a map. */
    fun toMap(vendorStatus: CurrentUserStatus.VendorStatus): Map<String, Any?> = mapOf(
        "id" to vendorStatus.id,
        "enabled" to vendorStatus.enabled,
    )

    /** Convert the widget that collected the consent into a map (null values are kept). */
    fun toMap(widget: Source.Widget): Map<String, Any?> = mapOf(
        "type" to widget.type,
        "id" to widget.id,
        "version_id" to widget.versionId,
    )

    /** Convert a regulation configuration into a map (null values are kept). */
    fun toMap(configuration: Regulation.Configuration): Map<String, Any?> = mapOf(
        "id" to configuration.id,
        "versionId" to configuration.versionId,
        "numericalId" to configuration.numericalId,
        "regulationName" to configuration.regulationName,
        "tokenStorageName" to configuration.tokenStorageName,
        "mixed" to configuration.mixed,
    )

    /** Convert the user status into a map. */
    fun toMap(status: UserStatus): Map<String, Any?> = mapOf(
        "purposes" to toMap(status.purposes),
        "vendors" to toMap(status.vendors),
        "user_id" to status.userId,
        "created" to status.created,
        "updated" to status.updated,
        "consent_string" to status.consentString,
        "addtl_consent" to status.additionalConsent,
        "didomi_dcs" to status.didomiDcs,
        // The native SDK sets the regulation from `Regulation.value`, which `Regulation.fromString` maps back for every regulation except CUSTOM
        "regulation" to (status.regulation?.value ?: Regulation.CUSTOM.value),
    )

    private fun toMap(purposes: UserStatus.Purposes): Map<String, Any?> = mapOf(
        "global" to toMap(purposes.global),
        "consent" to toMap(purposes.consent),
        "legitimate_interest" to toMap(purposes.legitimateInterest),
        "essential" to purposes.essential.toList(),
    )

    private fun toMap(vendors: UserStatus.Vendors): Map<String, Any?> = mapOf(
        "global" to toMap(vendors.global),
        "global_consent" to toMap(vendors.globalConsent),
        "global_li" to toMap(vendors.globalLegitimateInterest),
        "consent" to toMap(vendors.consent),
        "legitimate_interest" to toMap(vendors.legitimateInterest),
    )

    private fun toMap(ids: UserStatus.Ids): Map<String, Any?> = mapOf(
        "disabled" to ids.disabled.toList(),
        "enabled" to ids.enabled.toList(),
    )

    /**
     * Parse the current user status sent by `setCurrentUserStatus` (JSON of the TypeScript `CurrentUserStatus`).
     * Only the purposes and vendors are used by the native SDK.
     *
     * @throws org.json.JSONException if the JSON is invalid or a status has no id.
     */
    fun parseCurrentUserStatus(json: String): CurrentUserStatus {
        val jsonObject = JSONObject(json)
        return CurrentUserStatus(
            purposes = parseStatuses(jsonObject.optJSONObject("purposes")) { id, enabled ->
                CurrentUserStatus.PurposeStatus(id, enabled)
            },
            vendors = parseStatuses(jsonObject.optJSONObject("vendors")) { id, enabled ->
                CurrentUserStatus.VendorStatus(id, enabled)
            },
        )
    }

    private fun <T> parseStatuses(jsonObject: JSONObject?, create: (id: String, enabled: Boolean) -> T): Map<String, T> {
        jsonObject ?: return emptyMap()
        return jsonObject.keys().asSequence().associateWith { key ->
            val status = jsonObject.getJSONObject(key)
            create(status.getString("id"), status.optBoolean("enabled", false))
        }
    }

    /** Parse the user context sent with the user parameters (JSON of the TypeScript `DidomiUserContext`). */
    fun parseUserContext(jsonObject: JSONObject) = UserContext(
        attributes = jsonObject.optJSONObject("attributes")?.toStringMap(),
        variables = jsonObject.optJSONObject("variables")?.toStringMap(),
        age = (jsonObject.opt("age") as? Number)?.toInt(),
        isLoggedIn = jsonObject.opt("isLoggedIn") as? Boolean,
    )

    // Keep the primitive values as strings (null values, objects and arrays are ignored)
    private fun JSONObject.toStringMap(): Map<String, String> = buildMap {
        keys().forEach { key ->
            when (val value = opt(key)) {
                null, JSONObject.NULL, is JSONObject, is JSONArray -> Unit
                else -> put(key, value.toString())
            }
        }
    }
}
