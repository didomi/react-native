import React from 'react';
import { StyleSheet, View, TextInput } from 'react-native';
import { Didomi } from 'react-native-didomi';
import MethodCall from './MethodCall';

export default function Methods() {
  const [apiKey, onChangeApiKey] = React.useState('');
  const [language, onChangeLanguage] = React.useState('');
  const [preferenceType, onChangePreferences] = React.useState('');
  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        onChangeText={onChangeApiKey}
        value={apiKey}
        placeholder="API Key"
      />

      <MethodCall
        name="initialize"
        call={() => {
          Didomi.initialize(apiKey);
        }}
        test={() => {
          return true;
        }}
      />

      <TextInput
        style={styles.input}
        onChangeText={onChangeLanguage}
        value={language}
        placeholder="Language : en, es, fr..."
      />

      <MethodCall
        name="updateSelectedLanguage"
        call={() => {
          Didomi.updateSelectedLanguage(language);
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="setupUI"
        call={() => {
          Didomi.setupUI();
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="showNotice"
        call={() => {
          Didomi.showNotice();
        }}
        test={() => {
          return true;
        }}
      />

      <TextInput
        style={styles.input}
        onChangeText={onChangePreferences}
        value={preferenceType}
        placeholder="Preferences type : purposes, vendors"
      />

      <MethodCall
        name="showPreferences"
        call={() => {
          Didomi.showPreferences(preferenceType);
        }}
        test={() => {
          return true;
        }}
      />

      <MethodCall
        name="reset"
        call={() => {
          Didomi.reset();
        }}
        test={() => {
          return true;
        }}
      />
      {/*
setUser
setUserConsentStatus
setUserDisagreeToAll
setUserStatus
setUserStatusSets
updateSelectedLanguage
*/}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'column',
  },
  input: {
    height: 40,
    width: 250,
    margin: 5,
    textAlign: 'center',
    backgroundColor: '#eaeaea',
  },
});
