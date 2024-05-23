import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import MethodCall from './MethodCall';

interface InitializeProps {
  updateSdkState: (state: string) => any;
}

export default function InitializeMethods(props: InitializeProps) {

  const callInitialize = (
    countryCode?: string, 
    regionCode?: string, 
    noticeId: string = "XWhEXzb9"
  ) => {
    props.updateSdkState("REINIT");
    Didomi.initialize(
      "9bf8a7e4-db9a-4ff2-a45c-ab7d2b6eadba",
      undefined,
      undefined,
      undefined,
      false,
      undefined,
      noticeId,
      undefined,
      false,
      countryCode,
      regionCode
    )
    Didomi.onReady().then (() => {
      props.updateSdkState("READY")
    });
  };

  return (
    <View style={styles.container}>
      <MethodCall
        name="Initialize FR"
        call={async() => {
          callInitialize("FR");
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="Initialize US CA"
        call={async() => {
          callInitialize("US", "CA");
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="Initialize default notice"
        call={async() => {
          callInitialize(
            undefined, 
            undefined,
            "Ar7NPQ72"
          );
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
