import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
declare var $: any;  
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})


export class HomeComponent {
  private readonly notifier: NotifierService;
  

  constructor(notifierService: NotifierService) {
    this.notifier = notifierService;
   }

  
  clickSideBar(element: HTMLElement){
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

  notify(){
    this.notifier.notify('success', 'You are awesome! I mean it!');
  }
}
