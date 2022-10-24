import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { Subject } from 'rxjs';
import { loan } from 'src/app/interface/loan';
import { UserService } from 'src/app/_services/user.service';
import { UserComponent } from '../user.component';

@Component({
  selector: 'app-borrowed',
  templateUrl: './borrowed.component.html',
  styleUrls: ['./borrowed.component.css']
})
export class BorrowedComponent implements OnInit, OnDestroy {

  loans: loan[] = []
  dtOptions: DataTables.Settings = {}
  dtTrigger: Subject<any> = new Subject()
  selectedLoanID: number = -1
  status: string = "ONGOING"

  constructor(private userService: UserService,
    private notifierService: NotifierService,
    private user: UserComponent) {
      this.userService.isInitialize()
     }



  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      lengthMenu: [5, 10, 15],
      processing: true,
    }
    this.updateLoan()
  }

  updateLoan(){
    this.userService.Borrored(this.status).subscribe(
      {
        next: (res) => {
          this.loans = res
          this.dtTrigger.next(this.loans)
          console.log(res)
        },
        error: (err) => console.log(err),
        complete: () => console.log("Done")
      }
    )
  }

  selectStatus(status: string){
    this.status = status;
    this.updateLoan()
  }
  

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe()
  }

  selectLoanToRepay(loan: loan){
    if (this.status == "COMPLETED") return
    const box = document.getElementById("check")
    if(box != null) {
      box.style.display = 'flex'
    }
    this.selectedLoanID = loan.id
  }

  RepayLoan(repayLoanForm: NgForm){
    const repay = {
      loanId: this.selectedLoanID,
      amount: repayLoanForm.value.amount
    }
    
    this.userService.repay(repay).subscribe({
      next: (res) => {
        this.updateLoan()
        this.user.updateUserData()
        this.notifierService.notify('success',"You have repayed successfully.")
        this.closeBox()
        console.log(res)
      },
      error: (err) => {
        this.notifierService.notify('error',"You don't have enough money to repay.")
        this.closeBox()
        console.log(err)
      },
      complete: () => console.log("Done")
    })
  }

  closeBox(){
    const box = document.getElementById("check")
    if(box != null) {
      box.style.display = 'none'
    }
    this.selectedLoanID = -1
  }

}
