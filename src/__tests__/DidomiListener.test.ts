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
