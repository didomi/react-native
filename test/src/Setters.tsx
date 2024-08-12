import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi, PurposeStatus, VendorStatus } from '@didomi/react-native';
import Setter from './SetterCall';
import Getter from './GetterCall';

export default function Setters() {

  return (
    <View style={styles.container}>
      <Getter
        name="setCurrentUserStatus-Fails"
        call={async () => {
          const purposes: { [key: string]: PurposeStatus } = {};
          purposes["cookies"] = { id: "toto", enabled: true };
          
          const vendors: { [key: string]: VendorStatus } = {};
          vendors["ipromote"] = { id: "ipromote", enabled: true };

          return "setCurrentUserStatus-Fails-" + await Didomi.setCurrentUserStatus({
            purposes: purposes,
            vendors: vendors,
            user_id: '',
            created: '',
            updated: '',
            consent_string: '',
            addtl_consent: '',
            didomi_dcs: '',
            regulation: ''
          });
        }}
        test={() => {
          return false;
        }}
      />
      
      <Getter
        name="setCurrentUserStatus-Succeeds"
        call={async () => {
          const purposes: { [key: string]: PurposeStatus } = {};
          purposes["cookies"] = { id: "cookies", enabled: true };
          
          const vendors: { [key: string]: VendorStatus } = {};
          vendors["ipromote"] = { id: "ipromote", enabled: true };

          return "setCurrentUserStatus-Succeeds-" + await Didomi.setCurrentUserStatus({
            purposes: purposes,
            vendors: vendors,
            user_id: '',
            created: '',
            updated: '',
            consent_string: '',
            addtl_consent: '',
            didomi_dcs: '',
            regulation: ''
          });
        }}
        test={() => {
          return true;
        }}
      />
      
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
