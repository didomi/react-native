// Use React Native's event emitter so subscription removal behaves as it does in JS.
jest.mock('../specs/NativeDidomi', () => ({
  __esModule: true,
  default: {
    addListener: jest.fn(),
    removeListeners: jest.fn(),
  },
}));

import { DidomiListener } from '../DidomiListener';

describe.each([
  {
    name: 'ready',
    eventName: 'on_ready_callback',
    subscribe: () => DidomiListener.setOnReadyListener(),
    timeoutMessage: 'Didomi SDK ready timeout',
    payload: undefined,
  },
  {
    name: 'error',
    eventName: 'on_error_callback',
    subscribe: () => DidomiListener.setOnErrorListener(),
    timeoutMessage: 'Didomi SDK error listener timeout',
    payload: 'Native SDK initialization failed',
  },
])('$name callback subscriptions', ({ eventName, subscribe, timeoutMessage, payload }) => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    DidomiListener.eventEmitter.removeAllListeners(eventName);
    jest.clearAllTimers();
    jest.useRealTimers();
  });

  it('resolves with the callback payload and cancels its timeout', async () => {
    const promise = subscribe();

    DidomiListener.eventEmitter.emit(eventName, payload);

    await expect(promise).resolves.toBe(payload);
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(0);
    expect(jest.getTimerCount()).toBe(0);
  });

  it('resolves all concurrent calls when the native callback arrives', async () => {
    const first = subscribe();
    const second = subscribe();

    DidomiListener.eventEmitter.emit(eventName, payload);

    await expect(first).resolves.toBe(payload);
    await expect(second).resolves.toBe(payload);
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(0);
    expect(jest.getTimerCount()).toBe(0);
  });

  it('keeps a newer call subscribed when an older call times out', async () => {
    const first = subscribe();
    const firstRejection = expect(first).rejects.toThrow(timeoutMessage);
    jest.advanceTimersByTime(10000);
    const second = subscribe();

    jest.advanceTimersByTime(20000);
    await firstRejection;
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(1);

    DidomiListener.eventEmitter.emit(eventName, payload);

    await expect(second).resolves.toBe(payload);
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(0);
    expect(jest.getTimerCount()).toBe(0);
  });

  it('does not remove another subscriber when the callback resolves', async () => {
    const callback = jest.fn();
    const otherSubscription = DidomiListener.eventEmitter.addListener(eventName, callback);
    const promise = subscribe();

    DidomiListener.eventEmitter.emit(eventName, payload);
    await expect(promise).resolves.toBe(payload);
    DidomiListener.eventEmitter.emit(eventName, payload);

    expect(callback).toHaveBeenCalledTimes(2);
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(1);
    otherSubscription.remove();
  });

  it('rejects after 30 seconds and allows a fresh subscription afterward', async () => {
    const promise = subscribe();
    const rejection = expect(promise).rejects.toThrow(timeoutMessage);

    jest.advanceTimersByTime(29999);
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(1);
    jest.advanceTimersByTime(1);
    await rejection;
    expect(DidomiListener.eventEmitter.listenerCount(eventName)).toBe(0);

    const retry = subscribe();
    DidomiListener.eventEmitter.emit(eventName, payload);
    await expect(retry).resolves.toBe(payload);
    expect(jest.getTimerCount()).toBe(0);
  });
});
