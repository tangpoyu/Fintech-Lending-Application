import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-request-loan',
  templateUrl: './request-loan.component.html',
  styleUrls: ['./request-loan.component.css']
})
export class RequestLoanComponent implements OnInit {

  private readonly notifier: NotifierService;

  constructor(private usrService: UserService,
              private notifierService: NotifierService) {
    this.notifier = notifierService;
   }

  ngOnInit(): void {
  }

  requestLoan(ngForm: NgForm){
    this.usrService.requestLoan(ngForm.value).subscribe(
      (response: any) => {
        this.successBuildLoanApplication(response);
      },
      (error: any) => {
        this.failBuildLoanApplication()
      }
    )
  }

  successBuildLoanApplication(id: number){
    this.notifier.notify('success', `The Loan Application ${id} has built successfully.`);
  }

  failBuildLoanApplication(){
    this.notifier.notify('error', `The Loan Application has built failed.`)
  }

}
