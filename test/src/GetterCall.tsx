import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import { convertResultToString } from './helpers/ResultHelper';

interface GetterCallProps {
  name: string;
  call: (name: string) => any;
  test: (result: any) => boolean;
}

export default function Getter(props: GetterCallProps) {
  const [called, setCalled] = useState(false);
  const [result, setResult] = useState(null);

  return (
    <View style={styles.container}>
      <Button
        onPress={async () => {
          try {
            setResult(await props.call(props.name));
          } catch (error) {
            setResult(`Error: ${error instanceof Error ? error.message : String(error)}` as any);
          }
          setCalled(true);
        }}
        title={props.name}
        testID={props.name}
      />
      {called && (typeof(result) == "boolean" || result != '') ? (
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
