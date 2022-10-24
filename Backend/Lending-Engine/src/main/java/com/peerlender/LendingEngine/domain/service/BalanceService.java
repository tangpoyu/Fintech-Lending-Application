package com.peerlender.LendingEngine.domain.service;

import com.peerlender.LendingEngine.domain.entity.Money;
import com.peerlender.LendingEngine.domain.entity.User;
import com.peerlender.LendingEngine.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
// import reactor.core.publisher.Mono;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class BalanceService {

     private final UserRepository userRepository;

     @Transactional
     public void TopUpBalance(final Money money, User user){
         user.getBalance().TopUp(money);

         // ------------ Reactive --------------------

//         Mono<User> user = userRepository.findById(authToken);
//         user.subscribe(
//                 value -> value.getBalance().TopUp(money),
//                 error -> new UserNotFoundException(authToken)
//         );
     }

     @Transactional
     public void WithdrawFromBalance(final Money money, User user){
         user.getBalance().Withdraw(money);
         // ------------ Reactive --------------------

//         Mono<User> user = userRepository.findById(authToken);
//         user.subscribe(
//                 value -> value.getBalance().Withdraw(money),
//                 error -> new UserNotFoundException(authToken)
//         );
     }
}
