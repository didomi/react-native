import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Getter from './GetterCall';

export default function Getters() {
  return (
    <View style={styles.container}>
      <Getter
        name="getPurpose('cookies')"
        call={async () => {
          return await Didomi.getPurpose("cookies");
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getVendor('google')"
        call={async () => {
          return await Didomi.getVendor("google");
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
        name="getCurrentUserStatus"
        call={async () => {
          return await Didomi.getCurrentUserStatus();
        }}
        test={() => {
          return true;
        }}
      />

      <Getter
        name="getApplicableRegulation"
        call={async () => {
          return await Didomi.getApplicableRegulation();
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
