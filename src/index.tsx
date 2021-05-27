import { NativeModules, WritableArray, WritableMap } from 'react-native';

type DidomiType = {
  initialize(
    apiKey: string,
    localConfigurationPath: string,
    remoteConfigurationUrl: string,
    providerId: string,
    disableDidomiRemoteConfig: boolean
  ): Promise<number>;

  setupUI(): Promise<number>;
  setLogLevel(level: number): Promise<number>;
  addEventListener(eventKey: string): Promise<number>;
  removeEventListener(eventKey: string): Promise<number>;
  getDisabledPurposes(): Promise<WritableArray>;
  getDisabledPurposeIds(): Promise<WritableArray>;
  getDisabledVendors(): Promise<WritableArray>;
  getDisabledVendorIds(): Promise<WritableArray>;
  getEnabledPurposes(): Promise<WritableArray>;
  getEnabledPurposeIds(): Promise<WritableArray>;
  getEnabledVendors(): Promise<WritableArray>;
  getEnabledVendorIds(): Promise<WritableArray>;
  getJavaScriptForWebView(): Promise<string>;
  getQueryStringForWebView(): Promise<string>;
  getPurpose(purposeId: string): Promise<WritableMap>;
  getRequiredPurposes(): Promise<WritableArray>;
  getRequiredPurposeIds(): Promise<WritableArray>;
  getRequiredVendors(): Promise<WritableArray>;
  getRequiredVendorIds(): Promise<WritableArray>;
  getText(key: string): Promise<WritableMap>;
  getTranslatedText(key: string): Promise<string>;
  getUserConsentStatusForPurpose(purposeId: string): Promise<boolean>;
  getUserConsentStatusForVendor(vendorId: string): Promise<boolean>;
  getUserConsentStatusForVendorAndRequiredPurposes(vendorId: string): Promise<boolean>;
  getUserLegitimateInterestStatusForPurpose(purposeId: string): Promise<boolean>;
  getUserLegitimateInterestForVendor(vendorId: string): Promise<boolean>;
  getUserLegitimateInterestStatusForVendorAndRequiredPurposes(
    vendorId: string
  ): boolean;

  getUserStatusForVendor(vendorId: string): Promise<boolean>;
  getVendor(vendorId: string): Promise<WritableMap>;
  hideNotice(): Promise<number>;
  hidePreferences(): Promise<number>;
  isConsentRequired(): Promise<boolean>;
  isUserConsentStatusPartial(): Promise<boolean>;
  isNoticeVisible(): Promise<boolean>;
  isPreferencesVisible(): Promise<boolean>;
  isError(): Promise<boolean>;
  isReady(): Promise<boolean>;
  onError(): Promise<number>;
  onReady(): Promise<number>;

  setUser(
    organizationUserId: string,
    organizationUserIdAuthAlgorithm: string,
    organizationUserIdAuthSid: string,
    organizationUserIdAuthSalt: string,
    organizationUserIdAuthDigest: string
  ): Promise<number>;

  showNotice(): Promise<number>;
  showPreferences(view: string): Promise<number>;
  reset(): Promise<number>;
  setUserAgreeToAll(): Promise<boolean>;

  setUserConsentStatus(
    enabledPurposeIds: Set<String>,
    disabledPurposeIds: Set<String>,
    enabledLegitimatePurposeIds: Set<String>,
    disabledLegitimatePurposeIds: Set<String>,
    enabledVendorIds: Set<String>,
    disabledVendorIds: Set<String>,
    enabledLegIntVendorIds: Set<String>,
    disabledLegIntVendorIds: Set<String>
  ): Promise<boolean>;

  setUserDisagreeToAll(): Promise<boolean>;

  setUserStatus(
    purposesConsentStatus: boolean,
    purposesLIStatus: boolean,
    vendorsConsentStatus: boolean,
    vendorsLIStatus: boolean
  ): Promise<boolean>;

  setUserStatusSets(
    enabledConsentPurposeIds: Set<string>,
    disabledConsentPurposeIds: Set<string>,
    enabledLIPurposeIds: Set<string>,
    disabledLIPurposeIds: Set<string>,
    enabledConsentVendorIds: Set<string>,
    disabledConsentVendorIds: Set<string>,
    enabledLIVendorIds: Set<string>,
    disabledLIVendorIds: Set<string>,
    sendAPIEvent: boolean
  ): Promise<boolean>;

  shouldConsentBeCollected(): Promise<boolean>;
  updateSelectedLanguage(languageCode: String): Promise<number>;
};

const { Didomi } = NativeModules;

export default Didomi as DidomiType;
