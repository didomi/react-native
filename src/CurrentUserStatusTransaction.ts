import { PurposeStatus, VendorStatus } from './DidomiTypes';

export interface CurrentUserStatusTransaction {
  enablePurpose(id: string): CurrentUserStatusTransaction;
  enablePurposes(ids: string[]): CurrentUserStatusTransaction;
  enableVendor(id: string): CurrentUserStatusTransaction;
  enableVendors(ids: string[]): CurrentUserStatusTransaction;
  disablePurpose(id: string): CurrentUserStatusTransaction;
  disablePurposes(ids: string[]): CurrentUserStatusTransaction;
  disableVendor(id: string): CurrentUserStatusTransaction;
  disableVendors(ids: string[]): CurrentUserStatusTransaction;
  commit(): Promise<boolean>;
}

interface IDStatus {
  enabled: string[];
  disabled: string[];
}

type CommitCallback = (enabledPurposes: string[], disabledPurposes: string[], enabledVendors: string[], disabledVendors: string[]) => Promise<boolean>;

export const createCurrentUserStatusTransaction = (commitCallback: CommitCallback): CurrentUserStatusTransaction => {

  // Partial statuses.
  let purposesStatus: { [key: string]: PurposeStatus } = {};
  let vendorsStatus: { [key: string]: VendorStatus } = {};

  const enablePurposes = (ids: string[]): CurrentUserStatusTransaction => {
    ids.forEach(enablePurpose);
    return transaction;
  };

  const enablePurpose = (id: string): CurrentUserStatusTransaction => {
    purposesStatus[id] = { id, enabled: true };
    return transaction;
  };

  const enableVendors = (ids: string[]): CurrentUserStatusTransaction => {
    ids.forEach(enableVendor);
    return transaction;
  };

  const enableVendor = (id: string): CurrentUserStatusTransaction => {
    vendorsStatus[id] = { id, enabled: true };
    return transaction;
  };

  const disablePurposes = (ids: string[]): CurrentUserStatusTransaction => {
    ids.forEach(disablePurpose);
    return transaction;
  };

  const disablePurpose = (id: string): CurrentUserStatusTransaction => {
    purposesStatus[id] = { id, enabled: false };
    return transaction;
  };

  const disableVendors = (ids: string[]) => {
    ids.forEach(disableVendor);
    return transaction;
  };

  const disableVendor = (id: string): CurrentUserStatusTransaction => {
    vendorsStatus[id] = { id, enabled: false };
    return transaction;
  };


  const commit = (): Promise<boolean> => {
    const vendorIdsFromStatus = getIdsFromStatus(vendorsStatus);
    const purposeIdsFromStatus = getIdsFromStatus(purposesStatus);

    const result = commitCallback(
      purposeIdsFromStatus.enabled,
      purposeIdsFromStatus.disabled,
      vendorIdsFromStatus.enabled,
      vendorIdsFromStatus.disabled,
    );

    return result;
  };

  const getIdsFromStatus = (status: { [key: string]: PurposeStatus } | { [key: string]: VendorStatus }): IDStatus => {
    let enabled: string[] = [];
    let disabled: string[] = [];
    const keys = Object.keys(status);
    keys.forEach((key) => {
      const value = status[key];
      if (value.enabled) {
        enabled.push(key);
      } else {
        disabled.push(key);
      }
    });

    return { enabled, disabled };
  }

  const transaction: CurrentUserStatusTransaction = {
    enablePurpose,
    enablePurposes,
    enableVendor,
    enableVendors,
    disablePurpose,
    disablePurposes,
    disableVendor,
    disableVendors,
    commit,
  };

  return transaction;
}
