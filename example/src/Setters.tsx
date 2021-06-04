import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from 'react-native-didomi';
import Setter from './SetterCall';

export default function Getters() {
  return (
    <View style={styles.container}>
      <Setter
        name="setUserConsentStatus"
        call={async () => {
          return await Didomi.setUserConsentStatus(["analytics", "advertising_personalization"], [], [], [], [], [], [], []);
        }}
        test={() => {
          return true;
        }}
      />

      {/*
      setUserConsentStatus
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
