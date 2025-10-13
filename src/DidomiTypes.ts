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
    /** 
     * @deprecated SPI purposes are now displayed in preferences screen, use {@link #NOTICE_CLICK_MORE_INFO} instead.
     */
    NOTICE_CLICK_VIEW_SPI_PURPOSES = "on_notice_click_view_spi_purposes",
    NOTICE_CLICK_MORE_INFO = "on_notice_click_more_info",
    NOTICE_CLICK_PRIVACY_POLICY = "on_notice_click_privacy_policy",
    // Preferences
    HIDE_PREFERENCES = "on_hide_preferences",
    SHOW_PREFERENCES = "on_show_preferences",
    // Preferences - Views
    PREFERENCES_CLICK_VIEW_PURPOSES = "on_preferences_click_view_purposes",
    PREFERENCES_CLICK_VIEW_VENDORS = "on_preferences_click_view_vendors",
    /**
     *  @deprecated SPI purposes are now displayed in preferences screen.
     */
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
    // Preferences - Sensitive Personal Information (deprecated)
    /** 
     * @deprecated SPI purposes are now treated as other purposes, use {@link #PREFERENCES_CLICK_PURPOSE_AGREE} instead.
     */
    PREFERENCES_CLICK_SPI_PURPOSE_AGREE = "on_preferences_click_spi_purpose_agree",
    /**
     * @deprecated SPI purposes are now treated as other purposes, use {@link #PREFERENCES_CLICK_PURPOSE_DISAGREE} instead.
     */
    PREFERENCES_CLICK_SPI_PURPOSE_DISAGREE = "on_preferences_click_spi_purpose_disagree",
    /** 
     * @deprecated SPI purposes are now treated as other purposes, use {@link #PREFERENCES_CLICK_CATEGORY_AGREE} instead.
     */
    PREFERENCES_CLICK_SPI_CATEGORY_AGREE = "on_preferences_click_spi_category_agree",
    /** 
     * @deprecated SPI purposes are now treated as other purposes, use {@link #PREFERENCES_CLICK_CATEGORY_DISAGREE} instead.
     */
    PREFERENCES_CLICK_SPI_CATEGORY_DISAGREE = "on_preferences_click_spi_category_disagree",
    /** 
     * @deprecated SPI purposes are now displayed in preferences screen, use {@link #PREFERENCES_CLICK_SAVE_CHOICES} instead.
     */
    PREFERENCES_CLICK_SPI_PURPOSE_SAVE_CHOICES = "on_preferences_click_spi_purpose_save_choices",
    // Sync
    SYNC_READY = "on_sync_ready",
    SYNC_ERROR = "on_sync_error",
    /** 
     * @deprecated use {@link #SYNC_READY} instead.
     */
    SYNC_DONE = "on_sync_done",
    // Language
    LANGUAGE_UPDATED = "on_language_updated",
    LANGUAGE_UPDATE_FAILED = "on_language_update_failed",
    // Integrations
    INTEGRATION_ERROR = "on_integration_error",
}

export interface Vendor {
  id: string;
  name: string;
  namespaces: VendorNamespaces;
  policyUrl: string;
  purposeIds: string[];
  flexibleIds: string[];
  legIntPurposeIds: string[];
  flexiblePurposeIds: string[];
  specialFeatureIds: string[];
  specialPurposeIds: string[];
  urls: VendorUrls[];
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
  name: string;
  description: string;
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

export interface CurrentUserStatus {
  purposes: Map<PurposeStatus>;
  vendors: Map<VendorStatus>;
  user_id: string;
  created: string;
  updated: string;
  consent_string: string;
  addtl_consent: string;
  didomi_dcs: string;
  gpp_string: string;
  regulation: string;
}

export interface PurposeStatus {
  id: string;
  enabled: boolean;
}

export interface VendorStatus {
  id: string;
  enabled: boolean;
}

export interface SyncReadyEvent {
  organizationUserId: string;
  statusApplied: boolean;
  syncAcknowledged: () => Promise<boolean>;
}

export interface IntegrationErrorEvent {
  integrationName: string;
  reason: string;
}

export interface UserAuth {
  id: string;
}

export interface UserAuthParams extends UserAuth {
  algorithm: string;
  secretId: string;
  expiration?: number;
}

export interface UserAuthWithEncryptionParams extends UserAuthParams {
  initializationVector: string;
}

export interface UserAuthWithHashParams extends UserAuthParams {
  digest: string;
  salt?: string;
}

/**
 * Initialization parameters for Didomi SDK
 * @interface
 */
export interface DidomiInitializeParameters {
  /**
   * Public API key of the organization from the Didomi Console.
   * @property
   */
  apiKey: string;
  /**
   * Path to your local config file. Defaults to didomi_config.json if nil.
   * @property
   */
  localConfigurationPath?: string;
  /**
   * URL to a remote configuration file to load during initialization. This parameter is not used yet. Set it to nil for now.
   * @property
   */
  remoteConfigurationUrl?: string;
  /**
   * The provider ID (if any).
   * @property
   */
  providerId?: string;
  /** 
   * Whether to disable loading the remove configuration from the Didomi config. Keep this to `false` for loading configuration from the Didomi Console.
   * @property
   * @deprecated In the future, it will be mandatory to create your notice from the console (see https://developers.didomi.io/cmp/mobile-sdk/android/setup#from-the-console-recommended for more information).
   */
  disableDidomiRemoteConfig?: boolean;
  /** 
   * Language in which the consent UI should be displayed. By default, the consent UI is displayed in the language configured in the device settings.
   * This property allows you to override the default setting and specify a language to display the UI in. String containing the language code e.g.: "es", "fr", etc.
   * @property
   */
  languageCode?: string;
  /**
   * ID of the notice configuration to load from the Didomi Console.
   * @property
   */
  noticeId?: string;
  /**
   * ID of the notice to display on Android TV
   * @property
   */
  androidTvNoticeId?: string;
  /**
   * Enable/disable Android TV specific features
   * @property
   */
  androidTvEnabled?: boolean;
  /** 
   * Override user country code when determining the privacy regulation to apply.
   * Keep `undefined` to let the Didomi SDK determine the user country.
   * @property
   */
  countryCode?: string;
  /** 
   * Override user region code when determining the privacy regulation to apply.
   * Keep `undefined` to let the Didomi SDK determine the user region.
   * Ignored if `countryCode` is not set.
   * @property
   */
  regionCode?: string;
  /**
   * If set to `true`, the SDK will only display the underage notice (`false` by default).
   * @property
   */
  isUnderage?: boolean;
}

/**
 * Object that contains the parameters for the setUser method.
 * @interface
 */
export interface DidomiUserParameters {
  /**
   * @property
   */
  userId?: string;
  /**
   * Main user authentication
   * - [UserAuthWithEncryptionParams] (encryption)
   * - [UserAuthWithHashParams] (hash)
   * - [UserAuthWithoutParams] (ID only)
   * @property
   */
  userAuth?: UserAuth;
  /**
   * User authentication for Didomi Consent String
   * - [UserAuthWithEncryptionParams] (encryption)
   * - [UserAuthWithHashParams] (hash)
   * @property
   */
  dcsUserAuth?: UserAuthParams;
  /**
   * If the user is underage (undefined will keep the previous setting)
   * @property
   */
  isUnderage?: boolean;
}

/**
 * Object that contains the parameters for the setUser method including synchronized users.
 * @interface
 */ 
export interface DidomiMultiUserParameters extends DidomiUserParameters {
  /**
   * Synchronized user list
   * - [UserAuthWithEncryptionParams] (encryption)
   * - [UserAuthWithHashParams] (hash)
   * @property
   */
  synchronizedUsers?: UserAuthParams[];
}
