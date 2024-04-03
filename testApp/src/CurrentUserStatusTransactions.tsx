import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Getter from './GetterCall';

// UI element used to contain buttons related to tests that involve `CurrentUserStatusTransaction`.
export default function CurrentUserStatusTransactions() {

  return (
    <View style={styles.container}>
      {/* Single purposes */}
      <Getter
        name="enablePurpose[cookies]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enablePurpose("cookies");
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

      <Getter
        name="disablePurpose[cookies]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disablePurpose("cookies");
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

        {/* Multiple purposes */}
      <Getter
        name="enablePurposes[cookies]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enablePurposes(["cookies"]);
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

      <Getter
        name="disablePurposes[cookies]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disablePurposes(["cookies"]);
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

        {/* Single vendors */}
      <Getter
        name="enableVendor[ipromote]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enableVendor("ipromote");
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

      <Getter
        name="disableVendor[ipromote]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disableVendor("ipromote");
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

      {/* Multiple vendors */}
      <Getter
        name="enableVendors[ipromote]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enableVendors(["ipromote"]);
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />

      <Getter
        name="disableVendors[ipromote]-transaction"
        call={async (name) => {
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disableVendors(["ipromote"]);
          const result = await transaction.commit();
          return name + "-updated-" +result;
        }}
        test={() => {
          return true;
        }} />
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
