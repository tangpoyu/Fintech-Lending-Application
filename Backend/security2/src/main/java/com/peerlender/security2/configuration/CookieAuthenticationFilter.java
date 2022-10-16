package com.peerlender.security2.configuration;

import com.google.gson.Gson;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CookieAuthenticationFilter extends OncePerRequestFilter {

    public static final String COOKIE_NAME = "auth_by_cookie";
    private final static Gson GSON = new Gson();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        if(!"/register".equals(request.getServletPath()) && !"/login".equals(request.getServletPath())){
            String[] rawCookieParams = request.getHeader(HttpHeaders.COOKIE).split(";");
            for(String rawCookieNameAndValue :rawCookieParams)
            {
                String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
                if(COOKIE_NAME.equals(rawCookieNameAndValuePair[0])){
                    SecurityContextHolder.getContext().setAuthentication(
                            new PreAuthenticatedAuthenticationToken(rawCookieNameAndValuePair[1],null)
                    );
                }
            }

//            Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
//                    .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
//                    .findFirst()
//                    .ifPresent(cookie ->
//                            SecurityContextHolder.getContext().setAuthentication(
//                                    new PreAuthenticatedAuthenticationToken(cookie.getValue(),null)
//                            )
//                    );
        }

        filterChain.doFilter(request,response);
    }
}
