import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { Subject } from 'rxjs';
import { LoanApplicaion } from 'src/app/interface/loanApplication';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-admin-loan-application',
  templateUrl: './admin-loan-application.component.html',
  styleUrls: ['./admin-loan-application.component.css']
})
export class AdminLoanApplicationComponent implements OnInit {

  dtOptions: DataTables.Settings = {}
  dtTrigger: Subject<any> = new Subject()
  loanApplicationData : LoanApplicaion[] = []
  status: string = "ONGOING"

  constructor(private userService: UserService,
    private notifierService: NotifierService,) { }

  

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      lengthMenu: [5, 10, 15],
      processing: true,
    }

    this.updateLoanApplication() 
  }


  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe()
  }


  updateLoanApplication() {
    this.userService.getAllApplicationLoans(this.status).subscribe({
      next: (res) => {
        this.loanApplicationData = res
        this.dtTrigger.next(this.loanApplicationData)
        this.notifierService.notify("success", "Get all loan application successfully")
        console.log(res)
      },
      error: (err) => {
        this.notifierService.notify("error", "Get all loan application unsuccessfully")
        console.log(err)
      },
      complete: () => console.log("Done")
    })
  }

  selectStatus(status: string){
    this.status = status;
    this.updateLoanApplication()
  }

}
