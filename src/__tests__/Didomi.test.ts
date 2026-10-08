jest.mock('../specs/NativeDidomi', () => ({
  __esModule: true,
  default: {
    getUserCountryCode: jest.fn(),
    getUserRegionCode: jest.fn(),
    showWidget: jest.fn(),
    hideWidget: jest.fn(),
    isWidgetVisible: jest.fn(),
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
