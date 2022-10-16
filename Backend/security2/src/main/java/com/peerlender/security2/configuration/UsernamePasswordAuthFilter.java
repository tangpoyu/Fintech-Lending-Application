package com.peerlender.security2.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peerlender.security2.entity.JwtRequest;
import com.peerlender.security2.service.JwtService;
import com.peerlender.security2.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;


// 5
@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {


        if("/login".equals(request.getServletPath())
                && HttpMethod.POST.matches(request.getMethod())){
            JwtRequest credential = MAPPER.readValue(request.getInputStream(), JwtRequest.class);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            credential.getUsername(),
                            credential.getPassword()
                    )
            );
        }

        filterChain.doFilter(request,response);

//        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        String jwtToken = null;
//        String username = null;
//
//        if(header != null && header.startsWith("Bearer ")){
//            jwtToken = header.substring(7);
//            try{
//                username = jwtUtil.getUserNameFromToken(jwtToken);
//            }catch (IllegalArgumentException e){
//                System.out.println("Unable to get JWT token.");
//            }catch (ExpiredJwtException e){
//                System.out.println("Jwt token is expired.");
//            }
//        } else {
//            System.out.println("Jwt token does not start with Bearer");
//        }
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//           UserDetails userDetails = jwtService.loadUserByUsername(username);
//           if(jwtUtil.validateToken(jwtToken, userDetails)){
//                UsernamePasswordAuthenticationToken token =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//               SecurityContextHolder.getContext().setAuthentication(token);
//           }
//        }
//
//
//        filterChain.doFilter(request,response);
    }
}
