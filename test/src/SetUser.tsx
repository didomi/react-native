import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Didomi } from '@didomi/react-native';
import Setter from './SetterCall';

export default function SetUser() {

  const userId = "d13e49f6255c8729cbb201310f49d70d65f365415a67f034b567b7eac962b944eda131376594ef5e23b025fada4e4259e953ceb45ea57a2ced7872c567e6d1fae8dcc3a9772ead783d8513032e77d3fd";
  const secretId = "testsdks-PEap2wBx";

  return (
    <View style={styles.container}>

      <Setter
        name="clearUser"
        call={async () => {
          return await Didomi.clearUser();
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithId"
        call={async () => {
          return await Didomi.setUser("abcd");
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithIdAndSetupUI"
        call={async () => {
          return await Didomi.setUserAndSetupUI("abcd");
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithHashAuth"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "hash-md5", secretId: secretId, digest: "test-digest"};
          return Didomi.setUserWithAuthParams(userAuthParams);
        }}
        test={() => {
          return true;
        }}
        />

      <Setter
        name="setUserWithHashAuthAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "hash-md5", secretId: secretId, digest: "test-digest"};
          return Didomi.setUserWithAuthParamsAndSetupUI(userAuthParams);
        }}
        test={() => {
          return true;
        }}
        />

      <Setter
        name="setUserWithHashAuthWithSaltAndExpiration"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "hash-md5", secretId: secretId, digest: "test-digest", salt: "test-salt", expiration: 3600};
          return Didomi.setUserWithAuthParams(userAuthParams);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithHashAuthWithSaltAndExpirationAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "hash-md5", secretId: secretId, digest: "test-digest", salt: "test-salt", expiration: 3600};
          return Didomi.setUserWithAuthParamsAndSetupUI(userAuthParams);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithEncryptionAuth"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"};
          return Didomi.setUserWithAuthParams(userAuthParams);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithEncryptionAuthAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"};
          return Didomi.setUserWithAuthParamsAndSetupUI(userAuthParams);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithEncryptionAuthWithExpiration"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};
          return Didomi.setUserWithAuthParams(userAuthParams);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithEncryptionAuthWithExpirationAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};
          return Didomi.setUserWithAuthParamsAndSetupUI(userAuthParams);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithHashAuthWithSynchronizedUsers"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "hash-md5", secretId: secretId, digest: "test-digest"};

          var synchronizedUsers = [
            {id: userId + "1", algorithm: "hash-md5", secretId: secretId, digest: "test-digest"},
            {id: userId + "2", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"}
          ];

          return Didomi.setUserWithAuthParams(userAuthParams, synchronizedUsers);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithHashAuthWithSynchronizedUsersAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "hash-md5", secretId: secretId, digest: "test-digest"};

          var synchronizedUsers = [
            {id: userId + "1", algorithm: "hash-md5", secretId: secretId, digest: "test-digest"},
            {id: userId + "2", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"}
          ];

          return Didomi.setUserWithAuthParamsAndSetupUI(userAuthParams, synchronizedUsers);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithEncryptionAuthWithSynchronizedUsers"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};

          var synchronizedUsers = [
            {id: userId + "1", algorithm: "hash-md5", secretId: secretId, digest: "test-digest"},
            {id: userId + "2", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"}
          ];

          return Didomi.setUserWithAuthParams(userAuthParams, synchronizedUsers);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithEncryptionAuthWithSynchronizedUsersAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};

          var synchronizedUsers = [
            {id: userId + "1", algorithm: "hash-md5", secretId: secretId, digest: "test-digest"},
            {id: userId + "2", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"}
          ];

          return Didomi.setUserWithAuthParamsAndSetupUI(userAuthParams, synchronizedUsers);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithParameters"
        call={async () => {

          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};

          var dcsUserAuth = {id: userId + "-dcs", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};

          var synchronizedUsers = [
            {id: userId + "1", algorithm: "hash-md5", secretId: secretId, digest: "test-digest"},
            {id: userId + "2", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"}
          ];

          var parameters = {userAuthParams: userAuthParams, dcsUserAuth: dcsUserAuth, synchronizedUsers: synchronizedUsers, isUnderage: false};

          return Didomi.setUserWithParameters(parameters);
        }}
        test={() => {
          return true;
        }}
      />

      <Setter
        name="setUserWithParametersAndSetupUI"
        call={async () => {
          var userAuthParams = {id: userId, algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd", expiration: 3600};

          var synchronizedUsers = [
            {id: userId + "1", algorithm: "hash-md5", secretId: secretId, digest: "test-digest"},
            {id: userId + "2", algorithm: "aes-256-cbc", secretId: secretId, initializationVector: "abcd"}
          ];

          var parameters = {userAuthParams: userAuthParams, synchronizedUsers: synchronizedUsers, isUnderage: false};

          return await Didomi.setUserWithParametersAndSetupUI(parameters);
        }}
        test={() => {
          return true;
        }}
      />  
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
});
