package com.example.tossback.auth.controller;

import com.example.tossback.auth.oauth.OAuthLoginResult;
import com.example.tossback.auth.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class OAuthController {

    private final OAuthService oAuthService;

    @Value("${spring.app.frontend.login-success-uri}")
    private String LoginSuccessUri;
    @Value("${spring.app.frontend.login-fail-uri}")
    private String LoginFailureUri;
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${naver.client-id}")
    private String naverClientId;
    @Value("${naver.redirect-uri}")
    private String naverRedirectUri;
    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${spring.app.cookie.secure}")
    private boolean isSecure;
    @Value("${spring.app.cookie.same-site}")
    private String sameSite;


    @GetMapping("/kakao")
    public void redirectToKakao(HttpServletResponse response) throws IOException {
        String kakaoAuthUrl =
                "https://kauth.kakao.com/oauth/authorize" +
                        "?client_id=" + clientId +
                        "&redirect_uri=" + redirectUri +
                        "&response_type=code";

        response.sendRedirect(kakaoAuthUrl);
    }
    @GetMapping("/naver")
    public void redirectToNaver(HttpServletResponse response) throws IOException {

        String naverAuthUrl =
                "https://nid.naver.com/oauth2.0/authorize" +
                        "?response_type=code" +
                        "&client_id=" + naverClientId +
                        "&redirect_uri=" + naverRedirectUri +
                        "&state=RANDOM_STATE";

        response.sendRedirect(naverAuthUrl);
    }
    @GetMapping("/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {

        String googleAuthUrl =
                "https://accounts.google.com/o/oauth2/v2/auth" +
                        "?client_id=" + googleClientId +
                        "&redirect_uri=" + googleRedirectUri +
                        "&response_type=code" +
                        "&scope=openid email profile";

        response.sendRedirect(googleAuthUrl);
    }

    @GetMapping("/kakao/callback")
    public void kakaoLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws IOException {

        try {
            OAuthLoginResult result = oAuthService.kakaoLogin(code);

            setAuthCookies(response, result);

            response.sendRedirect(LoginSuccessUri);

        } catch (Exception e) {
            log.error("카카오 로그인 실패");
            response.sendRedirect(LoginFailureUri);
        }

    }
    @GetMapping("/naver/callback")
    public void naverLogin(
            @RequestParam String code,
            @RequestParam String state,
            HttpServletResponse response
    ) throws IOException {

        try {
            OAuthLoginResult result = oAuthService.naverLogin(code, state);

            setAuthCookies(response, result);

            response.sendRedirect(LoginSuccessUri);
        } catch (Exception e) {
            log.error("네이버 로그인 실패", e);
            response.sendRedirect(LoginFailureUri);
        }
    }
    @GetMapping("/google/callback")
    public void googleLogin(
            @RequestParam String code,
            HttpServletResponse response
    ) throws IOException {

        try {
            OAuthLoginResult result = oAuthService.googleLogin(code);

            setAuthCookies(response, result);

            response.sendRedirect(LoginSuccessUri);

        } catch (Exception e) {
            log.error("구글 로그인 실패", e);
            response.sendRedirect(LoginFailureUri);
        }
    }
//
//    @GetMapping("/{provider}/callback")
//    public void loginCallback(
//            @PathVariable String provider,
//            @RequestParam String code,
//            @RequestParam(required = false) String state,
//            HttpServletResponse response
//    ) throws IOException {
//        try {
//            OAuthLoginResult result = oAuthService.login(provider, code, state);
//            setAuthCookies(response, result);
//            response.sendRedirect(LoginSuccessUri);
//        } catch (Exception e) {
//            response.sendRedirect(LoginFailureUri);
//        }
//    }


    private void setAuthCookies(HttpServletResponse response, OAuthLoginResult result) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", result.getAccessToken())
                .httpOnly(false)
                .secure(isSecure)
                .path("/")
                .maxAge(60 * 60)
                .sameSite(sameSite)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", result.getRefreshToken())
                .httpOnly(true)
                .secure(isSecure)
                .path("/")
                .maxAge(60 * 60 * 24 * 14)
                .sameSite(sameSite)
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }
}

