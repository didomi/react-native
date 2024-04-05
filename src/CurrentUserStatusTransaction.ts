import { PurposeStatus, VendorStatus } from './DidomiTypes';

// Transaction type exposed to client's app that allows the enabling and disabling of vendors and purposes.
export interface CurrentUserStatusTransaction {
  // Function to enable a specific purpose by its ID and returns the updated transaction
  enablePurpose(id: string): CurrentUserStatusTransaction;

  // Function to enable multiple purposes at once by their IDs and returns the updated transaction
  enablePurposes(ids: string[]): CurrentUserStatusTransaction;

  // Function to enable a specific vendor by its ID and returns the updated transaction
  enableVendor(id: string): CurrentUserStatusTransaction;

  // Function to enable multiple vendors at once by their IDs and returns the updated transaction
  enableVendors(ids: string[]): CurrentUserStatusTransaction;

  // Function to disable a specific purpose by its ID and returns the updated transaction
  disablePurpose(id: string): CurrentUserStatusTransaction;

  // Function to disable multiple purposes at once by their IDs and returns the updated transaction
  disablePurposes(ids: string[]): CurrentUserStatusTransaction;

  // Function to disable a specific vendor by its ID and returns the updated transaction
  disableVendor(id: string): CurrentUserStatusTransaction;

  // Function to disable multiple vendors at once by their IDs and returns the updated transaction
  disableVendors(ids: string[]): CurrentUserStatusTransaction;

  // Function to commit the changes made to the status of purposes and vendors
  // Returns a Promise that resolves to a boolean indicating whether the commit was successful
  commit(): Promise<boolean>;
}

// Interface used to transfer enabled and disabled IDs between internal methods in a more convenient way.
interface StatusIDs {
  enabled: string[];
  disabled: string[];
}

// Callback executed once changes are committed to the native implementation.
type NativeCallback = (
  enabledPurposes: string[],
  disabledPurposes: string[],
  enabledVendors: string[],
  disabledVendors: string[]
  ) => Promise<boolean>;

// Create a `CurrentUserStatusTransaction` object based on the native implementation.
// Please note that this implementation delays the interaction with the native libraries until the changes are committed.
export const createCurrentUserStatusTransaction = (nativeCallback: NativeCallback): CurrentUserStatusTransaction => {

  // Variable used to store statuses before changes are committed against the transaction.
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

    const result = nativeCallback(
      purposeIdsFromStatus.enabled,
      purposeIdsFromStatus.disabled,
      vendorIdsFromStatus.enabled,
      vendorIdsFromStatus.disabled,
    );

    return result;
  };

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

// Separates and returns IDs from the `status` object into `enabled` and `disabled` based on their `enabled` state.
const getIdsFromStatus = (status: { [key: string]: PurposeStatus } | { [key: string]: VendorStatus }): StatusIDs => {
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
