import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

  constructor(private httpClient:HttpClient) { }
 
  public setRoles(roles: Array<string>): string {
    let r = ""

    roles.forEach((role, index) => {
      if (role === "app_user") r = role
      else if (role == "app_admin") r = role
    })
    localStorage.setItem("roles", JSON.stringify(roles));
    return r
  }

  public getRoles(): Array<string> {
    let result = localStorage.getItem('roles')?.toString() ?? "";
    if(result === "") return [];
    return JSON.parse(result);
  }

  // public setToken(jwtToken: string) {
  //   localStorage.setItem("jwtToken", jwtToken);
  // }

  // public getToken(): string {
  //   return localStorage.getItem("jwtToken") ?? "";
  // }

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn() : boolean{
    let a = this.getRoles().length != 0
    return a
  }
  
}
