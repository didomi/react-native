import { NativeModules } from 'react-native';
import { DidomiListener } from './DidomiListener';
import { DidomiEventType } from './DidomiTypes';
import { DIDOMI_USER_AGENT_NAME, DIDOMI_VERSION } from './Constants';

const { Didomi: RNDidomi } = NativeModules;

export const Didomi = {
  /**
   *  Initialize the Didomi SDK
   *
   *  @param apiKey: Public API key of the organization from the Didomi Console.
   *  @param localConfigurationPath: Path to your local config file. Defaults to didomi_config.json if null.
   *  @param remoteConfigurationURL: URL to a remote configuration file to load during initialization. This parameter is not used yet. Set it to null for now.
   *  @param providerId: Your provider ID (if any).
   *  @param disableDidomiRemoteConfig: Whether to disable loading the remove configuration from the Didomi config. Keep this to "false" for loading configuration from the Didomi Console.
   *  @param languageCode: Language in which the consent UI should be displayed. By default, the consent UI is displayed in the language configured in the device settings. This property allows you to override the default setting and specify a language to display the UI in. String containing the language code e.g.: "es", "fr", etc.
   *  @param noticeId: Notice id for the remote configuration.
   */
  initialize: (
    apiKey: string,
    localConfigurationPath?: string,
    remoteConfigurationUrl?: string,
    providerId?: string,
    disableDidomiRemoteConfig: boolean = false,
    languageCode?: string,
    noticeId?: string
  ): Promise<void> => {
    // Init listener
    DidomiListener.init();

    // Trigger native SDK init
    return RNDidomi.initialize(
      DIDOMI_USER_AGENT_NAME,
      DIDOMI_VERSION,
      apiKey,
      localConfigurationPath,
      remoteConfigurationUrl,
      providerId,
      disableDidomiRemoteConfig,
      languageCode,
      noticeId
    );
  },

  /**
   * Listen to SDK ready state
   */
  onReady: (): Promise<void> => DidomiListener.onReady(),

  /**
   * Listen to SDK errors
   */
  onError: (): Promise<any> => DidomiListener.onError(),

  /**
   * Provide the objects required to display UI elements
   */
  setupUI: (): Promise<void> => RNDidomi.setupUI(),

  /**
   * Set the minimum level of messages to log
   * You can decide what level of messages should be logged by the SDK by calling the setLogLevel function before initialize
   *
   * Messages with a level below `minLevel` will not be logged.
   *
   * For iOS:
   *  Levels are standard levels from `OSLogType` (https://developer.apple.com/documentation/os/logging/choosing_the_log_level_for_a_message):
   *  - OSLogType.info (1)
   *  - OSLogType.debug (2)
   *  - OSLogType.error (16)
   *  - OSLogType.fault (17)
   *
   *  We recommend setting `OSLogType.error` (16) in production
   *
   * For Android:
   *  Use the standard log levels supported by android.util.Log: VERBOSE, DEBUG, INFO, WARN and ERROR.
   *  (https://developer.android.com/reference/android/util/Log#constants_1)
   *
   *  We recommend setting `Log.INFO` (4) in production
   *
   * @param minLevel: Minimum level of messages to log
   */
  setLogLevel: (level: number): Promise<void> => RNDidomi.setLogLevel(level),

  /**
   *  Add an event listener
   *  @param listener:
   */
  addEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => DidomiListener.addEventListener(eventType, callback),

  /**
   *  Remove an event listener
   *  @param listener:
   */
  removeEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => DidomiListener.removeEventListener(eventType, callback),

  /**
   *  Remove all event listeners
   */
  removeAllEventListeners: () => DidomiListener.reset(),

  /**
   *  Method used to get an array of disabled purposes.
   *  @returns: array of disabled purposes.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getPurposes().getConsent().getDisabled().
   */
  getDisabledPurposes: (): Promise<any[]> => RNDidomi.getDisabledPurposes(),

  /**
   *  Method used to get a set of disabled purpose IDs.
   *  @returns: set that contains the IDs of disabled purposes.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getPurposes().getConsent().getDisabled().
   */
  getDisabledPurposeIds: (): Promise<any[]> => RNDidomi.getDisabledPurposeIds(),

  /**
   *  Method used to get an array of disabled vendors.
   *  @returns: array of disabled vendors.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getVendors().getConsent().getDisabled().
   */
  getDisabledVendors: (): Promise<any[]> => RNDidomi.getDisabledVendors(),

  /**
   *  Method used to get a set of disabled vendor IDs.
   *  @returns: set that contains the IDs of disabled vendors.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getVendors().getConsent().getDisabled().
   */
  getDisabledVendorIds: (): Promise<any[]> => RNDidomi.getDisabledVendorIds(),

  /**
   *  Method used to get an array of enabled purposes.
   *  @returns: array of enabled purposes.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getPurposes().getConsent().getEnabled().
   */
  getEnabledPurposes: (): Promise<any[]> => RNDidomi.getEnabledPurposes(),

  /**
   *  Method used to get a set of enabled purpose IDs.
   *  @returns: set that contains the IDs of enabled purposes.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getPurposes().getConsent().getEnabled().
   */
  getEnabledPurposeIds: (): Promise<any[]> => RNDidomi.getEnabledPurposeIds(),

  /**
   *  Method used to get an array of enabled vendors.
   *  @returns: array of enabled vendors.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getVendors().getConsent().getEnabled().
   */
  getEnabledVendors: (): Promise<any[]> => RNDidomi.getEnabledVendors(),

  /**
   *  Method used to get a set of enabled vendor IDs.
   *  @returns: set that contains the IDs of enabled vendors.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method
   *  has been replaced by getUserStatus().getVendors().getConsent().getEnabled().
   */
  getEnabledVendorIds: (): Promise<any[]> => RNDidomi.getEnabledVendorIds(),

  /**
   *  Get JavaScript to embed into a WebView to pass the consent status from the app
   *  to the Didomi Web SDK embedded into the WebView
   *  Inject the returned JavaScript into a WebView
   *  @param extra: Extra JavaScript to inject in the returned code
   *  @returns: JavaScript code to inject in a WebView
   */
  getJavaScriptForWebView: (extra?: string | null): Promise<string> => RNDidomi.getJavaScriptForWebView(extra),

  getQueryStringForWebView: (): Promise<string> => RNDidomi.getQueryStringForWebView(),

  /**
   *  Method used to get a Purpose based on its ID.
   *  @param purposeId: purpose ID used in the search.
   *  @returns: purpose found in the array.
   */
  getPurpose: (purposeId: string): Promise<any> => RNDidomi.getPurpose(purposeId),

  /**
   *  Method used to get an array of required purposes.
   *
   *  @returns: array of required purposes.
   */
  getRequiredPurposes: (): Promise<any[]> => RNDidomi.getRequiredPurposes(),

  /**
   *  Get the configured purpose IDs
   *  @returns: set of purpose IDs.
   */
  getRequiredPurposeIds: (): Promise<any[]> => RNDidomi.getRequiredPurposeIds(),

  /**
   *  Method used to get an array of required vendors.
   *  @returns: array of required vendors.
   */
  getRequiredVendors: (): Promise<any[]> => RNDidomi.getRequiredVendors(),

  /**
   *  Get the configured vendor IDs
   *  @returns: set of vendor IDs.
   */
  getRequiredVendorIds: (): Promise<any[]> => RNDidomi.getRequiredVendorIds(),

  /**
   *  Method used to get a dictionary/map in the form of { "en": "Key in English", "fr": "Key in French." }
   *  for a given key.
   *  @param key: used to find its corresponding value in the dictionary/map.
   *  @returns: dictionary containing the different translations for a given key.
   */
  getText: (key: string): Promise<any[]> => RNDidomi.getText(key),

  /**
   *  Method used to get a translated text based on the key being passed.
   *  The language and the source of this translated text will depend on the availability of the translation for the specific key.
   *  The language being used will be either the selected language of the SDK (based on device Locale and other parameters) or the language specified by app developers as the default language being used by the SDK. The source can be either the `didomi_config.json` file which can be either local or remote, or the `didomi_master_config.json` file which is bundled within the SDK.
   *
   *  These are the attempts performed by the SDK to try to find a translation for the specific key:
   *  - Get translated value in user locale (selected language) from `didomi_config.json` (either local or remote).
   *  - Get translated value in default locale (from the config) from `didomi_config.json` (either local or remote).
   *  - Get translated value in user locale (selected language) from `didomi_master_config.json` (bundled within the Didomi SDK).
   *  - Get translated value in default locale (from the config) from `didomi_master_config.json` (bundled within the Didomi SDK).
   *
   *  If no translation can be found after these 4 attempts, the key will be returned.
   *
   *  App developers can provide these translated texts through the `didomi_config.json` file (locally or remotely) in 3 different ways:
   *  - Custom texts for the consent notice: https://developers.didomi.io/cmp/mobile-sdk/consent-notice/customize-the-notice#texts
   *  - Custom texts for the preferences: https://developers.didomi.io/cmp/mobile-sdk/consent-notice/customize-the-preferences-popup#text
   *  - Custom texts for custom notices: https://developers.didomi.io/cmp/mobile-sdk/consent-notice/customize-the-theme#translatable-texts-for-custom-notices
   *
   *  @param key: used to find its corresponding value in one of the different sources.
   *  @returns: a translated string if a translation was found, the same key that was passed otherwise.
   */
  getTranslatedText: (key: string): Promise<string> => RNDidomi.getTranslatedText(key),

  /**
   *  Get the user consent status for a specific purpose
   *  @param purposeId: The purpose ID to check consent for
   *  @returns: The user consent status for the specified purpose
   *  @deprecated use {@link #getUserStatus()} instead. Search the purposeId in
     * getUserStatus().getPurposes().getConsent().getEnabled() or
   *  getUserStatus().getPurposes().getConsent().getDisabled().
   */
  getUserConsentStatusForPurpose: (purposeId: string): Promise<boolean> => RNDidomi.getUserConsentStatusForPurpose(purposeId),

  /**
   *  Get the user consent status for a specific vendor
   *  @param vendorId: The vendor ID to check consent for
   *  @returns: The user consent status for the specified vendor
   *  @deprecated use {@link #getUserStatus()} instead. Search the vendorId in
   *  getUserStatus().getVendors().getConsent().getEnabled() or
   *  getUserStatus().getVendors().getConsent().getDisabled().
   */
  getUserConsentStatusForVendor: (vendorId: string): Promise<boolean> => RNDidomi.getUserConsentStatusForVendor(vendorId),

  /**
   *  Get the user consent status for a specific vendor and all its purposes
   *  @param vendorId: The ID of the vendor
   *  @returns: The user consent status corresponding to the specified vendor and all its required purposes.
   *  @deprecated use {@link #getUserStatus()} instead. The result of this method has been replaced
   *  by getUserStatus().getVendors().getGlobalConsent().getEnabled() or
   *  getUserStatus().getVendors().getGlobalConsent().getDisabled().
   */
  getUserConsentStatusForVendorAndRequiredPurposes: (vendorId: string): Promise<boolean> => RNDidomi.getUserConsentStatusForVendorAndRequiredPurposes(vendorId),

  /**
   *  Get the user legitimate interest status for a specific purpose.
   *  @param purposeId: purpose ID.
   *  @returns: LI status of a purpose.
   *  @deprecated use {@link #getUserStatus()} instead. Search the purposeId in
   *  getUserStatus().getPurposes().getLegitimateInterest().getEnabled() or
   *  getUserStatus().getPurposes().getLegitimateInterest().getDisabled().
   */
  getUserLegitimateInterestStatusForPurpose: (purposeId: string): Promise<boolean> => RNDidomi.getUserLegitimateInterestStatusForPurpose(purposeId),

  /**
   *  Get the user legitimate interest status for a specific vendor.
   *  @param vendorId: vendor ID.
   *  @returns: LI status of a vendor.
   *  @deprecated use {@link #getUserStatus()} instead. Search the vendorId in
   *  getUserStatus().getVendors().getLegitimateInterest().getEnabled() or
   *  getUserStatus().getVendors().getLegitimateInterest().getDisabled().
   */
  getUserLegitimateInterestForVendor: (vendorId: string): Promise<boolean> => RNDidomi.getUserLegitimateInterestStatusForVendor(vendorId),

  /**
   *  Get the user LI status for a specific vendor and all its purposes.
   *  @param vendorId: vendor ID.
   *  @returns: The user LI status corresponding to the specified vendor and all its required purposes.
   *  @deprecated use {@link #getUserStatus()} instead. Search the purposeId in
   *  getUserStatus().getVendors().getGlobalLegitimateInterest().getEnabled() or
   *  getUserStatus().getVendors().getGlobalLegitimateInterest().getDisabled().
   */
  getUserLegitimateInterestStatusForVendorAndRequiredPurposes: (vendorId: string): boolean => RNDidomi.getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId),

  /**
   *  Get the user consent status.
   *  @returns: status that represents user consent.
   */
  getUserStatus: (): Promise<any> => RNDidomi.getUserStatus(),

  /**
   *  Get the user consent and legitimate interest status for a specific vendor.
   *  @param vendorId: vendor ID.
   *  @returns: status that represents both consent and legitimate interest status of a vendor.
   *  @deprecated use {@link #getUserStatus()} instead. Search the vendorId in
   *  getUserStatus().getVendors().getGlobal().getEnabled() or
   *  getUserStatus().getVendors().getGlobal().getDisabled().
   */
  getUserStatusForVendor: (vendorId: string): Promise<boolean> => RNDidomi.getUserStatusForVendor(vendorId),

  /**
   *  Method used to get a Vendor based on its ID.
   *  @param vendorId: vendor ID used in the search.
   *  @returns: vendor found in the array.
   */
  getVendor: (vendorId: string): Promise<any> => RNDidomi.getVendor(vendorId),

  /**
   *  Hide the notice if it is currently displayed
   */
  hideNotice: (): Promise<void> => RNDidomi.hideNotice(),

  /**
   * Hide the preferences popup for purposes
   */
  hidePreferences: (): Promise<void> => RNDidomi.hidePreferences(),

  /**
   *  Determine if consent is required for the user. The rules are (OR):
   *  - The user country is in the EU.
   *  - The company is from the EU.
   *  - The user country is unknown and the app has chosen to collect consent when unknown.
   *  @returns: **true** if consent is required, **false** if it is not required.
   */
  isConsentRequired: (): Promise<boolean> => RNDidomi.isConsentRequired(),

  /**
   *  Determine if consent information is available for all purposes and vendors that are required
   *  @returns: **true** if consent is required and consent information is available, **false** otherwise.
   */
  isUserConsentStatusPartial: (): Promise<boolean> => RNDidomi.isUserConsentStatusPartial(),

  /**
   *  Check if the consent notice is currently displayed
   *  @returns: **true** if the notice is displayed, **false** if is not.
   */
  isNoticeVisible: (): Promise<boolean> => RNDidomi.isNoticeVisible(),

  /**
   *  Method used to check if the Preferences view is visible.
   *  @returns: **true** if Preferences view is visible, **false** otherwise.
   */
  isPreferencesVisible: (): Promise<boolean> => RNDidomi.isPreferencesVisible(),

  isError: (): Promise<boolean> => RNDidomi.isError(),

  /**
   *  Is the Didomi SDK ready?
   */
  isReady: (): Promise<boolean> => RNDidomi.isReady(),

  /**
   *  Clear user information
   */
  clearUser: (): Promise<void> => RNDidomi.clearUser(),

  /**
   *  Set user information without authentication
   *
   *  @param id Organization user ID
   *  @param algorithm Deprecated. To set user with authentication, use setUserWithHashAuth or setUserWithEncryptionAuth.
   *  @param secretId Deprecated. To set user with authentication, use setUserWithHashAuth or setUserWithEncryptionAuth.
   *  @param salt Deprecated. To set user with authentication, use setUserWithHashAuth or setUserWithEncryptionAuth.
   *  @param digest Deprecated. To set user with authentication, use setUserWithHashAuth or setUserWithEncryptionAuth.
   */
  setUser: (
    id: string,
    algorithm?: string,
    secretId?: string,
    salt?: string,
    digest?: string
  ): Promise<void> =>
    RNDidomi.setUser(
      id,
      algorithm,
      secretId,
      salt,
      digest
    ),

  /**
   *  Set user information without authentication and check for missing consent
   *
   *  @param id Organization user ID
   */
  setUserAndSetupUI: (id: string): Promise<void> => RNDidomi.setUserAndSetupUI(id),

  /**
   *  Set user information with Hash authentication
   *
   *  @param id Organization user ID
   *  @param algorithm Algorithm used for computing the digest
   *  @param secretId ID of the secret used for computing the digest
   *  @param digest Digest of the organization user ID and secret
   *  @param salt Salt used for computing the digest (optional)
   *  @param expiration Expiration date as timestamp (to prevent replay attacks)
   */
  setUserWithHashAuth: (
    id: string,
    algorithm: string,
    secretId: string,
    digest: string,
    salt?: string,
    expiration?: BigInt
  ): void => {
    if (expiration === undefined) {
      RNDidomi.setUserWithHashAuth(
        id,
        algorithm,
        secretId,
        digest,
        salt
      );
    } else {
      RNDidomi.setUserWithHashAuthWithExpiration(
        id,
        algorithm,
        secretId,
        digest,
        salt,
        expiration
      );
    }
  },

  /**
   *  Set user information with Hash authentication and check for missing consent
   *
   *  @param id Organization user ID
   *  @param algorithm Algorithm used for computing the digest
   *  @param secretId ID of the secret used for computing the digest
   *  @param digest Digest of the organization user ID and secret
   *  @param salt Salt used for computing the digest (optional)
   *  @param expiration Expiration date as timestamp (to prevent replay attacks)
   */
  setUserWithHashAuthAndSetupUI: (
    id: string,
    algorithm: string,
    secretId: string,
    digest: string,
    salt?: string,
    expiration?: BigInt
  ): void => {
    if (expiration === undefined) {
      RNDidomi.setUserWithHashAuthAndSetupUI(
        id,
        algorithm,
        secretId,
        digest,
        salt
      );
    } else {
      RNDidomi.setUserWithHashAuthWithExpirationAndSetupUI(
        id,
        algorithm,
        secretId,
        digest,
        salt,
        expiration
      );
    }
  },

  /**
   *  Set user information with Encryption authentication
   *
   *  @param id Organization user ID
   *  @param algorithm Algorithm used for computing the digest
   *  @param secretId ID of the secret used for computing the digest
   *  @param initializationVector Initialization Vector used for computing the user ID
   *  @param expiration Expiration date as timestamp (to prevent replay attacks)
   */
  setUserWithEncryptionAuth: (
    id: string,
    algorithm: string,
    secretId: string,
    initializationVector: string,
    expiration?: BigInt
  ): void => {
    if (expiration === undefined) {
      RNDidomi.setUserWithEncryptionAuth(
        id,
        algorithm,
        secretId,
        initializationVector
      );
    } else {
      RNDidomi.setUserWithEncryptionAuthWithExpiration(
        id,
        algorithm,
        secretId,
        initializationVector,
        expiration
      );
    }
  },

  /**
   *  Set user information with Encryption authentication and check for missing consent
   *
   *  @param id Organization user ID
   *  @param algorithm Algorithm used for computing the digest
   *  @param secretId ID of the secret used for computing the digest
   *  @param initializationVector Initialization Vector used for computing the user ID
   *  @param expiration Expiration date as timestamp (to prevent replay attacks)
   */
  setUserWithEncryptionAuthAndSetupUI: (
    id: string,
    algorithm: string,
    secretId: string,
    initializationVector: string,
    expiration?: BigInt
  ): void => {
    if (expiration === undefined) {
      RNDidomi.setUserWithEncryptionAuthAndSetupUI(
        id,
        algorithm,
        secretId,
        initializationVector
      );
    } else {
      RNDidomi.setUserWithEncryptionAuthWithExpirationAndSetupUI(
        id,
        algorithm,
        secretId,
        initializationVector,
        expiration
      );
    }
  },

  /**
   * Show the consent notice (if required, not disabled in the config and not already displayed)
   */
  showNotice: (): Promise<void> => RNDidomi.showNotice(),

  /**
   *  Show the preferences screen when/if the SDK is ready. By default the purposes list will be displayed.
   *  @param view: It can be `purposes` or `vendors`
   */
  showPreferences: (view?: string): Promise<void> => RNDidomi.showPreferences(view),

  /**
   *  Remove all consents for the user
   */
  reset: (): Promise<void> => RNDidomi.reset(),

  /**
   *  Method that allows to enable consent and legitimate interest for all the required purposes.
   *  @returns: **true** if consent status has been updated, **false** otherwise.
   */
  setUserAgreeToAll: (): Promise<boolean> => RNDidomi.setUserAgreeToAll(),

  /**
   *  Set the user consent status.
   *  @param enabledPurposeIds: set containing **enabled purpose ids**
   *  @param disabledPurposeIds: set containing **disabled purpose ids**
   *  @param enabledVendorIds: set containing **enabled vendor ids**
   *  @param disabledVendorIds: set containing **disabled purpose ids**
   *  @returns: **true** if consent status has been updated, **false** otherwise.
   */
  setUserConsentStatus: (
    enabledPurposeIds: string[],
    disabledPurposeIds: string[],
    enabledVendorIds: string[],
    disabledVendorIds: string[]
  ): Promise<boolean> =>
    RNDidomi.setUserConsentStatus(
      enabledPurposeIds,
      disabledPurposeIds,
      enabledVendorIds,
      disabledVendorIds
    ),

  /**
   * Method that allows to disable consent and legitimate interest for all the required purposes and vendors.
   * @returns: **true** if consent status has been updated, **false** otherwise.
   */
  setUserDisagreeToAll: (): Promise<boolean> => RNDidomi.setUserDisagreeToAll(),

  /**
   *  Set the user status for purposes and vendors for consent and legitimate interest.
   *  @param purposesConsentStatus: boolean used to determine if consent will be enabled or disabled for all purposes.
   *  @param purposesLIStatus: boolean used to determine if legitimate interest will be enabled or disabled for all purposes.
   *  @param vendorsConsentStatus: boolean used to determine if consent will be enabled or disabled for all vendors.
   *  @param vendorsLIStatus: boolean used to determine if legitimate interest will be enabled or disabled for all vendors.
   *  @returns: **true** if consent status has been updated, **false** otherwise.
   */
  setUserStatus: (
    purposesConsentStatus: boolean,
    purposesLIStatus: boolean,
    vendorsConsentStatus: boolean,
    vendorsLIStatus: boolean
  ): Promise<boolean> =>
    RNDidomi.setUserStatus(
      purposesConsentStatus,
      purposesLIStatus,
      vendorsConsentStatus,
      vendorsLIStatus
    ),

  /**
   *  Set the user status for purposes and vendors for consent and legitimate interest.
   *  @param enabledConsentPurposeIds: List of purpose IDs to enable for consent.
   *  @param disabledConsentPurposeIds: List of purpose IDs to disable for consent.
   *  @param enabledLIPurposeIds: List of purpose IDs to enable for LI.
   *  @param disabledLIPurposeIds: List of purpose IDs to disable for LI.
   *  @param enabledConsentVendorIds: List of vendor IDs to enable for consent.
   *  @param disabledConsentVendorIds: List of vendor IDs to disable for consent.
   *  @param enabledLIVendorIds: List of vendor IDs to enable for LI.
   *  @param disabledLIVendorIds: List of vendor IDs to disable for LI.
   */
  setUserStatusSets: (
    enabledConsentPurposeIds: string[],
    disabledConsentPurposeIds: string[],
    enabledLIPurposeIds: string[],
    disabledLIPurposeIds: string[],
    enabledConsentVendorIds: string[],
    disabledConsentVendorIds: string[],
    enabledLIVendorIds: string[],
    disabledLIVendorIds: string[]
  ): Promise<boolean> =>
    RNDidomi.setUserStatusSets(
      enabledConsentPurposeIds,
      disabledConsentPurposeIds,
      enabledLIPurposeIds,
      disabledLIPurposeIds,
      enabledConsentVendorIds,
      disabledConsentVendorIds,
      enabledLIVendorIds,
      disabledLIVendorIds
    ),

  /**
   *  Check if the consent should be collected depending on if we have any consents or if we have some consents but the number of days before displaying the notice again has not expired yet
   *  @returns: A boolean depending if the consent should be collected or not
   */
  shouldConsentBeCollected: (): Promise<boolean> =>
    RNDidomi.shouldConsentBeCollected(),

  /**
   *  Method used to update the selected language of the Didomi SDK and any property that depends on it.
   *  In most cases this method doesn't need to be called. It would only be required for those apps that allow language change on-the-fly, i.e.: from within the app rather than from the device settings.
   *  In order to update the language of the views displayed by the Didomi SDK, this method needs to be called before these views are displayed.
   *  @param languageCode: string containing the language code e.g.: "es", "fr", etc.
   */
  updateSelectedLanguage: (languageCode: string): Promise<void> =>
    RNDidomi.updateSelectedLanguage(languageCode),
};
