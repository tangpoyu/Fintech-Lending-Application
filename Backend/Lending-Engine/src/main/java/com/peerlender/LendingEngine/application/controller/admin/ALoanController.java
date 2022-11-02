package com.peerlender.LendingEngine.application.controller.admin;

import com.peerlender.LendingEngine.application.dto.LoanApplicationDto;
import com.peerlender.LendingEngine.application.dto.LoanDto;
import com.peerlender.LendingEngine.application.utils.Converter;
import com.peerlender.LendingEngine.domain.entity.Status;
import com.peerlender.LendingEngine.domain.service.LoanApplicationService;
import com.peerlender.LendingEngine.domain.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/admin/loan")
@CrossOrigin(origins = {"http://localhost", "http://localhost:443", "https://localhost"}, maxAge = 3600, allowCredentials = "true")
@AllArgsConstructor
public class ALoanController {

    private final LoanApplicationService loanApplicationService;
    private final LoanService loanService;

    @GetMapping(value = "application/{status}/all")
    public ResponseEntity<List<LoanApplicationDto>> FindAllLoanApplications(@PathVariable final Status status) {
        return ResponseEntity.ok(loanApplicationService.findAllByStatus(status));
    }

    @GetMapping(value = "{status}/all")
    public ResponseEntity<List<LoanDto>> GetAllLoans(@PathVariable final Status status){
        List<LoanDto> loanDtos = loanService.GetLoans(status).stream().map(Converter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }
}
