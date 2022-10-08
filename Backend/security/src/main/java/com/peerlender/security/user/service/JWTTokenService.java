package com.peerlender.security.user.service;

import ch.qos.logback.core.net.server.Client;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.substringBeforeLast;


@Component
public class JWTTokenService implements TokenService, Clock {


    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();
    String issuer;
    int expirationInSec;
    int clockSkewSec;
    String secretKey;

    public JWTTokenService(@Value("${JWT_ISSUER}") String issuer,
                           @Value("${JWT_EXPIRATION_SEC}") int expirationInSec,
                           @Value("${JWT_CLOCK_SKEW_SEC}") int clockSkewSec,
                           @Value("${JWT_SECRET}") String secretKey) {
        this.issuer = issuer;
        this.expirationInSec = expirationInSec;
        this.clockSkewSec = clockSkewSec;
        this.secretKey = secretKey;
    }

    @Override
    public String permanent(Map<String, String> attributes) { return newToken(attributes, 0); }

    @Override
    public String expiring(Map<String, String> attributes) {
        return newToken(attributes, expirationInSec);
    }

    @Override
    public Map<String, String> untrusted(String token) {
        final JwtParser parser = Jwts.parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec);

        final String noSignature =  substringBeforeLast(token, ".") + ".";
        Claims c = parser.parseClaimsJws(token).getBody();
        Map<String,String> attribute = parseClaims(() -> c);
        return attribute;
    }

    @Override
    public Map<String, String> verify(String token) {
        final JwtParser parser = Jwts.parser()
                .requireIssuer(issuer)
                .setClock(this).setAllowedClockSkewSeconds(clockSkewSec)
                .setSigningKey(secretKey);

        Claims c = parser.parseClaimsJws(token).getBody();
        Map<String,String> attribute = parseClaims(() -> c);
        return null;
    }

    @Override
    public Date now() {
        return null;
    }

    private String newToken(final Map<String,String> attributes, final int expiresInSec){
        final LocalDateTime currentTime = LocalDateTime.now();
        final Claims claims = Jwts
                .claims()
                .setIssuer(issuer)
                .setIssuedAt(Date.from(currentTime.toInstant(ZoneOffset.UTC)));

        if(expiresInSec > 0){
            final LocalDateTime expireAt = currentTime.plusSeconds(expiresInSec);
            claims.setExpiration(Date.from(expireAt.toInstant(ZoneOffset.UTC)));
        }
        claims.putAll(attributes);

        String jwt = Jwts.builder()
                .setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey)
                .compressWith(COMPRESSION_CODEC).compact();
        return jwt;
    }

    private static Map<String ,String> parseClaims(final Supplier<Claims> toClaims){
        try{
            final Claims claims = toClaims.get();
            final ImmutableMap.Builder<String,String> builder = ImmutableMap.builder();
            claims.entrySet().stream().forEach(e -> builder.put(e.getKey(), String.valueOf(e.getValue())));
            return builder.build();
        } catch (final IllegalArgumentException | JwtException e){
            return ImmutableMap.of();
        }
    }
}
