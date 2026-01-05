package com.example.tossback.auth.oauth.google;


import com.example.tossback.auth.oauth.google.dto.GoogleTokenResponse;
import com.example.tossback.auth.oauth.google.dto.GoogleUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class googleOAuthClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final googleOAuthProperties properties;

    public GoogleTokenResponse getToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("redirect_uri", properties.getRedirectUri());
        body.add("grant_type", "authorization_code");

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<GoogleTokenResponse> response =
                restTemplate.postForEntity(
                        "https://oauth2.googleapis.com/token",
                        request,
                        GoogleTokenResponse.class
                );

        return response.getBody();
    }

    public GoogleUserResponse getUser(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserResponse> response =
                restTemplate.exchange(
                        "https://openidconnect.googleapis.com/v1/userinfo",
                        HttpMethod.GET,
                        request,
                        GoogleUserResponse.class
                );

        return response.getBody();
    }
}

