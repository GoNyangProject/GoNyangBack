package com.example.tossback.auth.oauth.naver;

import com.example.tossback.auth.oauth.naver.dto.NaverTokenResponse;
import com.example.tossback.auth.oauth.naver.dto.NaverUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverOAuthClient{

    private final RestTemplate restTemplate = new RestTemplate();
    private final NaverOAuthProperties properties;

    public NaverTokenResponse getToken(String code, String state) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> request = new HttpEntity<>(params, headers);

        ResponseEntity<NaverTokenResponse> response =
                restTemplate.postForEntity(
                        properties.getTokenUri(),
                        request,
                        NaverTokenResponse.class
                );

        return response.getBody();
    }

    public NaverUserResponse getUser(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<NaverUserResponse> response =
                restTemplate.exchange(
                        properties.getUserInfoUri(),
                        HttpMethod.GET,
                        request,
                        NaverUserResponse.class
                );

        return response.getBody();
    }
}

