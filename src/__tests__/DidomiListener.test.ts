// Mocked NativeEventEmitter
jest.mock('react-native/Libraries/EventEmitter/NativeEventEmitter');

// Mock the spec module so TurboModuleRegistry.getEnforcing doesn't throw in unit tests
jest.mock('../specs/NativeDidomi', () => ({
  __esModule: true,
  default: {
    addListener: jest.fn(),
    removeListeners: jest.fn(),
    syncAcknowledged: jest.fn(),
    removeSyncAcknowledgedCallback: jest.fn(),
  },
}));

import { DidomiListener } from '../DidomiListener';
import { DidomiEventType } from '../DidomiTypes';

describe('test add listener', () => {
  it('add listener', () => {
    DidomiListener.addEventListener(DidomiEventType.READY, () => {});
    expect(DidomiListener.listeners.size).toBe(1);
    expect(DidomiListener.listeners.get(DidomiEventType.READY).length).toBe(1);
  });
});

describe('test remove listener', () => {
  it('remove ready listener', () => {
    const callback = () => {};
    DidomiListener.addEventListener(DidomiEventType.ERROR, callback);
    DidomiListener.removeEventListener(DidomiEventType.ERROR, callback);
    expect(DidomiListener.listeners.get(DidomiEventType.ERROR).length).toBe(0);
  });
});

describe('test widget listeners', () => {
  it('add and remove show widget listener', () => {
    const callback = () => {};
    DidomiListener.addEventListener(DidomiEventType.SHOW_WIDGET, callback);
    expect(
      DidomiListener.listeners.get(DidomiEventType.SHOW_WIDGET).length
    ).toBe(1);
    DidomiListener.removeEventListener(DidomiEventType.SHOW_WIDGET, callback);
    expect(
      DidomiListener.listeners.get(DidomiEventType.SHOW_WIDGET).length
    ).toBe(0);
  });

  it('add and remove hide widget listener', () => {
    const callback = () => {};
    DidomiListener.addEventListener(DidomiEventType.HIDE_WIDGET, callback);
    expect(
      DidomiListener.listeners.get(DidomiEventType.HIDE_WIDGET).length
    ).toBe(1);
    DidomiListener.removeEventListener(DidomiEventType.HIDE_WIDGET, callback);
    expect(
      DidomiListener.listeners.get(DidomiEventType.HIDE_WIDGET).length
    ).toBe(0);
  });

  it('registers the widget events with the native event emitter', () => {
    DidomiListener.eventEmitter.addListener = jest.fn();
    DidomiListener.init();
    const registered = (
      DidomiListener.eventEmitter.addListener as jest.Mock
    ).mock.calls.map((call) => call[0]);
    expect(registered).toContain('on_show_widget');
    expect(registered).toContain('on_hide_widget');
  });
});
