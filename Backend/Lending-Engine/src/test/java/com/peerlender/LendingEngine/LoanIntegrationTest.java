package com.peerlender.LendingEngine;

import com.google.gson.Gson;
import com.peerlender.LendingEngine.application.dto.LoanRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LoanIntegrationTest {

    private static final String JOHN = "JOHN";
    private static final Gson GSON = new Gson();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void test1(){
        final String baseUrl = "http://localhost:" + serverPort + "/loan";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, JOHN);
        HttpEntity<LoanRequest> request = new HttpEntity<>(new LoanRequest(50, "NT",10,10), httpHeaders);
        restTemplate.postForEntity(baseUrl + "/request",request, String.class);
    }
}
