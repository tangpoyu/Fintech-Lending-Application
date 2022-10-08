package com.peerlender.LendingEngine.domain.exeception;

public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException(Long id) {
        super("Loan " + id + " isn't found");
    }
}
