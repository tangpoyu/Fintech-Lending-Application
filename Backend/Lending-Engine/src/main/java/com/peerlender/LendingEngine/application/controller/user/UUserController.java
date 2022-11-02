package com.peerlender.LendingEngine.application.controller.user;

import com.peerlender.LendingEngine.application.dto.UserBasicData;
import com.peerlender.LendingEngine.application.dto.UserData;
import com.peerlender.LendingEngine.application.utils.JwtService;
import com.peerlender.LendingEngine.domain.entity.Currency;
import com.peerlender.LendingEngine.domain.entity.User;
import lombok.AllArgsConstructor;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "api/user", method = {RequestMethod.GET,RequestMethod.POST})
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost", "http://localhost:443", "https://localhost"}, maxAge = 3600, allowCredentials = "true",methods = {RequestMethod.GET,RequestMethod.POST})
public class UUserController {

    private final JwtService jwtService;

    @GetMapping("/isInitialize")
    public ResponseEntity<Boolean> isInitialize(KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);
        boolean result = true;
        if(user.getAge() == 0) result = false;
        else if(user.getOccupation() == null) result = false;
        return ResponseEntity.ok(result);
    }

    @GetMapping("/userdata")
    public ResponseEntity<UserData> GetUserData(KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);
        UserData userData = new UserData();

        try{
            userData.setAmount(user.getBalance().getMoneyMap().get(Currency.NT).getAmount());
        }catch (Exception e){
            userData.setAmount(0);
        }

        return ResponseEntity.ok(userData);
    }

    @PostMapping("/setting")
    @Transactional
    public ResponseEntity<Void> setBasicData(@RequestBody UserBasicData userBasicData, KeycloakAuthenticationToken authenticationToken){
        User user = jwtService.getUserFromToken(authenticationToken);
        user.setAge(userBasicData.getAge());
        user.setOccupation(userBasicData.getOccupation());
        return ResponseEntity.ok(null);
    }
}
