package com.peerlender.security2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// 6
@Component
public class JwtUtil {

    private static final String SECRET_KEN = "tangpoyu";
    private static final int TOKEN_VALIDITY = 3600 * 5;

    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims =  getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEN).parseClaimsJws(token).getBody();
    }

    public boolean validateToken( String token, UserDetails userDetails){
        String username =  getUserNameFromToken(token);
        boolean c1 = username.equals(userDetails.getUsername());
        boolean c2 = isTokenExpired(token);
        return c1 && !c2;
    }

    private boolean isTokenExpired(String token){
        Date expirationDateFromToken = getExpirationDateFromToken(token);
        Date now = new Date();
        return expirationDateFromToken.before(now);
    }

    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEN)
                .compact();
    }

}
