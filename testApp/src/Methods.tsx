import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi, VendorStatus } from '@didomi/react-native';
import MethodCall from './MethodCall';

interface MethodsProps {
  onEventReceived: (eventName: string) => any;
  registerAllListeners: () => any;
}

export default function Methods(props: MethodsProps) {
  return (
    <View style={styles.container}>
      <MethodCall
        name="reset"
        call={() => {
          Didomi.reset();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="setupUI"
        call={() => {
          Didomi.setupUI();
        }}
        test={() => {
          return true;
        }}
      />
      <MethodCall
        name="setLogLevel"
        call={() => {
          Didomi.setLogLevel(2);
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="updateSelectedLanguage"
        call={() => {
          Didomi.updateSelectedLanguage('fr');
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="showNotice"
        call={() => {
          Didomi.showNotice();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="hideNotice"
        call={() => {
          Didomi.hideNotice();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="hidePreferences"
        call={() => {
          Didomi.hidePreferences();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="showPreferences Purposes"
        call={() => {
          Didomi.showPreferences();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="showPreferences Vendors"
        call={() => {
          Didomi.showPreferences('vendors');
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="Listen ipromote Vendor status"
        call={()=> {
          Didomi.removeAllEventListeners();
          Didomi.addVendorStatusListener('ipromote', (vendorStatus: VendorStatus) => {
            props.onEventReceived("Vendor status ipromote -> " + vendorStatus.enabled);
            console.log("event received: Vendor status ipromote");
          });
        }}
        test={() => {
          return true;
        }} />

      <MethodCall
        name="Restore event listeners"
        call={() => {
          Didomi.removeVendorStatusListener('ipromote');
          props.registerAllListeners();
        }}
        test={() => {
          return true;
        }} />

      {/*
setUser
setUserConsentStatus
setUserDisagreeToAll
setUserStatus
setUserStatusSets
updateSelectedLanguage
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
