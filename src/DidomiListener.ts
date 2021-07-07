import { NativeModules, NativeEventEmitter } from 'react-native';
import { DidomiEventType } from './DidomiTypes';

const { Didomi: RNDidomi } = NativeModules;

enum InternalEventType {
  READY_CALLBACK = 'on_ready_callback',
  ERROR_CALLBACK = 'on_error_callback',
}

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
    Object.values(InternalEventType).forEach((eventTypeValue) => {
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

  addInternalEventListener: (
    eventType: InternalEventType,
    callback: (data: any) => void
  ) => {
    console.log('adding ' + eventType);
    let events = DidomiListener.listeners.get(eventType);
    if (!events) {
      console.log('setting ' + eventType);
      events = [];
      DidomiListener.listeners.set(eventType, events);
    }
    console.log('pushing ' + eventType);
    events.push(callback);
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

  removeInternalEventListener: (
    eventType: InternalEventType,
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
