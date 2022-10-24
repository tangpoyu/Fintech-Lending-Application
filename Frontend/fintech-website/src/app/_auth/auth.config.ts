import {AuthConfig} from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  clientId: 'oauth2-demo-pkce-client',
  issuer: "http://localhost:8080/auth/realms/oauth2",
  redirectUri: window.location.origin,
  responseType: 'code',
  scope: 'openid profile email offline_access',
  strictDiscoveryDocumentValidation: true,
}
