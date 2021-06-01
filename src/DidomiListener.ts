import { NativeModules, NativeEventEmitter } from 'react-native'
import { DidomiEventType } from './DidomiTypes'

const { Didomi: RNDidomi } = NativeModules

export const DidomiListener = {
  listeners: new Map(),
  eventEmitter: new NativeEventEmitter(RNDidomi),

  init: () => {
    // Register native event listeners
    DidomiListener.eventEmitter.addListener(
      DidomiEventType.READY,
      (event: any) => {
        // FIXME: event never received (missing declaration on native bridge)
        let events = DidomiListener.listeners.get(DidomiEventType.READY)
        if (events) {
          events.forEach((el: any) => {
            el()
          })
        }
      }
    )

    // TODO: register every event type !!!
  },

  addEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => {
    let events = DidomiListener.listeners.get(eventType)
    if (!events) {
      events = new Array()
      DidomiListener.listeners.set(eventType, events)
    }
    events.push(callback)
  },

  removeEventListener: (
    eventType: DidomiEventType,
    callback: (data: any) => void
  ) => {
    let events = DidomiListener.listeners.get(eventType)
    if (events) {
      var index = events.indexOf(callback)
      if (index !== -1) {
        events.splice(index, 1)
      }
    }
  },
}
