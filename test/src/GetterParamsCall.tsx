import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import { convertResultToString } from './helpers/ResultHelper';

interface GetterParamsCallProps {
  name: string;
  call: () => any;
  test: (result: any) => boolean;
}

export default function GetterParams(props: GetterParamsCallProps) {
  const [called, setCalled] = useState(false);
  const [result, setResult] = useState(null);

  return (
    <View style={styles.container}>
      <Button
        onPress={async () => {
          setResult(await props.call());
          setCalled(true);
        }}
        title={props.name}
        testID={props.name}
      />
      {called && result != '' ? (
        <Text testID={props.name + '-result'} style={styles.result}>
          {convertResultToString(result)}
        </Text>
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    marginVertical: 5,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'column',
  },
  result: {
    marginLeft: 10,
  },
});
