import { CurrentUserStatusTransaction, createCurrentUserStatusTransaction } from '../CurrentUserStatusTransaction';

describe('createCurrentUserStatusTransaction', () => {
  let transaction: CurrentUserStatusTransaction;
  let nativeCallbackMock: any;

  beforeEach(() => {
    nativeCallbackMock = jest.fn().mockResolvedValue(true);
    transaction = createCurrentUserStatusTransaction(nativeCallbackMock);
  });

  it('enables a single purpose', async () => {
    transaction.enablePurpose('cookies');
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith(['cookies'], [], [], []);
  });

  it('enables multiple purposes', async () => {
    transaction.enablePurposes(['cookies', 'analytics']);
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith(['cookies', 'analytics'], [], [], []);
  });

  it('disables a single purpose', async () => {
    transaction.disablePurpose('cookies');
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith([], ['cookies'], [], []);
  });

  it('disables multiple purposes', async () => {
    transaction.disablePurposes(['cookies', 'analytics']);
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith([], ['cookies', 'analytics'], [], []);
  });

  it('enables a single vendor', async () => {
    transaction.enableVendor('ipromote');
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith([], [], ['ipromote'], []);
  });

  it('enables multiple vendors', async () => {
    transaction.enableVendors(['ipromote', 'adnetwork']);
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith([], [], ['ipromote', 'adnetwork'], []);
  });

  it('disables a single vendor', async () => {
    transaction.disableVendor('ipromote');
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith([], [], [], ['ipromote']);
  });

  it('disables multiple vendors', async () => {
    transaction.disableVendors(['ipromote', 'adnetwork']);
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith([], [], [], ['ipromote', 'adnetwork']);
  });

  it('handles mixed enable/disable actions for purposes and vendors', async () => {
    transaction.enablePurpose('cookies').disablePurpose('analytics').enableVendor('ipromote').disableVendor('adnetwork');
    await transaction.commit();
    expect(nativeCallbackMock).toHaveBeenCalledWith(['cookies'], ['analytics'], ['ipromote'], ['adnetwork']);
  });
});