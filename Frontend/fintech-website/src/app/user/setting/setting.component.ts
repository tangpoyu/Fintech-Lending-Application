import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent implements OnInit {

  constructor(private userService: UserService,
    private notifierService: NotifierService) { }

  ngOnInit(): void {
  }

  setBasicData(ngForm: NgForm) {
    this.userService.setBasicData(ngForm.value).subscribe({
      next: (res) => this.notifierService.notify('success', 'You have set data successfully.'),
      error: (err) => this.notifierService.notify('error','Set unsuccessfully, there are some internal Server error.')
    })
  }

}
