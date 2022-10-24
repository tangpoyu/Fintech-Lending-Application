import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { join } from '@fireflysemantics/join'
import { Observable } from 'rxjs';
import { loan } from '../interface/loan';
import { LoanApplicaion } from '../interface/loanApplication';
import { Money } from '../interface/money';
import { Repay } from '../interface/repay';
import { UserBasicData } from '../interface/userBasicData';
import { UserLoanData } from '../interface/userLoanData';
import { UserAuthService } from './user-auth.service';


@Injectable({
  providedIn: 'root'
})

export class UserService { 

  LOAN_API_PATH_FOR_USER = "http://localhost:8081/user"
  LOAN_API_PATH_FOR_ADMIN = "http://localhost:8081/admin"

  // requestHeader = new HttpHeaders(
  //   { "No-Auth": "True" }
  // )


  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService,
    private router: Router
  ) { }


  // ---------------------- user ----------------------------------------------

  public isInitialize(){
    this.httpClient.get<boolean>(join(this.LOAN_API_PATH_FOR_USER,"isInitialize"), {withCredentials: true})
    .subscribe({
      next: (res) => {
        if(!res) this.router.navigate(['/user/setting'])
      },
      error: (err) => { console.log(err) }
    })
  }

  public setBasicData(userBasicData: UserBasicData) {
    return this.httpClient.put(join(this.LOAN_API_PATH_FOR_USER, "setting"), userBasicData, { withCredentials: true})
  }

  public getUserLoanData(): Observable<UserLoanData> {
    return this.httpClient.get<UserLoanData>(join(this.LOAN_API_PATH_FOR_USER, "userdata"), { withCredentials: true })
  }

  public requestLoan(loanApplicationData: any) {
    return this.httpClient.post(join(this.LOAN_API_PATH_FOR_USER, "loan", "request"), loanApplicationData, { withCredentials: true })
  }

  public loanApplication(): Observable<LoanApplicaion[]> {
    return this.httpClient.get<LoanApplicaion[]>(join(this.LOAN_API_PATH_FOR_USER, "loan", "application", "all"), { withCredentials: true })
  }

  public acceptLoanApplication(id: number) {
    return this.httpClient.post(join(this.LOAN_API_PATH_FOR_USER, "loan", "accept", id.toString()), null, { withCredentials: true })
  }

  public Recharge(money: Money) {
    return this.httpClient.post(join(this.LOAN_API_PATH_FOR_USER, "balance", "topup"), money, { withCredentials: true })
  }

  public Borrored(status: string): Observable<loan[]> {
    return this.httpClient.get<loan[]>(join(this.LOAN_API_PATH_FOR_USER, "loan", "borrowed", status, "all"), { withCredentials: true })
  }

  public lent(status: string): Observable<loan[]> {
    return this.httpClient.get<loan[]>(join(this.LOAN_API_PATH_FOR_USER, "loan", "lent", status, "all"), { withCredentials: true })
  }

  public repay(repay: Repay) {
    return this.httpClient.post(join(this.LOAN_API_PATH_FOR_USER, "loan", "repay"), repay, { withCredentials: true })
  }

  // ---------------------- admin ----------------------------------------------

  public getAllLoans(status: string): Observable<loan[]> {
    return this.httpClient.get<loan[]>(join(this.LOAN_API_PATH_FOR_ADMIN, 'loan', status, 'all'), { withCredentials: true })
  }

  public getAllApplicationLoans(status: string): Observable<LoanApplicaion[]> {
    return this.httpClient.get<LoanApplicaion[]>(join(this.LOAN_API_PATH_FOR_ADMIN, 'loan', 'application', status, 'all'), { withCredentials: true })
  }


  // ---------------------- utils ----------------------------------------------

  public roleMatch(allowedRoles: string[]): boolean {
    let isMatch = false;
    const userRoles = this.userAuthService.getRoles();
    if (userRoles.length != 0) {
      userRoles.forEach(role => {
        allowedRoles.forEach(allowRole => {
          if (allowRole === role) {
            isMatch = true;
          }
        })
      });
    }
    return isMatch;
  }
}
