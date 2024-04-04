import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Getter from './GetterCall';
import { allPurposeIds } from './constants/constants';

// UI element used to contain buttons related to tests that involve `CurrentUserStatusTransaction`.
export default function CurrentUserStatusTransactions() {

  return (
    <View style={styles.container}>
      {/* Single purposes */}
      <Getter
        name="enablePurpose[cookies]-transaction"
        call={async (name) => {
          const entity = "cookies";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enablePurpose(entity);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.purposes[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      <Getter
        name="disablePurpose[cookies]-transaction"
        call={async (name) => {
          const entity = "cookies";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disablePurpose(entity);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.purposes[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      {/* Multiple purposes */}
      <Getter
        name="enablePurposes[cookies]-transaction"
        call={async (name) => {
          const entity = "cookies";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enablePurposes([entity]);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.purposes[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      <Getter
        name="disablePurposes[cookies]-transaction"
        call={async (name) => {
          const entity = "cookies";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disablePurposes([entity]);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.purposes[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      {/* Single vendors */}
      <Getter
        name="enableVendor[ipromote]-transaction"
        call={async (name) => {
          const entity = "ipromote";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enableVendor(entity);
          transaction.enablePurposes(allPurposeIds);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.vendors[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      <Getter
        name="disableVendor[ipromote]-transaction"
        call={async (name) => {
          const entity = "ipromote";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disableVendor(entity);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.vendors[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      {/* Multiple vendors */}
      <Getter
        name="enableVendors[ipromote]-transaction"
        call={async (name) => {
          const entity = "ipromote";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.enableVendors([entity]);
          transaction.enablePurposes(allPurposeIds);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.vendors[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
      />

      <Getter
        name="disableVendors[ipromote]-transaction"
        call={async (name) => {
          const entity = "ipromote";
          const transaction = Didomi.openCurrentUserStatusTransaction();
          transaction.disableVendors([entity]);
          const isUpdated = await transaction.commit();
          const currentUserStatus = await Didomi.getCurrentUserStatus();
          const isEnabled = currentUserStatus.vendors[entity].enabled;
          return `${name}-updated-${isUpdated}-enabled-${isEnabled}`;
        }}
        test={() => true}
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
