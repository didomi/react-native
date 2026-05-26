import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  initialize(
    userAgentName: string,
    userAgentVersion: string,
    apiKey: string,
    localConfigurationPath: string | null,
    remoteConfigurationURL: string | null,
    providerId: string | null,
    disableDidomiRemoteConfig: boolean,
    languageCode: string | null,
    noticeId: string | null,
    androidTvNoticeId: string | null,
    androidTvEnabled: boolean,
    countryCode: string | null,
    regionCode: string | null
  ): Promise<void>;

  initializeWithParameters(
    userAgentName: string,
    userAgentVersion: string,
    parameters: string
  ): Promise<void>;

  onReady(): Promise<void>;
  onError(): Promise<void>;
  isReady(): Promise<boolean>;
  isError(): Promise<boolean>;
  reset(): Promise<void>;
  clearUser(): Promise<void>;

  setupUI(): Promise<void>;
  forceShowNotice(): Promise<void>;
  showNotice(): Promise<void>;
  hideNotice(): Promise<void>;
  isNoticeVisible(): Promise<boolean>;
  showPreferences(view: string | null): Promise<void>;
  hidePreferences(): Promise<void>;
  isPreferencesVisible(): Promise<boolean>;

  isConsentRequired(): Promise<boolean>;
  shouldUserStatusBeCollected(): Promise<boolean>;
  shouldConsentBeCollected(): Promise<boolean>;
  isUserConsentStatusPartial(): Promise<boolean>;
  isUserStatusPartial(): Promise<boolean>;
  isUserLegitimateInterestStatusPartial(): Promise<boolean>;

  getCurrentUserStatus(): Promise<Object>;
  setCurrentUserStatus(currentUserStatusAsString: string): Promise<boolean>;
  getUserStatus(): Promise<Object>;
  setUserStatus(
    purposesConsentStatus: boolean,
    purposesLIStatus: boolean,
    vendorsConsentStatus: boolean,
    vendorsLIStatus: boolean
  ): Promise<boolean>;
  setUserStatusSets(
    enabledConsentPurposeIds: Array<string>,
    disabledConsentPurposeIds: Array<string>,
    enabledLIPurposeIds: Array<string>,
    disabledLIPurposeIds: Array<string>,
    enabledConsentVendorIds: Array<string>,
    disabledConsentVendorIds: Array<string>,
    enabledLIVendorIds: Array<string>,
    disabledLIVendorIds: Array<string>
  ): Promise<boolean>;
  setUserConsentStatus(
    enabledPurposeIds: Array<string>,
    disabledPurposeIds: Array<string>,
    enabledVendorIds: Array<string>,
    disabledVendorIds: Array<string>
  ): Promise<boolean>;
  setUserAgreeToAll(): Promise<boolean>;
  setUserDisagreeToAll(): Promise<boolean>;

  getApplicableRegulation(): Promise<string>;
  getPurpose(purposeId: string): Promise<Object>;
  getRequiredPurposes(): Promise<Object>;
  getRequiredPurposeIds(): Promise<Array<string>>;
  getVendor(vendorId: string): Promise<Object>;
  getRequiredVendors(): Promise<Object>;
  getRequiredVendorIds(): Promise<Array<string>>;
  getTotalVendorCount(): Promise<number>;
  getIabVendorCount(): Promise<number>;
  getNonIabVendorCount(): Promise<number>;

  getText(key: string): Promise<Object>;
  getTranslatedText(key: string): Promise<string>;
  getJavaScriptForWebView(extra: string | null): Promise<string>;
  getQueryStringForWebView(): Promise<string>;
  updateSelectedLanguage(languageCode: string): Promise<void>;

  setLogLevel(level: number): Promise<void>;

  setUser(id: string): Promise<void>;
  setUserAndSetupUI(id: string): Promise<void>;

  setUserWithHashAuth(
    id: string,
    algorithm: string,
    secretId: string,
    digest: string,
    salt: string | null
  ): Promise<void>;
  setUserWithHashAuthAndSetupUI(
    id: string,
    algorithm: string,
    secretId: string,
    digest: string,
    salt: string | null
  ): Promise<void>;
  setUserWithHashAuthWithExpiration(
    id: string,
    algorithm: string,
    secretId: string,
    digest: string,
    salt: string | null,
    expiration: number
  ): Promise<void>;
  setUserWithHashAuthWithExpirationAndSetupUI(
    id: string,
    algorithm: string,
    secretId: string,
    digest: string,
    salt: string | null,
    expiration: number
  ): Promise<void>;

  setUserWithEncryptionAuth(
    id: string,
    algorithm: string,
    secretId: string,
    initializationVector: string
  ): Promise<void>;
  setUserWithEncryptionAuthAndSetupUI(
    id: string,
    algorithm: string,
    secretId: string,
    initializationVector: string
  ): Promise<void>;
  setUserWithEncryptionAuthWithExpiration(
    id: string,
    algorithm: string,
    secretId: string,
    initializationVector: string,
    expiration: number
  ): Promise<void>;
  setUserWithEncryptionAuthWithExpirationAndSetupUI(
    id: string,
    algorithm: string,
    secretId: string,
    initializationVector: string,
    expiration: number
  ): Promise<void>;

  setUserWithAuthParams(
    jsonUserAuthParams: string,
    jsonSynchronizedUsers: string | null
  ): Promise<void>;
  setUserWithAuthParamsAndSetupUI(
    jsonUserAuthParams: string,
    jsonSynchronizedUsers: string | null
  ): Promise<void>;

  setUserWithParameters(jsonParameters: string): Promise<void>;
  setUserWithParametersAndSetupUI(jsonParameters: string): Promise<void>;

  listenToVendorStatus(vendorId: string): Promise<void>;
  stopListeningToVendorStatus(vendorId: string): Promise<void>;

  commitCurrentUserStatusTransaction(
    enabledPurposes: Array<string>,
    disabledPurposes: Array<string>,
    enabledVendors: Array<string>,
    disabledVendors: Array<string>
  ): Promise<boolean>;

  syncAcknowledged(callbackIndex: number): Promise<boolean>;
  removeSyncAcknowledgedCallback(callbackIndex: number): Promise<void>;

  addListener(eventName: string): void;
  removeListeners(count: number): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Didomi');
