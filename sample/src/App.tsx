import React, { useState, useCallback } from 'react';

import { ScrollView, StyleSheet, Text, View } from 'react-native';
import { SafeAreaProvider, SafeAreaView } from 'react-native-safe-area-context';
import { Didomi, DidomiEventType } from '@didomi/react-native';
import Methods from './Methods';
import Getters from './Getters';
import Setters from './Setters';
import { TestEvent } from './Types';
import ErrorBoundary from './ErrorBoundary';

function App() {
  const MAX_EVENTS_DISPLAYED = 3;

  const [receivedEvents, setReceivedEvents] = useState<TestEvent[]>([]);

  const pushReceivedEvent = useCallback((event: TestEvent) => {
    setReceivedEvents(prevEvents => {
      const newEvents = [...prevEvents, event];
      return newEvents.length > MAX_EVENTS_DISPLAYED
        ? newEvents.slice(-MAX_EVENTS_DISPLAYED)
        : newEvents;
    });
  }, [MAX_EVENTS_DISPLAYED]);

  const registerListener = useCallback((eventType: DidomiEventType) => {
    Didomi.addEventListener(eventType, (data: any) => {
      pushReceivedEvent({ name: eventType, data });
      console.log('event received: ' + eventType);
    });
  }, [pushReceivedEvent]);

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

    // Cleanup on unmount
    return () => {
      Didomi.removeAllEventListeners();
    };
  }, [registerListener]);

  function displayEvents() {
    return receivedEvents.map((event)=>{
        return(
            "\n> " + event.name
              + (event.data ? "\n  " + JSON.stringify(event.data) : "")
        )
    })
  }
  
  return (
    <ErrorBoundary>
      <SafeAreaProvider>
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
      </SafeAreaProvider>
    </ErrorBoundary>
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
