import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Getter from './GetterCall';

export default function Getters() {
  return (
    <View style={styles.container}>
      <Getter
        name="getDisabledPurposes"
        call={async () => {
          return await Didomi.getDisabledPurposes();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getDisabledPurposeIds"
        call={async () => {
          return await Didomi.getDisabledPurposeIds();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getDisabledVendors"
        call={async () => {
          return await Didomi.getDisabledVendors();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getDisabledVendorIds"
        call={async () => {
          return await Didomi.getDisabledVendorIds();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getEnabledPurposes"
        call={async () => {
          return await Didomi.getEnabledPurposes();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getEnabledPurposeIds"
        call={async () => {
          return await Didomi.getEnabledPurposeIds();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getEnabledVendors"
        call={async () => {
          return await Didomi.getEnabledVendors();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getEnabledVendorIds"
        call={async () => {
          return await Didomi.getEnabledVendorIds();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getJavaScriptForWebView"
        call={async () => {
          return await Didomi.getJavaScriptForWebView();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getQueryStringForWebView"
        call={async () => {
          return await Didomi.getQueryStringForWebView();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getUserStatus"
        call={async () => {
          return await Didomi.getUserStatus();
        }}
        test={() => {
          return true;
        }}
      />

      {/* 
  getJavaScriptForWebView
  getQueryStringForWebView
  getPurpose
  getRequiredPurposes
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
