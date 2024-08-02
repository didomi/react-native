import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';

interface MethodCallProps {
  name: string;
  call: () => any;
  test: () => boolean;
}

export default function MethodCall(props: MethodCallProps) {
  const [called, setCalled] = useState(false);

  return (
    <View style={styles.container}>
      <Button
        onPress={() => {
          props.call();
          setCalled(true);
        }}
        title={props.name}
        testID={props.name}
      />
      {called && (
        <Text style={[styles.result]}>
          {props.name + '-' + (props.test() ? 'OK' : 'KO')}
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
