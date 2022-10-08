import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  login(loginForm: NgForm) {

    this.userService.login(loginForm.value).subscribe(
      (response:any) => {
        // this.userAuthService.setToken(response.jwtToken)
        this.userAuthService.setRoles(response.authorities)
        if(response.authorities[0].authority === "ROLE_Admin"){
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/user'])
        }
      },
      (error) => {
        console.log(error)
      }
    )

  }
}
