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

  constructor(private usrService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.router.navigate(['/user/requestLoan'])
  }

 

  clickSideBar(element: HTMLElement){
    $(".sidebar ul li.active").removeClass('active');
    element.classList.add('active');
  }

  openOrCloseSideBar(element: HTMLElement) {
    element.classList.remove('active');
  }

  forUser() {
    this.usrService.forUser().subscribe(
      (response) => {
        console.log(response)
        this.message = response;
      },
      (error) => {
        console.log(error)
      }
    )
  }
}
