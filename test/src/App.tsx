import React, { useState } from 'react';

import { SafeAreaView, ScrollView, StyleSheet, Text, View } from 'react-native';
import { Didomi, DidomiEventType } from '@didomi/react-native';
import Methods from './Methods';
import Getters from './Getters';
import GettersParams from './GettersParams';
import Setters from './Setters';
import SetUser from './SetUser';
import CurrentUserStatusTransactionsList from './CurrentUserStatusTransactionsList';
import { TestEvent } from './Types';
import InitializeMethods from './InitializeMethods';

function App() {
  const MAX_EVENTS_DISPLAYED = 3;

  const [receivedEvents, setReceivedEvents] = useState<TestEvent[]>([]);

  const [sdkStatus, setSdkStatus] = useState("NOT_READY");

  function pushReceivedEvent(event: TestEvent) {
    receivedEvents.forEach((el) => console.log("A -- "+el.name) );
    receivedEvents.push(event);
    if (receivedEvents.length > MAX_EVENTS_DISPLAYED) {
      receivedEvents.shift();
    }
    receivedEvents.forEach((el) => console.log("B -- "+el.name) );
    setReceivedEvents([
      ...receivedEvents,
      //event
    ]);
  }

  const registerListener = (eventType: DidomiEventType) => {
    Didomi.addEventListener(eventType, (data: any) => {
      pushReceivedEvent({ name: eventType, data });
      console.log('event received: ' + eventType);
      if (typeof data != "undefined" && data != "") {
        console.log(' -> data : ' + data);
      }
    });
  };

  const registerAllListeners = () => {
    Didomi.removeAllEventListeners();

    registerListener(DidomiEventType.CONSENT_CHANGED);
    registerListener(DidomiEventType.ERROR);
    registerListener(DidomiEventType.HIDE_NOTICE);
    registerListener(DidomiEventType.NOTICE_CLICK_AGREE);
    registerListener(DidomiEventType.NOTICE_CLICK_DISAGREE);
    registerListener(DidomiEventType.NOTICE_CLICK_MORE_INFO);
    registerListener(DidomiEventType.NOTICE_CLICK_PRIVACY_POLICY);
    registerListener(DidomiEventType.NOTICE_CLICK_VIEW_VENDORS);
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
    registerListener(DidomiEventType.READY);
    registerListener(DidomiEventType.SHOW_NOTICE);
    registerListener(DidomiEventType.HIDE_PREFERENCES);
    registerListener(DidomiEventType.SHOW_PREFERENCES);
    // Disabled to allow testing other events. TODO These events could be printed somewhere else if needed
    //registerListener(DidomiEventType.SYNC_DONE);
    //registerListener(DidomiEventType.SYNC_ERROR);
    registerListener(DidomiEventType.LANGUAGE_UPDATED);
    registerListener(DidomiEventType.LANGUAGE_UPDATE_FAILED);
  };

  React.useEffect(() => {
    registerAllListeners();

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

    async function init() {
      await Didomi.initialize(
        '9bf8a7e4-db9a-4ff2-a45c-ab7d2b6eadba',
        undefined,
        undefined,
        undefined,
        false,
        undefined,
        "Ar7NPQ72",
        undefined
      );
      console.log('Finished init');

      Didomi.onReady().then(() => {
        setSdkStatus("READY");
      });
    }
    init();
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
        <Text testID='ready-result' style={styles.title}>
          SDK STATUS: {sdkStatus}
        </Text>
        <Text style={styles.title}>
          LAST RECEIVED EVENTS:
          { displayEvents() }
        </Text>
      </View>
      <ScrollView>
        <View style={styles.container}>
          <Text style={styles.title}>INITIALIZE</Text>
          <InitializeMethods
            updateSdkState={setSdkStatus}
           />
          <Text style={styles.title}>METHODS</Text>
          <Methods
            onEventReceived={(eventName: string) => pushReceivedEvent({ name: eventName }) }
            registerAllListeners={() => registerAllListeners() }
           />
          <Text style={styles.title}>GETTERS</Text>
          <Getters />
          <Text style={styles.title}>GETTERS PARAMS</Text>
          <GettersParams />
          <Text style={styles.title}>SETTERS</Text>
          <Setters />
          <Text style={styles.title}>Current User Status Transactions</Text>
          <CurrentUserStatusTransactionsList />
          <Text style={styles.title}>SET USER</Text>
          <SetUser />
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
