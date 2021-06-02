import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from 'react-native-didomi';
import MethodCall from './MethodCall';

export default function Methods() {
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
        name="showNotice"
        call={() => {
          Didomi.showNotice();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="showPreferences"
        call={() => {
          Didomi.showPreferences('vendors');
        }}
        test={() => {
          return true;
        }}
      />

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
