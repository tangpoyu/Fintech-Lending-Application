package com.peerlender.LendingEngine.application;

import com.peerlender.LendingEngine.application.service.TokenValidationService;
import com.peerlender.LendingEngine.domain.entity.Money;
import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.service.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/balance")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class BalanceController {
    private final BalanceService balanceService;
    private final TokenValidationService tokenValidationService;

    @PostMapping("/topup")
    public void TopUp(final @RequestBody Money money, HttpServletRequest request){
        User user = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        balanceService.TopUpBalance(money,user);
    }

    @PostMapping("/withdraw")
    public void Withdraw(final @RequestBody Money money,HttpServletRequest request){
        User user = tokenValidationService.ValidateTokenAndGetUser(request,"user");
        balanceService.WithdrawFromBalance(money,user);
    }


}
