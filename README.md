# Didomi React Native

Didomi is a leading Consent Management Platform that allows companies to collect, store, and leverage user consent under GDPR, CCPA, and other data privacy regulations. This plugin enables Flutter developers to get in compliance and optimize their consent rate and monetization on native mobile apps.

This repository contains the source code and a sample app for the Didomi React Native plugin. This plugin enables React Native developers to easily use Didomi's Consent Management Platform on Android and iOS apps.

## Installation

Install using ``yarn add @didomi/react-native``
Or ``npm i @didomi/react-native``

Then install required pods for iOS with ``cd ios && pod install``



## Usage

```js
import { Didomi, DidomiEventType } from '@didomi/react-native-didomi';


// ...

// Initialize and setup UI
await Didomi.initialize('YOUR_API_KEY', '', '', '', true);
Didomi.setupUI();

// ...

// Register an event listener
Didomi.addEventListener(DidomiEventType.SHOW_NOTICE, () => {
  console.log('Show notice');
});

```

For a complete overview of available methods and events, have a look at the [online documentation](https://developers.didomi.io/cmp/react-native).

## Documentation

For complete instructions on installing and using the plugin, please read our documentation:

[Developer guide](https://developers.didomi.io/cmp/react-native).

[API reference](https://developers.didomi.io/cmp/react-native/reference)

## Example applications

Sources contain 2 applications: ``/example`` (an app designed mostly for UI testing ) and ``/example2`` (more human friendly app).

They can be run with:

```bash
# example or exmaple2
cd example
cd ios && pod install
yarn ios
# or yarn android
```



## Suggesting improvements

To file bugs, make feature requests, or to suggest other improvements, please use [Github's issue tracker](https://github.com/didomi/reat-native/issues).

## License

Modified BSD License (BSD-3)
