import { Money } from "./money";

export interface loan {
    id: number
    lenderName: string
    borrowerName: string
    amount: Money
    amountRepayed: Money
    datelent: Date
    datedue: Date
}