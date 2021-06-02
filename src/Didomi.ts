import { NativeModules } from 'react-native';
import { DidomiListener } from './DidomiListener';
import { DidomiEventType } from './DidomiTypes';

const { Didomi: RNDidomi } = NativeModules;

export const Didomi = {
  initialize: (
    apiKey: string,
    localConfigurationPath: string,
    remoteConfigurationUrl: string,
    providerId: string,
    disableDidomiRemoteConfig: boolean
  ) => {
    // Init listener
    DidomiListener.init();

    // Trigger native SDK init
    RNDidomi.initialize(
      apiKey,
      localConfigurationPath,
      remoteConfigurationUrl,
      providerId,
      disableDidomiRemoteConfig
    );
  },
  setupUI: (): Promise<void> => RNDidomi.setupUI(),
  setLogLevel: (level: number): Promise<number> => RNDidomi.setLogLevel(level),

  addEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => DidomiListener.addEventListener(eventType, callback),

  removeEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => DidomiListener.removeEventListener(eventType, callback),

  removeAllEventListeners: () => DidomiListener.reset(),

  getDisabledPurposes: (): Promise<any[]> => RNDidomi.getDisabledPurposes(),

  getDisabledPurposeIds: (): Promise<any[]> => RNDidomi.getDisabledPurposeIds(),

  getDisabledVendors: (): Promise<any[]> => RNDidomi.getDisabledVendors(),

  getDisabledVendorIds: (): Promise<any[]> => RNDidomi.getDisabledVendorIds(),

  getEnabledPurposes: (): Promise<any[]> => RNDidomi.getEnabledPurposes(),

  getEnabledPurposeIds: (): Promise<any[]> => RNDidomi.getEnabledPurposeIds(),

  getEnabledVendors: (): Promise<any[]> => RNDidomi.getEnabledVendors(),

  getEnabledVendorIds: (): Promise<any[]> => RNDidomi.getEnabledVendorIds(),

  getJavaScriptForWebView: (): Promise<string> =>
    RNDidomi.getJavaScriptForWebView(),

  getQueryStringForWebView: (): Promise<string> =>
    RNDidomi.getQueryStringForWebView(),

  getPurpose: (purposeId: string): Promise<any[]> =>
    RNDidomi.getPurpose(purposeId),

  getRequiredPurposes: (): Promise<any[]> => RNDidomi.getRequiredPurposes(),

  getRequiredPurposeIds: (): Promise<any[]> => RNDidomi.getRequiredPurposeIds(),

  getRequiredVendors: (): Promise<any[]> => RNDidomi.getRequiredVendors(),

  getRequiredVendorIds: (): Promise<any[]> => RNDidomi.getRequiredVendorIds(),

  getText: (key: string): Promise<any[]> => RNDidomi.getText(key),

  getTranslatedText: (key: string): Promise<string> =>
    RNDidomi.getTranslatedText(key),

  getUserConsentStatusForPurpose: (purposeId: string): Promise<boolean> =>
    RNDidomi.getUserConsentStatusForPurpose(purposeId),

  getUserConsentStatusForVendor: (vendorId: string): Promise<boolean> =>
    RNDidomi.getUserConsentStatusForVendor(vendorId),

  getUserConsentStatusForVendorAndRequiredPurposes: (
    vendorId: string
  ): Promise<boolean> =>
    RNDidomi.getUserConsentStatusForVendorAndRequiredPurposes(vendorId),

  getUserLegitimateInterestStatusForPurpose: (
    purposeId: string
  ): Promise<boolean> =>
    RNDidomi.getUserLegitimateInterestStatusForPurpose(purposeId),

  getUserLegitimateInterestForVendor: (vendorId: string): Promise<boolean> =>
    RNDidomi.getUserLegitimateInterestForVendor(vendorId),

  getUserLegitimateInterestStatusForVendorAndRequiredPurposes: (
    vendorId: string
  ): boolean =>
    RNDidomi.getUserLegitimateInterestStatusForVendorAndRequiredPurposes(
      vendorId
    ),

  getUserStatusForVendor: (vendorId: string): Promise<boolean> =>
    RNDidomi.getUserStatusForVendor(vendorId),

  getVendor: (vendorId: string): Promise<any[]> => RNDidomi.getVendor(vendorId),

  hideNotice: (): Promise<void> => RNDidomi.hideNotice(),

  hidePreferences: (): Promise<void> => RNDidomi.hidePreferences(),

  isConsentRequired: (): Promise<boolean> => RNDidomi.isConsentRequired(),

  isUserConsentStatusPartial: (): Promise<boolean> =>
    RNDidomi.isUserConsentStatusPartial(),
  isNoticeVisible: (): Promise<boolean> => RNDidomi.isNoticeVisible(),

  isPreferencesVisible: (): Promise<boolean> => RNDidomi.isPreferencesVisible(),

  isError: (): Promise<boolean> => RNDidomi.isError(),

  isReady: (): Promise<boolean> => RNDidomi.isReady(),

  setUser: (
    organizationUserId: string,
    organizationUserIdAuthAlgorithm: string,
    organizationUserIdAuthSid: string,
    organizationUserIdAuthSalt: string,
    organizationUserIdAuthDigest: string
  ): Promise<void> =>
    RNDidomi.setUser(
      organizationUserId,
      organizationUserIdAuthAlgorithm,
      organizationUserIdAuthSid,
      organizationUserIdAuthSalt,
      organizationUserIdAuthDigest
    ),

  showNotice: (): Promise<void> => RNDidomi.showNotice(),

  showPreferences: (view: string): Promise<void> =>
    RNDidomi.showPreferences(view),

  reset: (): Promise<void> => RNDidomi.reset(),

  setUserAgreeToAll: (): Promise<boolean> => RNDidomi.setUserAgreeToAll(),

  setUserConsentStatus: (
    enabledPurposeIds: string[],
    disabledPurposeIds: string[],
    enabledLegitimatePurposeIds: string[],
    disabledLegitimatePurposeIds: string[],
    enabledVendorIds: string[],
    disabledVendorIds: string[],
    enabledLegIntVendorIds: string[],
    disabledLegIntVendorIds: string[]
  ): Promise<boolean> =>
    RNDidomi.setUserConsentStatus(
      enabledPurposeIds,
      disabledPurposeIds,
      enabledLegitimatePurposeIds,
      disabledLegitimatePurposeIds,
      enabledVendorIds,
      disabledVendorIds,
      enabledLegIntVendorIds,
      disabledLegIntVendorIds
    ),

  setUserDisagreeToAll: (): Promise<boolean> => RNDidomi.setUserDisagreeToAll(),

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

  setUserStatusSets: (
    enabledConsentPurposeIds: string[],
    disabledConsentPurposeIds: string[],
    enabledLIPurposeIds: string[],
    disabledLIPurposeIds: string[],
    enabledConsentVendorIds: string[],
    disabledConsentVendorIds: string[],
    enabledLIVendorIds: string[],
    disabledLIVendorIds: string[],
    sendAPIEvent: boolean
  ): Promise<boolean> =>
    RNDidomi.setUserStatusSets(
      enabledConsentPurposeIds,
      disabledConsentPurposeIds,
      enabledLIPurposeIds,
      disabledLIPurposeIds,
      enabledConsentVendorIds,
      disabledConsentVendorIds,
      enabledLIVendorIds,
      disabledLIVendorIds,
      sendAPIEvent
    ),

  shouldConsentBeCollected: (): Promise<boolean> =>
    RNDidomi.shouldConsentBeCollected(),

  updateSelectedLanguage: (languageCode: string): Promise<void> =>
    RNDidomi.updateSelectedLanguage(languageCode),
};
