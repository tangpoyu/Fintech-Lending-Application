import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { Subject } from 'rxjs';
import { loan } from 'src/app/interface/loan';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-loan',
  templateUrl: './loan.component.html',
  styleUrls: ['./loan.component.css']
})
export class LoanComponent implements OnInit {

  dtOptions: DataTables.Settings = {}
  dtTrigger: Subject<any> = new Subject()
  loans: loan[] = []
  status: string = "ONGOING"

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
    this.updateLoan()
    
  }

  updateLoan() {
    this.userService.getAllLoans(this.status).subscribe({
      next: (res) => {
        this.loans = res
        this.notifierService.notify('success',"Get all loans successfully.")
        this.dtTrigger.next(this.loans)
        console.log(res)
      },
      error: (err) => this.notifierService.notify('error',"Get all loans unsuccessfully."),
      complete: () => console.log("Done")
    })
  }

  selectStatus(status: string){
    this.status = status;
    this.updateLoan()
  }

}
