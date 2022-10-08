package com.peerlender.security2.configuration;

import com.peerlender.security2.service.JwtService;
import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class UserAuthProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(PreAuthenticatedAuthenticationToken.class, authentication);

        UserDetails userDetails = null;
        userDetails = jwtService.loadUserByToken((String)authentication.getPrincipal());

//        if(authentication instanceof UsernamePasswordAuthenticationToken){
//            try {
//                jwtService.authenticate((String)authentication.getPrincipal(),(String) authentication.getCredentials());
//                userDetails = jwtService.loadUserByUsername((String)authentication.getPrincipal());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
//            userDetails = jwtService.loadUserByToken((String)authentication.getPrincipal());
//        }

        if(userDetails == null){
            return  null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        boolean isSupport = (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
        return isSupport;
    }
}
