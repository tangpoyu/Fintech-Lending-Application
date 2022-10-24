import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { OAuthService } from 'angular-oauth2-oidc';
import { claim } from '../interface/claim';
import { authConfig } from '../_auth/auth.config';
import { UserAuthService } from '../_services/user-auth.service';
declare var $: any;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})


export class HomeComponent {
  private readonly notifier: NotifierService;



  constructor(private notifierService: NotifierService,
    private oauthService: OAuthService,
    private userAuthService: UserAuthService,
    private router: Router
  ) {
    this.notifier = notifierService;
    this.configure();
  }

  private configure() {
    this.oauthService.configure(authConfig);
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocumentAndLogin()
      .then(() => {
        let claim: claim = this.oauthService.getIdentityClaims() as claim
        let role = this.userAuthService.setRoles(claim.realm_access.roles)
        if(role === "app_admin")
        this.router.navigate(['/admin']);
        else if(role === "app_user")
        this.router.navigate(['/user'])
      })
  }



  clickSideBar(element: HTMLElement) {
    $(".sidebar ul li.active").removeClass('active');
    element.classList.add('active');
  }



  alert(message: string, type: string) {

    const wrapper = document.createElement('div')
    wrapper.innerHTML = [
      `<div class="alert alert-${type} alert-dismissible" role="alert">`,
      `   <div>${message}</div>`,
      '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
      '</div>'
    ].join('')

    document.getElementById('liveAlertPlaceholder')?.append(wrapper)
  }

  notify() {
    this.notifier.notify('success', 'You are awesome! I mean it!');
  }
}
