import React, { useState } from 'react';

import { SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import { Didomi, DidomiEventType } from '@didomi/react-native';
import Methods from './Methods';
import Getters from './Getters';
import Setters from './Setters';
import { TestEvent } from './Types';

function App() {
  const MAX_EVENTS_DISPLAYED = 3;

  const [receivedEvents, setReceivedEvents] = useState<TestEvent[]>([]);

  function pushReceivedEvent(event: TestEvent) {
    receivedEvents.forEach((el) => console.log("A -- "+el.name) );
    receivedEvents.push(event);
    if (receivedEvents.length > MAX_EVENTS_DISPLAYED) {
      receivedEvents.shift();
    }
    receivedEvents.forEach((el) => console.log("B -- "+el.name) );
    setReceivedEvents([
      ...receivedEvents
    ]);
  }

  const registerListener = (eventType: DidomiEventType) => {
    Didomi.addEventListener(eventType, (data: any) => {
      pushReceivedEvent({ name: eventType, data });
      console.log('event received: ' + eventType);
    });
  };

  React.useEffect(() => {
    Didomi.removeAllEventListeners();

    registerListener(DidomiEventType.CONSENT_CHANGED);
    registerListener(DidomiEventType.ERROR);
    registerListener(DidomiEventType.READY);
    registerListener(DidomiEventType.SYNC_DONE);
    registerListener(DidomiEventType.HIDE_NOTICE);
    registerListener(DidomiEventType.SHOW_NOTICE);
    registerListener(DidomiEventType.NOTICE_CLICK_AGREE);
    registerListener(DidomiEventType.NOTICE_CLICK_DISAGREE);
    registerListener(DidomiEventType.NOTICE_CLICK_MORE_INFO);
    registerListener(DidomiEventType.NOTICE_CLICK_PRIVACY_POLICY);
    registerListener(DidomiEventType.NOTICE_CLICK_VIEW_VENDORS);
    registerListener(DidomiEventType.HIDE_PREFERENCES);
    registerListener(DidomiEventType.SHOW_PREFERENCES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_AGREE_TO_ALL);
    registerListener(DidomiEventType.PREFERENCES_CLICK_AGREE_TO_ALL_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_AGREE_TO_ALL_VENDORS);
    registerListener(DidomiEventType.PREFERENCES_CLICK_CATEGORY_AGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_CATEGORY_DISAGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_DISAGREE_TO_ALL);
    registerListener(DidomiEventType.PREFERENCES_CLICK_DISAGREE_TO_ALL_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_DISAGREE_TO_ALL_VENDORS);
    registerListener(DidomiEventType.PREFERENCES_CLICK_PURPOSE_AGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_PURPOSE_DISAGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_RESET_ALL_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_SAVE_CHOICES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VENDOR_AGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VENDOR_DISAGREE);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VENDOR_SAVE_CHOICES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VIEW_PURPOSES);
    registerListener(DidomiEventType.PREFERENCES_CLICK_VIEW_VENDORS);
    registerListener(DidomiEventType.LANGUAGE_UPDATED);
    registerListener(DidomiEventType.LANGUAGE_UPDATE_FAILED);

    Didomi.onReady().then(() => {
      console.log('ready');
    });

    Didomi.onError().then(() => {
      console.log('error');
    });

    /*Didomi.addEventListener(DidomiEventType.READY, (data: any) => {
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
    });*/

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function displayEvents() {
    return receivedEvents.map((event)=>{
        return(
            "\n> " + event.name
              + (event.data ? "\n  " + JSON.stringify(event.data) : "")
        )
    })
  }
  
  return (
    <SafeAreaView style={{ flex: 1 }}>
      <View style={styles.title}>
        <Text style={styles.title}>
          LAST RECEIVED EVENTS: 
          { displayEvents() }
          </Text>
      </View>
      <ScrollView>
        <View style={styles.container}>
          <Text style={styles.title}>METHODS</Text>
          <Methods />
          <Text style={styles.title}>GETTERS</Text>
          <Getters />
          <Text style={styles.title}>SETTERS</Text>
          <Setters />
        </View>
      </ScrollView>
    </SafeAreaView>
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
    marginBottom: 5,
    alignItems: 'center',
  },
});

export default App;
