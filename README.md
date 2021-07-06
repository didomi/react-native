# Didomi React Native

Didomi is a leading Consent Management Platform that allows companies to collect, store, and leverage user consent under GDPR, CCPA, and other data privacy regulations. This plugin enables Flutter developers to get in compliance and optimize their consent rate and monetization on native mobile apps.

This repository contains the source code and a sample app for the Didomi React Native plugin. This plugin enables React Native developers to easily use Didomi's Consent Management Platform on Android and iOS apps.

## Installation

Install using ``yarn add @didomi/react-native``
Or ``npm i @didomi/react-native``

Then install required pods for iOS with ``cd ios && pod install``

As Didomi requires Swift runtime for iOS, you may need to add an empty Swift file (to create a briding header) into Xcode project. 

## Usage


```js
import { Didomi, DidomiEventType } from '@didomi/react-native';

// ...

// Initialize and setup UI
Didomi.initialize('YOUR_API_KEY', undefined, undefined, undefined, false, undefined, 'YOUR_NOTICE_ID');
Didomi.setupUI();

// Retrieve data
const purposes = await Didomi.getEnabledPurposes();
const status = await Didomi.getUserStatusForVendor('vendorId');

//...

// Register an event listener
Didomi.addEventListener(DidomiEventType.SHOW_NOTICE, () => {
  console.log('Show notice');
});

//...

```

For a complete overview of available methods and events, have a look at the [online documentation](https://developers.didomi.io/cmp/react-native).

## Configuration with local file


In case you need to use a local configuration. The ``didomi_config.json`` must be added to native iOS and Android projects.

On iOS, drag and drop the file to Xcode files.

On Android, copy it to the ``src/main/assets` folder.

## Documentation

For complete instructions on installing and using the plugin, please read our documentation:

[Developer guide](https://developers.didomi.io/cmp/react-native)

[API reference](https://developers.didomi.io/cmp/react-native/reference)

## Example applications

Sources contain 2 applications: ``/example`` (an app designed mostly for UI testing ) and ``/example2`` (more human friendly app).

They can be run with:

```bash
# example or example2
cd example
cd ios && pod install
yarn ios
# or yarn android
```


## Suggesting improvements

To file bugs, make feature requests, or to suggest other improvements, please use [Github's issue tracker](https://github.com/didomi/reat-native/issues).

## License

Modified BSD License (BSD-3)
