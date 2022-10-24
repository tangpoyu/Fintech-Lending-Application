import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  message = "";

  constructor(private userService: UserService ) { }

  ngOnInit(): void {
  }


  clickSideBar(element: HTMLElement){
    $(".sidebar ul li.active").removeClass('active');
    element.classList.add('active');
  }

}
