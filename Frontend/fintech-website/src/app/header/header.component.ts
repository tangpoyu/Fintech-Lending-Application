import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from '../_auth/auth.config';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(
    private userAuthService: UserAuthService,
    private router: Router,
    private userService: UserService,
    private oauthService: OAuthService
    ) {}


  public login() {
    this.oauthService.initCodeFlow();
  }

  logout() {
    this.oauthService.logOut();
  }

  // public logout(){
  //   this.userAuthService.clear()
  //   this.router.navigate(['/home'])
  // }


  public isLoggedIn(): boolean{
    return this.userAuthService.isLoggedIn();
  }


  public displayFor(allowRoles: string[]) : boolean{
    return this.userService.roleMatch(allowRoles)
  }
}
