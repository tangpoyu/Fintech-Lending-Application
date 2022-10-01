package com.peerlender.LendingEngine.domain.service;

import com.peerlender.LendingEngine.domain.exeception.UserNotFoundException;
import com.peerlender.LendingEngine.domain.model.Money;
import com.peerlender.LendingEngine.domain.model.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.security.PublicKey;

@Component
@AllArgsConstructor
public class BalanceService {
     private final UserRepository userRepository;

     @Transactional
     public void TopUpBalance(final Money money, String authToken){
         User user = userRepository.findById(authToken)
                 .orElseThrow(() -> new UserNotFoundException(authToken));
         user.getBalance().TopUp(money);
     }

     @Transactional
     public void WithdrawFromBalance(final Money money, String authToken){
         User user = userRepository.findById(authToken)
                 .orElseThrow(() -> new UserNotFoundException(authToken));
         user.getBalance().Withdraw(money);
     }
}
