package com.peerlender.LendingEngine.application;

import com.peerlender.LendingEngine.domain.model.Money;
import com.peerlender.LendingEngine.domain.service.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
@AllArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("/topup")
    public void TopUp(final @RequestBody Money money, final @RequestHeader String authorization){
        balanceService.TopUpBalance(money,authorization);
    }

    @PostMapping("/withdraw")
    public void Withdraw(final @RequestBody Money money, final @RequestHeader String authorization){
        balanceService.WithdrawFromBalance(money,authorization);
    }
}
