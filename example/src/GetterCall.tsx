import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';

interface GetterCallProps {
  name: string;
  call: () => any;
  test: (result: any) => boolean;
}

export default function Getter(props: GetterCallProps) {
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
      />
      {called && (
        <Text style={styles.result}>{props.test(result) ? 'OK' : 'KO'}</Text>
      )}
      {result && <Text style={styles.result}>{JSON.stringify(result)}</Text>}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    marginVertical: 5,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'row',
  },
  result: {
    marginLeft: 10,
  },
});
