import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Setter from './SetterCall';

export default function Setters() {
  return (
    <View style={styles.container}>
      <Setter
        name="setUserStatusSets"
        call={async () => {
          return await Didomi.setUserStatusSets(
            ['cookies', 'advertising_personalization', 'analytics'],
            ['ad_delivery', 'content_personalization'],
            [],
            [],
            [],
            [],
            [],
            []
          );
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserAgreeToAll"
        call={async () => {
          return await Didomi.setUserAgreeToAll();
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserDisagreeToAll"
        call={async () => {
          return await Didomi.setUserDisagreeToAll();
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
