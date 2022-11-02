import {AuthConfig} from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  clientId: 'oauth2-demo-pkce-client',
  issuer: "https://fintech-lending.tangpoyu.click/auth/realms/oauth2",
  redirectUri: window.location.origin,
  requireHttps: false,
  responseType: 'code',
  scope: 'openid profile email offline_access',
  strictDiscoveryDocumentValidation: false,
  skipIssuerCheck: true,
}
