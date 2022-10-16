import { Component, NgIterable, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, of, startWith, Subject } from 'rxjs';
import { UserService } from 'src/app/_services/user.service';
import { LoanApplicaion } from '../../interface/loanApplication';
import { DataTableDirective } from 'angular-datatables';
import { NotifierService } from 'angular-notifier';

@Component({
  selector: 'app-loan-application',
  templateUrl: './loan-application.component.html',
  styleUrls: ['./loan-application.component.css']
})
export class LoanApplicationComponent implements OnInit, OnDestroy{

  loanApplicationData: LoanApplicaion[] = []
  selectedLoanApplicationID : number = -1;

  message = '';
  dtOptions: DataTables.Settings = {}
  dtTrigger: Subject<any> = new Subject()



  constructor(private userService: UserService,
    private notifierService: NotifierService) {
  }


  ngOnInit(): void {
    
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      lengthMenu: [5, 10, 15],
      processing: true,
    }
    this.updateLoanApplication() 
  }

  updateLoanApplication(){
    this.userService.loanApplication().subscribe({
      next: (response: LoanApplicaion[]) => {
        this.loanApplicationData = response
        this.dtTrigger.next(this.loanApplicationData);
        console.log(this.loanApplicationData)
      },
      error: (error: any) => console.log(error),
      complete: () => console.log("Done retrieving loanApplicaion.")
    })
  }
  
  ngOnDestroy(): void{
    this.dtTrigger.unsubscribe();
  }
  
  click(loanApplicaion : LoanApplicaion) {
    const box = document.getElementById("check")
    if(box != null) {
      box.style.display = 'flex'
    }
    this.selectedLoanApplicationID = loanApplicaion.id;
  }

  closeBox(){
    const box = document.getElementById("check")
    if(box != null) {
      box.style.display = 'none'
    }
    this.selectedLoanApplicationID = -1
  }

  acceptLoanApplication() {
    this.userService.acceptLoanApplication(this.selectedLoanApplicationID).subscribe(
      {
        next: (response) => {
          this.updateLoanApplication() 
          this.notifierService.notify('success',`You have accepted loan application ${this.selectedLoanApplicationID} successfully.`)
          this.closeBox()
        },
        error: (error) => {
          if(error ===500){
            this.notifierService.notify('error', `You don't have enough money to accept.`)
            this.closeBox()
          }
        },
        complete: () => console.log("Done Accept loanApplicaion.")
      }
    )
  }


  // this.userService.loanApplication().pipe(
  //   map(response => {
  //     console.log(response)
  //     // for(let id in response) {
  //     //   this.loanApplicationData.push({...response[id], id})
  //     // }
  //   }),
  //   startWith({}),
  //   catchError((error: string) => {
  //     return error
  //   })
  // )
}




