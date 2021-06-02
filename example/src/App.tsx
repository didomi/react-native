import React, { useState } from 'react';

import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { Didomi, DidomiEventType } from 'react-native-didomi';
import Methods from './Methods';
import Getters from './Getters';
import { TestEvent } from './Types';

export default function App() {
  const [receivedEvent, setReceivedEvent] = useState<TestEvent>({
    name: 'NONE',
  });

  React.useEffect(() => {
    Didomi.removeAllEventListeners();

    Didomi.addEventListener(DidomiEventType.READY, (data: any) => {
      setReceivedEvent({ name: DidomiEventType.READY, data });
      console.log("I'm ready");
    });

    Didomi.addEventListener(DidomiEventType.SHOW_NOTICE, (data: any) => {
      setReceivedEvent({ name: DidomiEventType.SHOW_NOTICE, data });
      console.log('Show notice');
    });

    Didomi.addEventListener(DidomiEventType.CONSENT_CHANGED, (data: any) => {
      setReceivedEvent({ name: DidomiEventType.CONSENT_CHANGED, data });
      console.log('Consent changed');
    });

    async function init() {
      await Didomi.initialize(
        '465ca0b2-b96f-43b4-a864-f87e18d2fd38',
        '',
        '',
        '',
        true
      );
      console.log('Finished init');
    }
    init();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <ScrollView>
      <View style={styles.container}>
        <Text style={styles.title}>
          LAST RECEIVED EVENT: {receivedEvent.name}
          {receivedEvent.data ? JSON.stringify(receivedEvent.data) : null}
        </Text>
        <Text style={styles.title}>METHODS</Text>
        <Methods />
        <Text style={styles.title}>GETTERS</Text>
        <Getters />
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontWeight: 'bold',
    marginTop: 20,
  },
});
