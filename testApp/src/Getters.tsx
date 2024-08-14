import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Getter from './GetterCall';

export default function Getters() {
  return (
    <View style={styles.container}>
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
        name="isUserStatusPartial"
        call={async () => {
          return await Didomi.isUserStatusPartial();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="shouldUserStatusBeCollected"
        call={async () => {
          return await Didomi.shouldUserStatusBeCollected();
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

      <Getter
        name="getUserStatus regulation"
        call={async () => {
          return (await Didomi.getUserStatus()).regulation;
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getUserStatus purposes"
        call={async () => {
          return (await Didomi.getUserStatus()).purposes;
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getUserStatus vendors"
        call={async () => {
          return (await Didomi.getUserStatus()).vendors;
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getUserStatus vendors global_consent"
        call={async () => {
          return (await Didomi.getUserStatus()).vendors.global_consent;
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getCurrentUserStatus"
        call={async () => {
          return await Didomi.getCurrentUserStatus();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="Get vendor count"
        call={async () => {
          return "Total: " + (await Didomi.getTotalVendorCount())
            + " - IAB: " + (await Didomi.getIabVendorCount())
            + " - Non-IAB: " + (await Didomi.getNonIabVendorCount());
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
  isUserStatusPartial
  shouldUserStatusBeCollected
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
