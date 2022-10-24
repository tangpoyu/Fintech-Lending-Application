import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { UserService } from 'src/app/_services/user.service';
import { UserComponent } from '../user.component';

@Component({
  selector: 'app-recharge',
  templateUrl: './recharge.component.html',
  styleUrls: ['./recharge.component.css']
})
export class RechargeComponent implements OnInit {

  constructor(private userService: UserService,
    private notifyService: NotifierService, private user: UserComponent) {
      this.userService.isInitialize()
     }

  ngOnInit(): void {
  }

  recharge(ngForm: NgForm) {
    this.userService.Recharge(ngForm.value).subscribe(
      {
        next: (res) => {
          this.notifyService.notify('success',"You have recharged successfully.")
          this.user.updateUserData()
          console.log(res)
          },
        error: (err) => {
          this.notifyService.notify('error',"Sorry, system currently has some internal error.")
          console.log(err)
        },
        complete: () => console.log()
      }
    )
  } 


}
