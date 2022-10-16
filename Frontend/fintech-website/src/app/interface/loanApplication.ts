import { Money } from "./money"


export interface LoanApplicaion {
    id : number
    username: string
    fullName: string
    occupation: string
    amount: Money
    interestRate: number
    repaymentTerm: number
}