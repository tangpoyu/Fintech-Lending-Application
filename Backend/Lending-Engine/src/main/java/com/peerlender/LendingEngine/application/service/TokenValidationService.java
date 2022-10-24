//package com.peerlender.LendingEngine.application.service;
//
//import com.google.gson.Gson;
//import com.peerlender.LendingEngine.domain.exeception.UserNotFoundException;
//import com.peerlender.LendingEngine.domain.entity.User;
//import com.peerlender.LendingEngine.domain.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
////import org.springframework.web.reactive.function.client.WebClient;
////import reactor.core.publisher.Mono;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//@Component
//public class TokenValidationService {
//
////    private WebClient webClient;
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final UserRepository userRepository;
//    private final static Gson GSON = new Gson();
//    private final String securityContextBaseUrl;
//
//
//    public TokenValidationService(final UserRepository userRepository,
//                                  @Value("${security.baseUrl}") final String securityContextBaseUrl) {
//        this.userRepository = userRepository;
//        this.securityContextBaseUrl = securityContextBaseUrl;
//        // webClient = WebClient.create(securityContextBaseUrl);
//    }
//
//    public User ValidateTokenAndGetUser(final HttpServletRequest request, final String role){
//
//        Cookie cookie = getCookie(request);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        String cookieJson = cookie.getName() + "=" + cookie.getValue();
//        httpHeaders.add(HttpHeaders.COOKIE, cookieJson);
//        HttpEntity httpEntity = new HttpEntity(role,httpHeaders);
//        ResponseEntity<String> res = restTemplate
//                .exchange(
//                securityContextBaseUrl + role,
//                HttpMethod.POST,
//                httpEntity,
//                String.class
//        );
//
//        if(res.getStatusCode().equals(HttpStatus.OK)){
//            String username = res.getBody();
//            if ("admin".equals(username)){
//                return null;
//            }
//            return userRepository.findById(username)
//                    .orElseThrow(() -> new UserNotFoundException(username));
//        }
//
//        throw new RuntimeException("Invalid token");
//
//        // --------------------------- Reactive -------------------------------------
//
//        //        Mono<String> username =   webClient.post()
////                            .uri(role)
////                            .cookie(cookie.getName(),cookie.getValue())
////                            .retrieve()
////                            .onStatus((httpStatus -> !HttpStatus.NOT_FOUND.equals(HttpStatus.OK)),
////                                    response -> response.bodyToMono(String.class).map(RuntimeException::new))
////                            .bodyToMono(String.class);
////
////        Mono<User> user =  userRepository.findById(username);
////        AtomicReference<User> user = null;
////        username.subscribe(
////                value -> {
////                    user.set(userRepository.findById(value).orElseThrow(() -> new UserNotFoundException(value)));
////                },
////                error -> error.printStackTrace(),
////                () -> System.out.println("completed without a value")
////        );
//
////        return user;
//    }
//
//    private Cookie getCookie(HttpServletRequest request) {
//        Cookie myCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
//                .filter(cookie -> "auth_by_cookie".equals(cookie.getName()))
//                .findFirst().orElse(null);
//        return myCookie;
//    }
//
//}
