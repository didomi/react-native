jest.mock('../specs/NativeDidomi', () => ({
  __esModule: true,
  default: {
    getUserCountryCode: jest.fn(),
    getUserRegionCode: jest.fn(),
    getApplicableRegulationConfiguration: jest.fn(),
    getCurrentUserStatus: jest.fn(),
    getUserStatus: jest.fn(),
    showWidget: jest.fn(),
    hideWidget: jest.fn(),
    isWidgetVisible: jest.fn(),
  },
}));

import { Didomi } from '../Didomi';
import NativeDidomi from '../specs/NativeDidomi';
import type { UserStatus } from '../DidomiTypes';

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

describe('getApplicableRegulationConfiguration', () => {
  it('resolves the configuration returned by the native module', async () => {
    const configuration = {
      id: 'abcd',
      versionId: 'v1',
      numericalId: 123,
      regulationName: 'gdpr',
      tokenStorageName: 'didomi_token_abcd',
      mixed: false,
    };
    RNDidomi.getApplicableRegulationConfiguration.mockResolvedValue(configuration);
    await expect(Didomi.getApplicableRegulationConfiguration()).resolves.toEqual(configuration);
  });

  it('resolves null when no regulation configuration is provided', async () => {
    RNDidomi.getApplicableRegulationConfiguration.mockResolvedValue(null);
    await expect(Didomi.getApplicableRegulationConfiguration()).resolves.toBeNull();
  });
});

describe('getCurrentUserStatus', () => {
  it('resolves the widget that collected the consent', async () => {
    const widget = { type: 'notice', id: 'widget-1', version_id: 'v2' };
    RNDidomi.getCurrentUserStatus.mockResolvedValue({ regulation: 'gdpr', widget });
    const status = await Didomi.getCurrentUserStatus();
    expect(status.widget).toEqual(widget);
  });

  it('resolves a widget without type and version id', async () => {
    const widget = { type: null, id: 'widget-1', version_id: null };
    RNDidomi.getCurrentUserStatus.mockResolvedValue({ regulation: 'gdpr', widget });
    const status = await Didomi.getCurrentUserStatus();
    expect(status.widget).toEqual(widget);
  });

  it('resolves no widget when the consent was not collected by a widget', async () => {
    RNDidomi.getCurrentUserStatus.mockResolvedValue({ regulation: 'gdpr' });
    const status = await Didomi.getCurrentUserStatus();
    expect(status.widget).toBeUndefined();
  });
});

describe('getUserStatus', () => {
  it('resolves the keys returned by the native SDKs', async () => {
    // Payload shape returned by Android (Gson) and iOS (Codable): typed as UserStatus so the type check fails if they drift
    const nativeUserStatus: UserStatus = {
      purposes: {
        global: { enabled: ['cookies'], disabled: [] },
        consent: { enabled: ['cookies'], disabled: [] },
        legitimate_interest: { enabled: [], disabled: [] },
        essential: ['essential_purpose'],
      },
      vendors: {
        global: { enabled: ['vendor-1'], disabled: [] },
        global_consent: { enabled: ['vendor-1'], disabled: [] },
        global_li: { enabled: [], disabled: ['vendor-2'] },
        consent: { enabled: ['vendor-1'], disabled: [] },
        legitimate_interest: { enabled: [], disabled: [] },
      },
      user_id: 'user-id',
      created: '2026-01-01T00:00:00.000Z',
      updated: '2026-01-01T00:00:00.000Z',
      consent_string: 'consent-string',
      addtl_consent: '1~',
      didomi_dcs: 'dcs',
      regulation: 'gdpr',
    };
    RNDidomi.getUserStatus.mockResolvedValue(nativeUserStatus);
    const status = await Didomi.getUserStatus();
    expect(status.addtl_consent).toBe('1~');
    expect(status.vendors.global_li.disabled).toEqual(['vendor-2']);
    expect(status.purposes.essential).toEqual(['essential_purpose']);
  });
});

describe('showWidget', () => {
  beforeEach(() => {
    RNDidomi.showWidget.mockReset();
    RNDidomi.showWidget.mockResolvedValue(0);
  });

  it('passes null parameters when none are provided', async () => {
    await Didomi.showWidget();
    expect(RNDidomi.showWidget).toHaveBeenCalledWith(null, null);
  });

  it('passes the widget ID only', async () => {
    await Didomi.showWidget({ widgetId: 'widget_cpra' });
    expect(RNDidomi.showWidget).toHaveBeenCalledWith('widget_cpra', null);
  });

  it('passes the widget ID and layer name', async () => {
    await Didomi.showWidget({ widgetId: 'widget_cpra', layerName: 'purposes' });
    expect(RNDidomi.showWidget).toHaveBeenCalledWith('widget_cpra', 'purposes');
  });
});

describe('hideWidget', () => {
  it('calls the native module', async () => {
    RNDidomi.hideWidget.mockResolvedValue(0);
    await Didomi.hideWidget();
    expect(RNDidomi.hideWidget).toHaveBeenCalledTimes(1);
  });
});

describe('isWidgetVisible', () => {
  beforeEach(() => {
    RNDidomi.isWidgetVisible.mockReset();
  });

  it('checks any widget when no ID is provided', async () => {
    RNDidomi.isWidgetVisible.mockResolvedValue(true);
    await expect(Didomi.isWidgetVisible()).resolves.toBe(true);
    expect(RNDidomi.isWidgetVisible).toHaveBeenCalledWith(null);
  });

  it('checks a specific widget', async () => {
    RNDidomi.isWidgetVisible.mockResolvedValue(false);
    await expect(Didomi.isWidgetVisible('widget_cpra')).resolves.toBe(false);
    expect(RNDidomi.isWidgetVisible).toHaveBeenCalledWith('widget_cpra');
  });
});
