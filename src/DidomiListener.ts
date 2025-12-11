import { NativeModules, NativeEventEmitter } from 'react-native';
import { DidomiEventType, VendorStatus } from './DidomiTypes';

const { Didomi: RNDidomi } = NativeModules;

enum InternalEventType {
  READY_CALLBACK = 'on_ready_callback',
  ERROR_CALLBACK = 'on_error_callback',
  VENDOR_STATUS_CHANGE_PREFIX = 'on_vendor_status_change_',
}

export const DidomiListener = {
  listeners: new Map(),
  vendorStatusListeners: new Map(),
  eventEmitter: new NativeEventEmitter(RNDidomi),

  init: () => {
    // Register all native event listeners
    Object.values(DidomiEventType).forEach((eventTypeValue) => {
      // Remove previous listeners, as init() can be called several times in some configs
      DidomiListener.eventEmitter.removeAllListeners(eventTypeValue);
      DidomiListener.eventEmitter.addListener(eventTypeValue, (_event: any) => {
        let events = DidomiListener.listeners.get(eventTypeValue);
        if (events && events.length > 0) {
          if (eventTypeValue == "on_sync_ready") {
            _event.syncAcknowledged = (): Promise<boolean> => { 
              return RNDidomi.syncAcknowledged(_event.syncAcknowledgedIndex)
            }
          }
          events.forEach((el: any) => {
            try {
              el(_event);
            } catch (err) {
              console.error('Didomi event listener error:', err);
            }
          });
        } else {  // No listener for this event type
          if (eventTypeValue == "on_sync_ready") {  
            // We can remove syncAcknowledgedCallback as it will never be called
            RNDidomi.removeSyncAcknowledgedCallback(_event.syncAcknowledgedIndex);
          }
        }
      });
    });
  },

  reset: () => {
    // Reset listeners
    DidomiListener.listeners = new Map();
    // Clear vendor status listeners and their native event emitters
    DidomiListener.vendorStatusListeners.forEach((_, vendorId) => {
      DidomiListener.eventEmitter.removeAllListeners(
        InternalEventType.VENDOR_STATUS_CHANGE_PREFIX + vendorId
      );
    });
    DidomiListener.vendorStatusListeners = new Map();
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
    return new Promise<void>((resolve, reject) => {
      const timeoutId = setTimeout(() => {
        DidomiListener.eventEmitter.removeAllListeners(InternalEventType.READY_CALLBACK);
        reject(new Error('Didomi SDK ready timeout'));
      }, 30000);

      const listener = (_event: any) => {
        clearTimeout(timeoutId);
        resolve();
        DidomiListener.eventEmitter.removeAllListeners(InternalEventType.READY_CALLBACK);
      };
      DidomiListener.eventEmitter.addListener(
        InternalEventType.READY_CALLBACK,
        listener
      );
    });
  },

  setOnErrorListener: (): Promise<void> => {
    return new Promise<void>((resolve, reject) => {
      const timeoutId = setTimeout(() => {
        DidomiListener.eventEmitter.removeAllListeners(InternalEventType.ERROR_CALLBACK);
        reject(new Error('Didomi SDK error listener timeout'));
      }, 30000);

      const listener = (_event: any) => {
        clearTimeout(timeoutId);
        resolve(_event);
        DidomiListener.eventEmitter.removeAllListeners(InternalEventType.ERROR_CALLBACK);
      };
      DidomiListener.eventEmitter.addListener(
        InternalEventType.ERROR_CALLBACK,
        listener
      );
    });
  },

  addVendorStatusListener: (
    vendorId: string,
    callback: (vendorStatus: VendorStatus) => void
  ) => {
    let events = DidomiListener.vendorStatusListeners.get(vendorId);
    if (!events) {
      events = [];
      DidomiListener.vendorStatusListeners.set(vendorId, events);

      DidomiListener.eventEmitter.addListener(InternalEventType.VENDOR_STATUS_CHANGE_PREFIX + vendorId, (_event: any) => {
        let events = DidomiListener.vendorStatusListeners.get(vendorId);
        if (events) {
          events.forEach((el: any) => {
            try {
              el(_event);
            } catch (err) {
              console.error('Didomi vendor status listener error:', err);
            }
          });
        }
      });
    }
    events.push(callback);
  },

  removeVendorStatusListener: (vendorId: string) => {
    if (DidomiListener.vendorStatusListeners.has(vendorId)) {
      DidomiListener.eventEmitter.removeAllListeners(InternalEventType.VENDOR_STATUS_CHANGE_PREFIX + vendorId);
      DidomiListener.vendorStatusListeners.delete(vendorId);
    }
  },
};
