import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { join } from '@fireflysemantics/join'
import { UserAuthService } from './user-auth.service';


@Injectable({
  providedIn: 'root'
})

export class UserService {

  PATH_OF_API = "http://localhost:8080/";
  requestHeader = new HttpHeaders(
    { "No-Auth": "True" }
  )


  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) { }

  public login(loginData: any) {
    return this.httpClient.post(join(this.PATH_OF_API, "authenticate"), loginData, { headers: this.requestHeader, withCredentials:true })
  }

  public forUser() {
    return this.httpClient.get(join(this.PATH_OF_API,"user","user"), {withCredentials:true , responseType:"text"});
  }

  public forAdmin() {
    return this.httpClient.get(join(this.PATH_OF_API,"user","admin"), {withCredentials:true ,responseType:"text"});
  }

  public roleMatch(allowedRoles: string[]) : boolean{
    let isMatch = false;
    const userRoles = this.userAuthService.getRoles();
    if(userRoles.length != 0){
      userRoles.forEach(role => {
        allowedRoles.forEach(allowRole => {
          if(allowRole === Object(role)["authority"]){
            isMatch = true;
          }
        })
      });
    }
    return isMatch;
  }
}
