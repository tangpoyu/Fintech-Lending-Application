import { Component, OnDestroy, OnInit } from '@angular/core';
import {  Subject } from 'rxjs';
import { loan } from 'src/app/interface/loan';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-lent',
  templateUrl: './lent.component.html',
  styleUrls: ['./lent.component.css']
})
export class LentComponent implements OnInit, OnDestroy {

  loans : loan[] = []
  dtOptions: DataTables.Settings = {}
  dtTrigger: Subject<any> = new Subject()
  status: string = "ONGOING"

  constructor(private userService: UserService) {
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
    this.userService.lent(this.status).subscribe(
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

  ngOnDestroy(): void{
    this.dtTrigger.unsubscribe();
  }

}
