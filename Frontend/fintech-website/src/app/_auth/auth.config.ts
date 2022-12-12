import {AuthConfig} from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';

export const authConfig: AuthConfig = {
  clientId: 'oauth2-demo-pkce-client',
  issuer: environment.issuer,
  redirectUri: window.location.origin,
  requireHttps: false,
  responseType: 'code',
  scope: 'openid profile email offline_access',
  strictDiscoveryDocumentValidation: false,
  skipIssuerCheck: true,
}

