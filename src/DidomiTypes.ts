// TODO
export enum DidomiEventType {
    // Consent
    CONSENT_CHANGED = "on_consent_changed",
    // SDK lifecycle events
    ERROR = "on_error",
    READY = "on_ready",
    // Notice
    HIDE_NOTICE = "on_hide_notice",
    SHOW_NOTICE = "on_show_notice",
    NOTICE_CLICK_AGREE = "on_notice_click_agree",
    NOTICE_CLICK_DISAGREE = "on_notice_click_disagree",
    NOTICE_CLICK_VIEW_VENDORS = "on_notice_click_view_vendors",
    NOTICE_CLICK_VIEW_SPI_PURPOSES = "on_notice_click_view_spi_purposes",
    NOTICE_CLICK_MORE_INFO = "on_notice_click_more_info",
    NOTICE_CLICK_PRIVACY_POLICY = "on_notice_click_privacy_policy",
    // Preferences
    HIDE_PREFERENCES = "on_hide_preferences",
    SHOW_PREFERENCES = "on_show_preferences",
    // Preferences - Views
    PREFERENCES_CLICK_VIEW_PURPOSES = "on_preferences_click_view_purposes",
    PREFERENCES_CLICK_VIEW_VENDORS = "on_preferences_click_view_vendors",
    PREFERENCES_CLICK_VIEW_SPI_PURPOSES = "on_preferences_click_view_spi_purposes",
    // Preferences - Purpose
    PREFERENCES_CLICK_AGREE_TO_ALL = "on_preferences_click_agree_to_all",
    PREFERENCES_CLICK_DISAGREE_TO_ALL = "on_preferences_click_disagree_to_all",
    PREFERENCES_CLICK_AGREE_TO_ALL_PURPOSES = "on_preferences_click_agree_to_all_purposes",
    PREFERENCES_CLICK_DISAGREE_TO_ALL_PURPOSES = "on_preferences_click_disagree_to_all_purposes",
    PREFERENCES_CLICK_RESET_ALL_PURPOSES = "on_preferences_click_reset_all_purposes",
    PREFERENCES_CLICK_PURPOSE_AGREE = "on_preferences_click_purpose_agree",
    PREFERENCES_CLICK_PURPOSE_DISAGREE = "on_preferences_click_purpose_disagree",
    PREFERENCES_CLICK_CATEGORY_AGREE = "on_preferences_click_category_agree",
    PREFERENCES_CLICK_CATEGORY_DISAGREE = "on_preferences_click_category_disagree",
    PREFERENCES_CLICK_SAVE_CHOICES = "on_preferences_click_save_choices",
    // Preferences - Vendor
    PREFERENCES_CLICK_AGREE_TO_ALL_VENDORS = "on_preferences_click_agree_to_all_vendors",
    PREFERENCES_CLICK_DISAGREE_TO_ALL_VENDORS = "on_preferences_click_disagree_to_all_vendors",
    PREFERENCES_CLICK_VENDOR_AGREE = "on_preferences_click_vendor_agree",
    PREFERENCES_CLICK_VENDOR_DISAGREE = "on_preferences_click_vendor_disagree",
    PREFERENCES_CLICK_VENDOR_SAVE_CHOICES = "on_preferences_click_vendor_save_choices",
    // Preferences - Sensitive Personal Information
    PREFERENCES_CLICK_SPI_PURPOSE_AGREE = "on_preferences_click_spi_purpose_agree",
    PREFERENCES_CLICK_SPI_PURPOSE_DISAGREE = "on_preferences_click_spi_purpose_disagree",
    PREFERENCES_CLICK_SPI_CATEGORY_AGREE = "on_preferences_click_spi_category_agree",
    PREFERENCES_CLICK_SPI_CATEGORY_DISAGREE = "on_preferences_click_spi_category_disagree",
    PREFERENCES_CLICK_SPI_PURPOSE_SAVE_CHOICES = "on_preferences_click_spi_purpose_save_choices",
    // Sync
    SYNC_DONE = "on_sync_done",
    SYNC_ERROR = "on_sync_error",
    // Language
    LANGUAGE_UPDATED = "on_language_updated",
    LANGUAGE_UPDATE_FAILED = "on_language_update_failed",
}

export interface Vendor {
  id: string;
  iabId: string
  name: string;
  policyUrl: string;
  urls: VendorUrls[];
  namespace: string;
  namespaces: VendorNamespaces;
  iabVendor: boolean;
  purposeIds: string[];
  flexiblePurposeIds: string[];
  specialPurposeIds: string[];
  legIntPurposeIds: string[];
  specialFeatureIds: string[];
  dataDeclaration: string[];
  dataRetention: VendorDataRetention;
  cookieMaxAgeSeconds: number;
  usesNonCookieAccess: boolean;
}

export interface VendorNamespaces {
  iab2: string;
}

export interface VendorUrls {
  privacy: string;
  legIntClaim: string;
  langId: string;
}

export interface VendorDataRetention {
  stdRetention: number;
  purposes: Map<number>;
  specialPurposes: Map<number>;
}

export interface Purpose {
  id: string;
  iabId: string;
  name: string;
  description: string;
  descriptionLegal: string;
  illustrations: string[];
  custom: boolean;
  essential: boolean;
  specialFeature: boolean;
  legitimateInterest: boolean;
  consent: boolean;
  category: PurposeCategory;
}

export interface PurposeCategory {
  id: string;
  purposeId: string;
  icon: string;
  type: string;
  name: Map<string>;
  description: Map<string>;
  children: PurposeCategory[];
}

export interface Map<T> {
  [index: string]: T;
  [index: number]: T;
}

export interface UserStatus {
  purposes: UserStatusPurposes;
  vendors: UserStatusVendors;
  user_id: string;
  created: string;
  updated: string;
  consent_string: string;
  additional_consent: string;
  didomi_dcs: string;
  regulation: string;
}

export interface UserStatusPurposes {
  global: UserStatusIds;
  consent: UserStatusIds;
  legitimate_interest: UserStatusIds;
  essential: UserStatusIds;
}

export interface UserStatusVendors {
  global: UserStatusIds;
  global_consent: UserStatusIds;
  global_legitimate_interest: UserStatusIds;
  consent: UserStatusIds;
  legitimate_interest: UserStatusIds;
}

export interface UserStatusIds {
  disabled: string[];
  enabled: string[];
}
