import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { LoanComponent } from './admin/loan/loan.component';
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
import { SettingComponent as adminSettingComp } from './admin/setting/setting.component';
import { UserComponent } from './user/user.component';
import { WithdrawComponent } from './user/withdraw/withdraw.component';
import { AuthGuard } from './_auth/auth.guard';
import { AdminLoanApplicationComponent } from './admin/admin-loan-application/admin-loan-application.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'admin', component: AdminComponent, canActivate: [AuthGuard], data: { roles: ['app_admin'] },
    children: [
      { path: 'loan', component: LoanComponent, canActivate: [AuthGuard], data: { roles: ['app_admin'] } },
      { path: 'loan-application', component: AdminLoanApplicationComponent, canActivate: [AuthGuard], data: { roles: ['app_admin'] } },
      { path: 'setting', component: adminSettingComp, canActivate: [AuthGuard], data: { roles: ['app_admin'] } }
    ]
  },
  {
    path: 'user', component: UserComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] },
    children: [
      { path: 'requestLoan', component: RequestLoanComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
      { path: 'loanApplication', component: LoanApplicationComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
      { path: 'borrowed', component: BorrowedComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
      { path: 'lent', component: LentComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
      { path: "setting", component: SettingComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
      { path: "recharge", component: RechargeComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
      { path: "withdraw", component: WithdrawComponent, canActivate: [AuthGuard], data: { roles: ['app_user'] } },
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
