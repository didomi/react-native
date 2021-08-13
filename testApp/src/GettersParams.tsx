import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import GetterParams from './GetterParamsCall';

export default function GettersParams() {
  return (
    <View style={styles.container}>
      <GetterParams
        name="getPurpose [ID = 'analytics']"
        call={async () => {
          return await Didomi.getPurpose('analytics');
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
        name="getUserConsentStatusForPurpose [ID = 'analytics']"
        call={async () => {
          return await Didomi.getUserConsentStatusForPurpose('analytics');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForVendor [ID = '1']"
        call={async () => {
          return await Didomi.getUserConsentStatusForVendor('1');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserStatusForVendor [ID = '1']"
        call={async () => {
          return await Didomi.getUserStatusForVendor('1');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForVendorAndRequiredPurposes [ID = '1']"
        call={async () => {
          return await Didomi.getUserConsentStatusForVendorAndRequiredPurposes(
            '1'
          );
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForPurpose [ID = 'analytics']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestStatusForPurpose(
            'analytics'
          );
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForVendor [ID = '1']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestForVendor('1');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForVendorAndRequiredPurposes [ID = '1']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestStatusForVendorAndRequiredPurposes(
            '1'
          );
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
