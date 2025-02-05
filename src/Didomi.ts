import { NativeModules } from 'react-native';
import { DidomiListener } from './DidomiListener';
import { DidomiEventType, Purpose, Vendor, UserStatus, CurrentUserStatus, VendorStatus, UserAuthParams, DidomiInitializeParameters, DidomiUserParameters } from './DidomiTypes';
import { DIDOMI_USER_AGENT_NAME, DIDOMI_VERSION } from './Constants';
import { CurrentUserStatusTransaction, createCurrentUserStatusTransaction } from './CurrentUserStatusTransaction';

const { Didomi: RNDidomi } = NativeModules;

export const Didomi = {
  /**
   *  Initialize the Didomi SDK
   *
   *  @param apiKey: Public API key of the organization from the Didomi Console.
   *  @param localConfigurationPath: Path to your local config file. Defaults to didomi_config.json if null.
   *  @param remoteConfigurationURL: URL to a remote configuration file to load during initialization. This parameter is not used yet. Set it to null for now.
   *  @param providerId: Your provider ID (if any).
   *  @param disableDidomiRemoteConfig: Whether to disable loading the remote configuration from the Didomi config. Keep this to "false" for loading configuration from the Didomi Console.
   *  @param languageCode: Language in which the consent UI should be displayed. By default, the consent UI is displayed in the language configured in the device settings. This property allows you to override the default setting and specify a language to display the UI in. String containing the language code e.g.: "es", "fr", etc.
   *  @param noticeId: Notice id for the remote configuration.
   *  @param androidTvNoticeId: Notice id for the remote configuration when running on Android TV device (ignored on iOS / tvOS). Note that using remote configuration is mandatory for TV devices.
   *  @param androidTvEnabled: Whether to allow Didomi SDK to run on Android TV, default to "false".
   *  @param countryCode: Override user country code when determining the privacy regulation to apply. Keep undefined to let the Didomi SDK determine the user country.
   *  @param regionCode: Override user region code when determining the privacy regulation to apply. Keep undefined to let the Didomi SDK determine the user region. Ignored if countryCode is not set.
   *  @deprecated Use {@link #initializeWithParameters} instead
   */
  initialize: (
    apiKey: string,
    localConfigurationPath?: string,
    remoteConfigurationUrl?: string,
    providerId?: string,
    disableDidomiRemoteConfig: boolean = false,
    languageCode?: string,
    noticeId?: string,
    androidTvNoticeId?: string,
    androidTvEnabled: boolean = false,
    countryCode?: string,
    regionCode?: string,
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
      noticeId,
      androidTvNoticeId,
      androidTvEnabled,
      countryCode,
      regionCode
    );
  },

  /**
   * Initialize the Didomi SDK with parameters
   * @param parameters: Parameters to initialize the Didomi SDK
   * @returns: Promise that resolves we have called the native initialize method
   */
  initializeWithParameters: (parameters: DidomiInitializeParameters): Promise<void> => {
    // Init listener
    DidomiListener.init();

    // Trigger native SDK init
    return RNDidomi.initializeWithParameters(
      DIDOMI_USER_AGENT_NAME,
      DIDOMI_VERSION,
      JSON.stringify(parameters)
    );
  },

  /**
   * Listen to SDK ready state
   */
  onReady: (): Promise<void> => {
    var promise = DidomiListener.setOnReadyListener();
    RNDidomi.onReady();
    return promise;
  },

  /**
   * Listen to SDK errors
   */
  onError: (): Promise<void> => {
    var promise = DidomiListener.setOnErrorListener();
    RNDidomi.onError();
    return promise;
  },

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
   * Add a listener to be triggered when the user status for the selected vendor changes
   * The callback will be registered after the SDK is ready
   * @param vendorId: the id of the vendor
   * @param callback: the callback to trigger when the user status for the selected vendor changes
   */
  addVendorStatusListener: (
    vendorId: string,
    callback: (vendorStatus: VendorStatus) => void
  ) => {
    RNDidomi.listenToVendorStatus(vendorId)
    DidomiListener.addVendorStatusListener(vendorId, callback);
  },

  /**
   * Remove one or multiple previously added vendor status listeners
   * @param vendorId the id of the vendor
   */
  removeVendorStatusListener: (vendorId: string) => {
    RNDidomi.stopListeningToVendorStatus(vendorId)
    DidomiListener.removeVendorStatusListener(vendorId);
  },

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
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getDisabledPurposes: (): Promise<Purpose[]> => RNDidomi.getDisabledPurposes(),

  /**
   *  Method used to get a set of disabled purpose IDs.
   *  @returns: set that contains the IDs of disabled purposes.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getDisabledPurposeIds: (): Promise<string[]> => RNDidomi.getDisabledPurposeIds(),

  /**
   *  Method used to get an array of disabled vendors.
   *  @returns: array of disabled vendors.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getDisabledVendors: (): Promise<Vendor[]> => RNDidomi.getDisabledVendors(),

  /**
   *  Method used to get a set of disabled vendor IDs.
   *  @returns: set that contains the IDs of disabled vendors.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getDisabledVendorIds: (): Promise<string[]> => RNDidomi.getDisabledVendorIds(),

  /**
   *  Method used to get an array of enabled purposes.
   *  @returns: array of enabled purposes.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getEnabledPurposes: (): Promise<Purpose[]> => RNDidomi.getEnabledPurposes(),

  /**
   *  Method used to get a set of enabled purpose IDs.
   *  @returns: set that contains the IDs of enabled purposes.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getEnabledPurposeIds: (): Promise<string[]> => RNDidomi.getEnabledPurposeIds(),

  /**
   *  Method used to get an array of enabled vendors.
   *  @returns: array of enabled vendors.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getEnabledVendors: (): Promise<Vendor[]> => RNDidomi.getEnabledVendors(),

  /**
   *  Method used to get a set of enabled vendor IDs.
   *  @returns: set that contains the IDs of enabled vendors.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getEnabledVendorIds: (): Promise<string[]> => RNDidomi.getEnabledVendorIds(),

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
  getPurpose: (purposeId: string): Promise<Purpose> => RNDidomi.getPurpose(purposeId),

  /**
   *  Method used to get an array of required purposes.
   *
   *  @returns: array of required purposes.
   */
  getRequiredPurposes: (): Promise<Purpose[]> => RNDidomi.getRequiredPurposes(),

  /**
   *  Get the configured purpose IDs
   *  @returns: set of purpose IDs.
   *  @deprecated use {@link #getRequiredPurposes()} instead.
   */
  getRequiredPurposeIds: (): Promise<string[]> => RNDidomi.getRequiredPurposeIds(),

  /**
   *  Method used to get an array of required vendors.
   *  @returns: array of required vendors.
   */
  getRequiredVendors: (): Promise<Vendor[]> => RNDidomi.getRequiredVendors(),

  /**
   *  Get the configured vendor IDs
   *  @returns: set of vendor IDs.
   *  @deprecated use {@link #getRequiredVendors()} instead.
   */
  getRequiredVendorIds: (): Promise<string[]> => RNDidomi.getRequiredVendorIds(),

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
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserConsentStatusForPurpose: (purposeId: string): Promise<boolean> => RNDidomi.getUserConsentStatusForPurpose(purposeId),

  /**
   *  Get the user consent status for a specific vendor
   *  @param vendorId: The vendor ID to check consent for
   *  @returns: The user consent status for the specified vendor
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserConsentStatusForVendor: (vendorId: string): Promise<boolean> => RNDidomi.getUserConsentStatusForVendor(vendorId),

  /**
   *  Get the user consent status for a specific vendor and all its purposes
   *  @param vendorId: The ID of the vendor
   *  @returns: The user consent status corresponding to the specified vendor and all its required purposes.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserConsentStatusForVendorAndRequiredPurposes: (vendorId: string): Promise<boolean> => RNDidomi.getUserConsentStatusForVendorAndRequiredPurposes(vendorId),

  /**
   *  Get the user legitimate interest status for a specific purpose.
   *  @param purposeId: purpose ID.
   *  @returns: LI status of a purpose.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserLegitimateInterestStatusForPurpose: (purposeId: string): Promise<boolean> => RNDidomi.getUserLegitimateInterestStatusForPurpose(purposeId),

  /**
   *  Get the user legitimate interest status for a specific vendor.
   *  @param vendorId: vendor ID.
   *  @returns: LI status of a vendor.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserLegitimateInterestForVendor: (vendorId: string): Promise<boolean> => RNDidomi.getUserLegitimateInterestStatusForVendor(vendorId),

  /**
   *  Get the user LI status for a specific vendor and all its purposes.
   *  @param vendorId: vendor ID.
   *  @returns: The user LI status corresponding to the specified vendor and all its required purposes.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserLegitimateInterestStatusForVendorAndRequiredPurposes: (vendorId: string): boolean => RNDidomi.getUserLegitimateInterestStatusForVendorAndRequiredPurposes(vendorId),

  /**
   *  Get the current user consent status.
   *  @returns: status that represents current user consent.
   */
  getCurrentUserStatus: (): Promise<CurrentUserStatus> => RNDidomi.getCurrentUserStatus(),

  /**
   *  Get the user consent status.
   *  @returns: status that represents user consent.
   *  @deprecated use {@link #getCurrentUserStatus()} instead.
   */
  getUserStatus: (): Promise<UserStatus> => RNDidomi.getUserStatus(),

  /**
   *  Get the user consent and legitimate interest status for a specific vendor.
   *  @param vendorId: vendor ID.
   *  @returns: status that represents both consent and legitimate interest status of a vendor.
   *  @deprecated use {@link #getUserStatus()} instead. Search the vendorId in
   *  getUserStatus().vendors.global.enabled or
   *  getUserStatus().vendors.global.disabled.
   */
  getUserStatusForVendor: (vendorId: string): Promise<boolean> => RNDidomi.getUserStatusForVendor(vendorId),

  /**
   * Determine the regulation applicable for the user.
   * @returns: the regulation applicable for the current user. If no regulation is applicable, will return "none".
   */
  getApplicableRegulation: (): Promise<string> => RNDidomi.getApplicableRegulation(),

  /**
   *  Method used to get a Vendor based on its ID.
   *  @param vendorId: vendor ID used in the search.
   *  @returns: vendor found in the array.
   */
  getVendor: (vendorId: string): Promise<Vendor> => RNDidomi.getVendor(vendorId),

  /**
   *  Get the total Vendor count.
   *  @returns: total vendor count.
   */
  getTotalVendorCount: (): Promise<number> => RNDidomi.getTotalVendorCount(),

  /**
   *  Get the IAB Vendor count.
   *  @returns: IAB vendor count.
   */
  getIabVendorCount: (): Promise<number> => RNDidomi.getIabVendorCount(),

  /**
   *  Get the non-IAB Vendor count.
   *  @returns: non-IAB vendor count.
   */
  getNonIabVendorCount: (): Promise<number> => RNDidomi.getNonIabVendorCount(),

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
   *  @deprecated use {@link #shouldUserStatusBeCollected()} instead.
   */
  isConsentRequired: (): Promise<boolean> => RNDidomi.isConsentRequired(),

  /**
   *  Determine if user status should be collected for the user.
   *  Returns **true** if user status is required for the current user and regulation, and one of the following two conditions is met:
   *  - User status has never been collected for this user yet
   *  - New user status should be collected (as new vendors have been added) AND the number of days before recollecting them has exceeded
   *  @returns: **true** if user status should be collected according to these rules, **false** otherwise.
   */
  shouldUserStatusBeCollected: (): Promise<boolean> => RNDidomi.shouldUserStatusBeCollected(),

  /**
   *  Determine if consent information is available for all purposes and vendors that are required
   *  @returns: **true** if consent is required and consent information is available, **false** otherwise.
   *  @deprecated use {@link #isUserStatusPartial()} instead.
   */
  isUserConsentStatusPartial: (): Promise<boolean> => RNDidomi.isUserConsentStatusPartial(),

  /**
   *  Determine if user did not express a choice for any purpose or vendor that is required
   *  @returns: **true** if user choice is required and a choice is not expressed, **false** otherwise.
   */
  isUserStatusPartial: (): Promise<boolean> => RNDidomi.isUserStatusPartial(),

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
   */
  setUser: (id: string): Promise<void> => RNDidomi.setUser(id),

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
   *  @deprecated Use {@link setUserWithParameters} instead
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
   *  @deprecated Use {@link setUserWithParametersAndSetupUI} instead
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
   *  @deprecated Use {@link setUserWithParameters} instead
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
   *  @deprecated Use {@link setUserWithParametersAndSetupUI} instead
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
   *  Set user information with authentication (Hash or Encryption)
   *
   *  @param userAuthParams as UserAuthParams (use UserAuthWithEncryptionParams or UserAuthWithHashParams)
   *  @param synchronizedUsers as UserAuthParams[] (optional)
   *  @deprecated Use {@link setUserWithParameters} instead
   */
  setUserWithAuthParams: (
    userAuthParams: UserAuthParams,
    synchronizedUsers?: UserAuthParams[] | undefined
  ): void => RNDidomi.setUserWithAuthParams(JSON.stringify(userAuthParams), JSON.stringify(synchronizedUsers)),

  /**
   *  Set user information with authentication (Hash or Encryption) and check for missing consent
   *
   *  @param userAuthParams as UserAuthParams (use UserAuthWithEncryptionParams or UserAuthWithHashParams)
   *  @param synchronizedUsers as UserAuthParams[] (optional)
   *  @deprecated Use {@link setUserWithParametersAndSetupUI} instead
   */
  setUserWithAuthParamsAndSetupUI: (
    userAuthParams: UserAuthParams,
    synchronizedUsers?: UserAuthParams[] | undefined
  ): void => RNDidomi.setUserWithAuthParamsAndSetupUI(JSON.stringify(userAuthParams), JSON.stringify(synchronizedUsers)),

  /**
   *  Set user information with parameters
   *
   *  @param parameters as DidomiUserParameters
   */
  setUserWithParameters: (parameters: DidomiUserParameters): void => RNDidomi.setUserWithParameters(JSON.stringify(parameters)),

  /**
   *  Set user information with parameters and check for missing consent
   *
   *  @param parameters as DidomiUserParameters
   */
  setUserWithParametersAndSetupUI: (parameters: DidomiUserParameters): void => RNDidomi.setUserWithParametersAndSetupUI(JSON.stringify(parameters)),

  /**
   * Show the consent notice (if required, not disabled in the config and not already displayed)
   */
  showNotice: (): Promise<void> => RNDidomi.showNotice(),

  /**
   *  Show the preferences screen when/if the SDK is ready. By default the purposes list will be displayed.
   *  @param view: It can be `purposes` or `vendors`. Note: `sensitive-personal-information` is deprecated and should not be used, as SPI purposes are now displayed in the `purposes` screen.
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
   *  @deprecated use {@link #setCurrentUserStatus()} instead.
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
   *  Set user's choices for purposes and vendors for the current regulation.
   *  @param currentUserStatus: instance of CurrentUserStatus containing the user's choices.
   *  @returns: **true** if user status has been updated, **false** otherwise.
   */
  setCurrentUserStatus: (
    currentUserStatus: CurrentUserStatus
  ): Promise<boolean> => {
    const jsonString = JSON.stringify(currentUserStatus);
    return RNDidomi.setCurrentUserStatus(jsonString)
  },

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
   *  @deprecated use {@link #setCurrentUserStatus()} instead.
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
   *  @deprecated use {@link #shouldUserStatusBeCollected()} instead.
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

  /**
   * Creates a `CurrentUserStatusTransaction` object.
   * This object provides mechanisms to stage updates to the user status regarding purposes and vendors, allowing for batch operations. 
   * Updates made through its methods are queued and applied simultaneously to the user status only once the commit method of the returned object is called.
   * @returns A new `CurrentUserStatusTransaction` object.
   */
  openCurrentUserStatusTransaction: (): CurrentUserStatusTransaction => createCurrentUserStatusTransaction(RNDidomi.commitCurrentUserStatusTransaction),
};
