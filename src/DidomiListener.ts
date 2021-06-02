import { NativeModules, NativeEventEmitter } from 'react-native';
import { DidomiEventType } from './DidomiTypes';

const { Didomi: RNDidomi } = NativeModules;

export const DidomiListener = {
  listeners: new Map(),
  eventEmitter: new NativeEventEmitter(RNDidomi),

  init: () => {
    // Register all native event listeners
    Object.values(DidomiEventType).forEach((eventTypeValue) => {
      DidomiListener.eventEmitter.addListener(eventTypeValue, (_event: any) => {
        let events = DidomiListener.listeners.get(eventTypeValue);
        if (events) {
          events.forEach((el: any) => {
            el(_event);
          });
        }
      });
    });
  },

  reset: () => {
    // Reset listeners
    DidomiListener.listeners = new Map();
  },

  addEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => {
    let events = DidomiListener.listeners.get(eventType);
    if (!events) {
      events = [];
      DidomiListener.listeners.set(eventType, events);
    }
    events.push(callback);
  },

  removeEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => {
    let events = DidomiListener.listeners.get(eventType);
    if (events) {
      var index = events.indexOf(callback);
      if (index !== -1) {
        events.splice(index, 1);
      }
    }
  },
};
