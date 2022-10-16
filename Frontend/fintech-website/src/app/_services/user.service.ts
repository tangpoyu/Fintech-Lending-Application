import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { join } from '@fireflysemantics/join'
import { Observable } from 'rxjs';
import { loan } from '../interface/loan';
import { LoanApplicaion } from '../interface/loanApplication';
import { Money } from '../interface/money';
import { Repay } from '../interface/repay';
import { UserAuthService } from './user-auth.service';


@Injectable({
  providedIn: 'root'
})

export class UserService {

  PATH_OF_API = "http://localhost:8081";
  LOAN_PATH_OF_API = "http://localhost:8082"

  // requestHeader = new HttpHeaders(
  //   { "No-Auth": "True" }
  // )


  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) { }

  public register(signUpData: any) {
    return this.httpClient.post(join(this.PATH_OF_API, "register"), signUpData, { withCredentials: true })
  }

  public login(loginData: any) {
    return this.httpClient.post(join(this.PATH_OF_API, "login"), loginData, { withCredentials: true })
  }

  public forUser() {
    return this.httpClient.get(join(this.PATH_OF_API, "user", "user"), { withCredentials: true, responseType: "text" });
  }

  public forAdmin() {
    return this.httpClient.get(join(this.PATH_OF_API, "user", "admin"), { withCredentials: true, responseType: "text" });
  }

  public requestLoan(loanApplicationData: any) {
    return this.httpClient.post(join(this.LOAN_PATH_OF_API, "loan", "request"), loanApplicationData, { withCredentials: true })
  }

  public loanApplication(): Observable<LoanApplicaion[]> {
    return this.httpClient.get<LoanApplicaion[]>(join(this.LOAN_PATH_OF_API, "loan", "Application", "all"), { withCredentials: true })
  }

  public acceptLoanApplication(id: number) {
    return this.httpClient.post(join(this.LOAN_PATH_OF_API, "loan", "accept", id.toString()), null, { withCredentials: true })
  }

  public Recharge(money: Money) {
    return this.httpClient.post(join(this.LOAN_PATH_OF_API, "balance", "topup"), money, { withCredentials: true })
  }

  public Borrored(status: string): Observable<loan[]> {
    return this.httpClient.get<loan[]>(join(this.LOAN_PATH_OF_API, "loan", "borrowed", status, "all"), { withCredentials: true })
  }

  public lent(status: string): Observable<loan[]> {
    return this.httpClient.get<loan[]>(join(this.LOAN_PATH_OF_API, "loan", "lent", status, "all"), { withCredentials: true })
  }

  public repay(repay: Repay) {
    return this.httpClient.post(join(this.LOAN_PATH_OF_API, "loan", "repay"), repay, { withCredentials: true })
  }


  public roleMatch(allowedRoles: string[]): boolean {
    let isMatch = false;
    const userRoles = this.userAuthService.getRoles();
    if (userRoles.length != 0) {
      userRoles.forEach(role => {
        allowedRoles.forEach(allowRole => {
          if (allowRole === Object(role)["authority"]) {
            isMatch = true;
          }
        })
      });
    }
    return isMatch;
  }
}
