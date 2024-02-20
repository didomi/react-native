import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import GetterParams from './GetterParamsCall';

export default function GettersParams() {
  return (
    <View style={styles.container}>
      <GetterParams
        name="getPurpose [ID = 'cookies']"
        call={async () => {
          return await Didomi.getPurpose('cookies');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getVendor [ID = '217']"
        call={async () => {
          return await Didomi.getVendor('217');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getVendor [ID = '217'] urls[0]"
        call={async () => {
          return (await Didomi.getVendor('217')).urls[0];
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getText [Key = '0']"
        call={async () => {
          return await Didomi.getText('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getTranslatedText [Key = '0']"
        call={async () => {
          return await Didomi.getTranslatedText('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getJavaScriptForWebViewWithExtra"
        call={async () => {
          return await Didomi.getJavaScriptForWebView("console.log('extra JS!');");
        }}
        test={() => {
          return true;
        }}
      />
      {/*
  getUserLegitimateInterestForVendor
  getUserLegitimateInterestStatusForVendorAndRequiredPurposes
  getUserStatusForVendor
  getVendor
  getRequiredPurposeIds
  getRequiredVendors
  getRequiredVendorIds
  getUserConsentStatusForPurpose
  getUserConsentStatusForVendor
  getUserConsentStatusForVendorAndRequiredPurposes
  getUserLegitimateInterestStatusForPurpose
  getUserLegitimateInterestForVendor
  getUserLegitimateInterestStatusForVendorAndRequiredPurposes
  getUserStatusForVendor
  getVendor
  isConsentRequired
  isUserConsentStatusPartial
  isNoticeVisible
  isPreferencesVisible
  isError
  isReady
  shouldConsentBeCollected
 */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'column',
  },
});
