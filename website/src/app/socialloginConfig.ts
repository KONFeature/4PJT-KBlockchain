import {
  AuthServiceConfig,
  FacebookLoginProvider
} from 'angular5-social-login';

export function getAuthServiceConfigs() {
  let config = new AuthServiceConfig([
    {
      id: FacebookLoginProvider.PROVIDER_ID,
      provider: new FacebookLoginProvider('2347129585338818')
    }
  ]);
  return config;
}
