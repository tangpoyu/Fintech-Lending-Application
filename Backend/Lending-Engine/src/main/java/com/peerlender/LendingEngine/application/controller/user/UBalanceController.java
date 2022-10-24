package com.peerlender.LendingEngine.application.controller.user;

import com.peerlender.LendingEngine.application.utils.JwtService;
import com.peerlender.LendingEngine.domain.entity.Money;
import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.service.BalanceService;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/balance")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UBalanceController {

    private final BalanceService balanceService;
    private final JwtService jwtService;

    @PostMapping("/topup")
    public void TopUp(final @RequestBody Money money, KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);
        balanceService.TopUpBalance(money,user);
    }

    @PostMapping("/withdraw")
    public void Withdraw(final @RequestBody Money money, KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);
        balanceService.WithdrawFromBalance(money,user);
    }

}
