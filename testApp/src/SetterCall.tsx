import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';

interface SetterCallProps {
  name: string;
  call: () => any;
  test: (result: any) => boolean;
}

export default function Setter(props: SetterCallProps) {
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
      {called && (
        <Text testID={props.name + '-result'} style={styles.result}>
          {props.name + '-' + (props.test(result) ? 'OK' : 'KO')}
        </Text>
      )}
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
