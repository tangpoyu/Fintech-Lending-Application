package com.peerlender.LendingEngine.application.dto;

import com.peerlender.LendingEngine.domain.entity.Loan;
import com.peerlender.LendingEngine.domain.entity.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanDto {
    private long id;
    private String lenderName, borrowerName;
    private Money amount, amountRepayed;
    private LocalDate datelent, datedue;

    public LoanDto(Loan loan) {
        this.id = loan.getId();
        this.lenderName = loan.getLender().getUsername();
        this.borrowerName = loan.getBorrower().getUsername();
        this.amount = loan.getAmount();
        this.amountRepayed = loan.getAmountRepayed();
        this.datelent = loan.getDateLent();
        this.datedue = loan.getDateDue();
    }
}
