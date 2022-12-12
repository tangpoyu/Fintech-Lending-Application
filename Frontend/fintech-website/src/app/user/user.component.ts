import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../_services/user.service';
declare var $: any;  

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})



export class UserComponent implements OnInit{

  message = "";
  money : Array<number> = [];

  constructor(private userService: UserService, private router: Router) { 
    this.userService.isInitialize()
  }

  ngOnInit(): void {
    this.updateUserData()
    this.router.navigate(['/user/requestLoan'])
  }

  updateUserData(){
    this.userService.getUserLoanData().subscribe({
      next: (res) => this.money = res.amount,
      error: (err) => console.log(err),
      complete: () => console.log("Initialize user loan data.")
    })
  }

  clickSideBar(element: HTMLElement){
    $(".sidebar ul li.active").removeClass('active');
    element.classList.add('active');
  }

  openOrCloseSideBar(element: HTMLElement) {
    element.classList.remove('active');
  }
}
