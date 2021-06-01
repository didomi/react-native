import { DidomiListener } from '../DidomiListener';
import { DidomiEventType } from '../DidomiTypes';

// Mocked NativeEventEmitter
jest.mock('react-native/Libraries/EventEmitter/NativeEventEmitter.js', () => {
  const { EventEmitter } = require('events');
  return EventEmitter;
});

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
