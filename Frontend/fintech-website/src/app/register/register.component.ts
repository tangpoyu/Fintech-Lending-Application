import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserAuthService } from '../_services/user-auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(
    private userService: UserService,
    private userAuthService: UserAuthService,
    private router: Router
    ) { }

  ngOnInit(): void {
  }


  signUp(signForm: NgForm) {
    let loginData = {
      username : "",
      password : ""
    }
    loginData.username = signForm.value.username
    loginData.password = signForm.value.password
    this.userService.register(signForm.value).subscribe(
      (response: any) => {
        this.userService.login(loginData).subscribe(
          (response: any) => {
            this.userAuthService.setRoles(response.authorities)
            if (response.authorities[0].authority === "ROLE_Admin") {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/user'])
            }
         },
          (error) => {
            console.log(error)
          }
        )
      },
      (error) => {
        console.log(error);
      }
    )
  }
}
