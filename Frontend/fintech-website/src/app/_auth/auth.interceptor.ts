import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { NodeWithI18n } from "@angular/compiler";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { catchError, Observable, throwError } from "rxjs";
import { UserAuthService } from "../_services/user-auth.service";

@Injectable()
export class AuthInterCeptor implements HttpInterceptor{

    constructor(private userAuthService: UserAuthService,
        private router: Router){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if(req.headers.get("No-Auth") === "True"){
            return next.handle(req.clone())
        }

       // const token = this.userAuthService.getToken();
        return next.handle(req.clone()).pipe(
            catchError(
                (error: HttpErrorResponse) => {
                    console.log(error.status)
                    if(error.status === 401){
                        // user not login in
                        this.router.navigate(['/login'])
                    } else if(error.status === 403){
                        // user is forrbidden from path
                        this.router.navigate(['/forbidden'])
                    }
                    return throwError(error.status);
                }
            )
        )
    }

    // private addToken(request: HttpRequest<any>, token:string){
    //     return request.clone(
    //         {
    //             setHeaders:{
    //                 Authorization: `Bearer ${token}` 
    //             }
    //         }
    //     );
    // }
    
}