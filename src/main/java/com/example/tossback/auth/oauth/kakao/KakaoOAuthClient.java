package com.example.tossback.auth.oauth.kakao;

import com.example.tossback.auth.oauth.kakao.dto.KakaoTokenResponse;
import com.example.tossback.auth.oauth.kakao.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoOAuthProperties properties;

    public KakaoTokenResponse getToken(String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("redirect_uri", properties.getRedirectUri());
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> response =
                restTemplate.postForEntity(
                        properties.getTokenUri(),
                        request,
                        KakaoTokenResponse.class
                );

        return response.getBody();
    }

    public KakaoUserResponse getUser(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserResponse> response =
                restTemplate.exchange(
                        properties.getUserInfoUri(),
                        HttpMethod.GET,
                        request,
                        KakaoUserResponse.class
                );

        return response.getBody();
    }
}
