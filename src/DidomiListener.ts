import { NativeModules, NativeEventEmitter, EmitterSubscription } from 'react-native';
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
      // Remove previous listeners, as init() can be called several times in some configs
      DidomiListener.eventEmitter.removeAllListeners(eventTypeValue);
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

  setOnReadyListener: (): Promise<void> => {
    return new Promise<void>((resolve) => {
      let subscription: EmitterSubscription;
      const listener = (_event: any) => {
        resolve();
        if (subscription) {
          subscription.remove();
        } else if ('removeListener' in DidomiListener.eventEmitter) {
          DidomiListener.eventEmitter.removeListener(
            InternalEventType.READY_CALLBACK,
            listener
          );
        }
      };
      subscription = DidomiListener.eventEmitter.addListener(
        InternalEventType.READY_CALLBACK,
        listener
      );
    });
  },

  setOnErrorListener: (): Promise<void> => {
    return new Promise<void>((resolve) => {
      let subscription: EmitterSubscription;
      const listener = (_event: any) => {
        resolve(_event);
        if (subscription) {
          subscription.remove();
        } else if ('removeListener' in DidomiListener.eventEmitter) {
          DidomiListener.eventEmitter.removeListener(
            InternalEventType.ERROR_CALLBACK,
            listener
          );
        }
      };
      subscription = DidomiListener.eventEmitter.addListener(
        InternalEventType.ERROR_CALLBACK,
        listener
      );
    });
  },
};
