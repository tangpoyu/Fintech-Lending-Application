package com.peerlender.LendingEngine.application.utils;

import com.peerlender.LendingEngine.application.dto.LoanApplicationDto;
import com.peerlender.LendingEngine.application.dto.LoanDto;
import com.peerlender.LendingEngine.domain.entity.Loan;
import com.peerlender.LendingEngine.domain.entity.LoanApplication;

public class Converter {

    public static LoanApplicationDto entityToDto(LoanApplication loanApplication){
        return new LoanApplicationDto(loanApplication);
    }

    public static LoanDto entityToDto(Loan loan){
        return new LoanDto(loan);
    }
}
