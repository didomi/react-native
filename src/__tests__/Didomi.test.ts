jest.mock('../specs/NativeDidomi', () => ({
  __esModule: true,
  default: {
    getUserCountryCode: jest.fn(),
    getUserRegionCode: jest.fn(),
  },
}));

import { Didomi } from '../Didomi';
import NativeDidomi from '../specs/NativeDidomi';

const RNDidomi = NativeDidomi as any;

describe('getUserCountryCode', () => {
  it('resolves the value returned by the native module', async () => {
    RNDidomi.getUserCountryCode.mockResolvedValue('FR');
    await expect(Didomi.getUserCountryCode()).resolves.toBe('FR');
  });

  it('resolves null when unknown', async () => {
    RNDidomi.getUserCountryCode.mockResolvedValue(null);
    await expect(Didomi.getUserCountryCode()).resolves.toBeNull();
  });
});

describe('getUserRegionCode', () => {
  it('resolves the value returned by the native module', async () => {
    RNDidomi.getUserRegionCode.mockResolvedValue('CA');
    await expect(Didomi.getUserRegionCode()).resolves.toBe('CA');
  });

  it('resolves null when unknown', async () => {
    RNDidomi.getUserRegionCode.mockResolvedValue(null);
    await expect(Didomi.getUserRegionCode()).resolves.toBeNull();
  });
});
