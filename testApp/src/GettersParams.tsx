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
        name="getUserConsentStatusForPurpose [ID = 'cookies']"
        call={async () => {
          return await Didomi.getUserConsentStatusForPurpose('cookies');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForVendor [ID = '755']"
        call={async () => {
          return await Didomi.getUserConsentStatusForVendor('755');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserStatusForVendor [ID = '755']"
        call={async () => {
          return await Didomi.getUserStatusForVendor('755');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForVendorAndRequiredPurposes [ID = '755']"
        call={async () => {
          return await Didomi.getUserConsentStatusForVendorAndRequiredPurposes('755');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForPurpose [ID = 'cookies']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestStatusForPurpose('cookies');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForVendor [ID = '755']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestForVendor('755');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForVendorAndRequiredPurposes [ID = '755']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestStatusForVendorAndRequiredPurposes('755');
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
