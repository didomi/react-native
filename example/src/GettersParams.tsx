import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from 'react-native-didomi';
import GetterParams from './GetterParamsCall';

export default function GettersParams() {
  return (
    <View style={styles.container}>
      <GetterParams
        name="getPurpose [ID '0']"
        call={async () => {
          return await Didomi.getPurpose('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getText [Key '0']"
        call={async () => {
          return await Didomi.getText('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getDisabledVendors"
        call={async () => {
          return await Didomi.getDisabledVendors();
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getTranslatedText [Key '0']"
        call={async () => {
          return await Didomi.getTranslatedText('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForPurpose [ID = '0']"
        call={async () => {
          return await Didomi.getUserConsentStatusForPurpose('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForVendor [ID = '0']"
        call={async () => {
          return await Didomi.getUserConsentStatusForVendor('0');
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserConsentStatusForVendorAndRequiredPurposes [ID = '0']"
        call={async () => {
          return await Didomi.getUserConsentStatusForVendorAndRequiredPurposes(
            '0'
          );
        }}
        test={() => {
          return true;
        }}
      />

      <GetterParams
        name="getUserLegitimateInterestStatusForPurpose [ID = '0']"
        call={async () => {
          return await Didomi.getUserLegitimateInterestStatusForPurpose('0');
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
  getText
  getTranslatedText
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
