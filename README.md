# [Fintech-Lending-Application](http://fintech-lending.tangpoyu.click/)

This project is for learing Web developent about Spring boot, Angular, AWS EC2, and Nginx.

## App Objective:
Implement a application which mocks to supply peer to peer lending transaction.

## Operation of App:

### Admin 

This system supplys a admin user to manage this app which has app_admin role. ( username: admin, password: 12345 )

* See Loan application : admin can see loan application which is current and past.

* See Loan : admin can see all user's loan data which is current or past.

****

### User

* Register user : through login page, you can register a user by register link or identity provider ( GitHub, Google ), and will be mapped a app_user role by default.

* Login : you can login through filling up the crendentials or identify provider ( Github, Google ).

* Request loan application : you can request a loan application by filling up the info of loan application form in Reqeust Loan page.      **(Note: currencyType only support NT.)**

* See Loan application : you can see current loan application from loan application page except the application which is be reqeusted by self.

* See Borrowed and Lent loan: you can see all own borrowed and lent loan data which is current or past from Borrowed page and Lent page.

* Repay loan : you can repay loan in borrowed page through clicking the loan and then filling up the replay loan info of form.

* Recharge money : you can recharge money in Recharge page.  **(Note: currencyType only support NT.)**

* Set basic information : you can set age and occupation in Setting page.

* Logout : you can logout user by clicking the logout button on the top right.




