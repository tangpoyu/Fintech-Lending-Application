import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS }    from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './_auth/auth.guard';
import { AuthInterCeptor } from './_auth/auth.interceptor';
import { UserService } from './_services/user.service';
import { RegisterComponent } from './register/register.component';
import { RequestLoanComponent } from './user/request-loan/request-loan.component';
import { LoanApplicationComponent } from './user/loan-application/loan-application.component';
import { BorrowedComponent } from './user/borrowed/borrowed.component';
import { LentComponent } from './user/lent/lent.component';
import { SettingComponent } from './user/setting/setting.component';
import { NotifierModule } from 'angular-notifier';
import { DataTablesModule } from "angular-datatables";
import { RechargeComponent } from './user/recharge/recharge.component';
import { WithdrawComponent } from './user/withdraw/withdraw.component';
import { LoanComponent } from './admin/loan/loan.component';
import { AdminLoanApplicationComponent } from './admin/admin-loan-application/admin-loan-application.component';
import {OAuthModule} from "angular-oauth2-oidc";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    AdminComponent,
    UserComponent,
    LoginComponent,
    ForbiddenComponent,
    RegisterComponent,
    RequestLoanComponent,
    LoanApplicationComponent,
    BorrowedComponent,
    LentComponent,
    SettingComponent,
    RechargeComponent,
    WithdrawComponent,
    LoanComponent,
    AdminLoanApplicationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    NotifierModule,
    DataTablesModule,
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: ['http://localhost:8081'],
        sendAccessToken: true
      }
    }),
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterCeptor,
      multi: true
    },
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
