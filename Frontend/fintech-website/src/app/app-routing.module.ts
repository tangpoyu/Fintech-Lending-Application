import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { BorrowedComponent } from './user/borrowed/borrowed.component';
import { LentComponent } from './user/lent/lent.component';
import { LoanApplicationComponent } from './user/loan-application/loan-application.component';
import { RechargeComponent } from './user/recharge/recharge.component';
import { RequestLoanComponent } from './user/request-loan/request-loan.component';
import { SettingComponent } from './user/setting/setting.component';
import { UserComponent } from './user/user.component';
import { WithdrawComponent } from './user/withdraw/withdraw.component';
import { AuthGuard } from './_auth/auth.guard';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard], data: { roles: ['ROLE_Admin'] } },
  {
    path: 'user', component: UserComponent, canActivate: [AuthGuard], data: { roles: ['ROLE_User'] },
    children: [
      {path : 'requestLoan', component: RequestLoanComponent},
      {path : 'loanApplication', component: LoanApplicationComponent},
      {path : 'borrowed', component: BorrowedComponent},
      {path : 'lent', component: LentComponent},
      {path : "setting", component: SettingComponent}, 
      {path : "recharge", component: RechargeComponent},
      {path : "withdraw", component: WithdrawComponent}
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
