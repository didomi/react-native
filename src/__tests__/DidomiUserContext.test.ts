jest.mock('../specs/NativeDidomi', () => ({
  __esModule: true,
  default: {
    addListener: jest.fn(),
    removeListeners: jest.fn(),
    setUserWithParameters: jest.fn(),
    setUserWithParametersAndSetupUI: jest.fn(),
  },
}));

import { Didomi } from '../Didomi';
import type { DidomiMultiUserParameters, DidomiUserContext, DidomiUserParameters } from '../DidomiTypes';
import NativeDidomi from '../specs/NativeDidomi';

const RNDidomi = jest.mocked(NativeDidomi);

describe.each([
  'setUserWithParameters',
  'setUserWithParametersAndSetupUI',
] as const)('%s user context', (method) => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it.each<DidomiUserContext>([
    { attributes: { tier: 'premium' }, variables: { language: 'fr' }, age: 18, isLoggedIn: true },
    { age: 0, isLoggedIn: false },
    { attributes: {}, variables: {} },
    {},
  ])('forwards the context without changing its values: %j', (userContext) => {
    const parameters: DidomiMultiUserParameters = {
      userAuth: { id: 'user-123' },
      userContext,
    };

    Didomi[method](parameters);

    expect(RNDidomi[method]).toHaveBeenCalledWith(JSON.stringify(parameters));
  });

  it('includes context alongside synchronized users', () => {
    const parameters: DidomiMultiUserParameters = {
      userAuth: { id: 'user-123' },
      synchronizedUsers: [{ id: 'other-user', algorithm: 'SHA-256', secretId: 'test-secret-id' }],
      userContext: { age: 21, isLoggedIn: false },
    };

    Didomi[method](parameters);

    expect(RNDidomi[method]).toHaveBeenCalledWith(JSON.stringify(parameters));
  });

  it.each<DidomiUserParameters>([
    {},
    { userId: 'user-123' },
    { userContext: { age: 18, isLoggedIn: false } },
  ])('allows parameters without userAuth: %j', (parameters) => {
    Didomi[method](parameters);

    expect(RNDidomi[method]).toHaveBeenCalledWith(JSON.stringify(parameters));
  });

  it('allows synchronized users without userAuth', () => {
    const parameters: DidomiMultiUserParameters = {
      synchronizedUsers: [{ id: 'other-user', algorithm: 'SHA-256', secretId: 'test-secret-id' }],
      userContext: { age: 21, isLoggedIn: false },
    };

    Didomi[method](parameters);

    expect(RNDidomi[method]).toHaveBeenCalledWith(JSON.stringify(parameters));
  });

  it('keeps context optional for existing callers', () => {
    const parameters = { userAuth: { id: 'user-123' } };

    Didomi[method](parameters);

    expect(RNDidomi[method]).toHaveBeenCalledWith(JSON.stringify(parameters));
  });
});
