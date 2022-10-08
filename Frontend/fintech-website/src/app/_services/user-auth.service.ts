import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

  constructor(private httpClient:HttpClient) { }
 
  public setRoles(roles: Array<string>) {
    localStorage.setItem("roles", JSON.stringify(roles));
  }

  public getRoles(): Array<Map<string,string>> {
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
