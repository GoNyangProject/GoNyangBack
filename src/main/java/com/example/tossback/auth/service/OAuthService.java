package com.example.tossback.auth.service;

import com.example.tossback.auth.oauth.OAuthLoginResult;
import com.example.tossback.auth.oauth.google.dto.GoogleTokenResponse;
import com.example.tossback.auth.oauth.google.dto.GoogleUserResponse;
import com.example.tossback.auth.oauth.kakao.KakaoOAuthClient;
import com.example.tossback.auth.oauth.kakao.dto.KakaoTokenResponse;
import com.example.tossback.auth.oauth.kakao.dto.KakaoUserResponse;
import com.example.tossback.auth.oauth.naver.NaverOAuthClient;
import com.example.tossback.auth.oauth.google.googleOAuthClient;
import com.example.tossback.auth.oauth.naver.dto.NaverTokenResponse;
import com.example.tossback.auth.oauth.naver.dto.NaverUserResponse;
import com.example.tossback.common.enums.JwtTokenType;
import com.example.tossback.config.jwt.util.JWTUtil;
import com.example.tossback.config.redis.util.RedisUtil;
import com.example.tossback.member.dto.MemberResponseDTO;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.enums.AuthProvider;
import com.example.tossback.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class OAuthService {

    private final KakaoOAuthClient kakaoOAuthClient;
    private final NaverOAuthClient naverOAuthClient;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final googleOAuthClient googleOAuthClient;

    @Value("${refresh-expiration-hours}")
    private Long refreshExpirationHours;

    public OAuthService(KakaoOAuthClient kakaoOAuthClient, NaverOAuthClient naverOAuthClient, MemberRepository memberRepository, JWTUtil jwtUtil, RedisUtil redisUtil, googleOAuthClient googleOAuthClient) {
        this.kakaoOAuthClient = kakaoOAuthClient;
        this.naverOAuthClient = naverOAuthClient;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        this.googleOAuthClient = googleOAuthClient;
    }

    public OAuthLoginResult kakaoLogin(String code) {

        KakaoTokenResponse token = kakaoOAuthClient.getToken(code);

        KakaoUserResponse userInfo = kakaoOAuthClient.getUser(token.getAccessToken());

        AuthProvider provider = AuthProvider.KAKAO;

        Long Id = userInfo.getId();
        String email = userInfo.getKakaoAccount() != null
                ? userInfo.getKakaoAccount().getEmail()
                : null;
        String userName = Objects.requireNonNull(userInfo.getKakaoAccount()).getProfile().getNickname();

        Member member = getOrSave(provider, Id.toString(), email, userName);

        String userId = member.getUserId();
        String role = member.getUserRoleType().name();

        String accessToken = jwtUtil.createToken(userId, role, JwtTokenType.ACCESS);
        String refreshToken = jwtUtil.createToken(userId, null, JwtTokenType.REFRESH);

        redisSetData(userId, refreshToken);

        MemberResponseDTO userData = MemberResponseDTO.from(member);

        return new OAuthLoginResult(accessToken, refreshToken, userData);
    }

    public OAuthLoginResult naverLogin(String code, String state) {

        NaverTokenResponse token = naverOAuthClient.getToken(code, state);

        NaverUserResponse userInfo = naverOAuthClient.getUser(token.getAccessToken());

        AuthProvider provider = AuthProvider.NAVER;

        String Id = userInfo.getResponse().getId();
        String email = userInfo.getResponse().getEmail();
        String userName = userInfo.getResponse().getNickname();

        Member member = getOrSave(provider, Id, email, userName);

        String userId = member.getUserId();
        String role = member.getUserRoleType().name();

        String accessToken = jwtUtil.createToken(userId, role, JwtTokenType.ACCESS);
        String refreshToken = jwtUtil.createToken(userId, null, JwtTokenType.REFRESH);

        redisSetData(userId, refreshToken);

        MemberResponseDTO userData = MemberResponseDTO.from(member);

        return new OAuthLoginResult(accessToken, refreshToken, userData);
    }



    public OAuthLoginResult googleLogin(String code) {

        GoogleTokenResponse token = googleOAuthClient.getToken(code);
        GoogleUserResponse userInfo = googleOAuthClient.getUser(token.getAccessToken());
        AuthProvider provider = AuthProvider.GOOGLE;

        String Id = userInfo.getSub();
        String email = userInfo.getEmail();
        String userName = userInfo.getName();

        Member member = getOrSave(provider, Id, email, userName);

        String userId = member.getUserId();
        String role = member.getUserRoleType().name();

        String accessToken = jwtUtil.createToken(userId, role, JwtTokenType.ACCESS);
        String refreshToken = jwtUtil.createToken(userId, null, JwtTokenType.REFRESH);

        redisSetData(userId, refreshToken);

        MemberResponseDTO userData = MemberResponseDTO.from(member);

        return new OAuthLoginResult(accessToken, refreshToken, userData);
    }

    private void redisSetData(String userId, String refreshToken) {
        redisUtil.setDataExpire(
                "refreshToken:" + userId,
                refreshToken,
                refreshExpirationHours * 60 * 60
        );
    }
    private Member getOrSave(AuthProvider provider, String Id, String email, String userName) {
        return memberRepository
                .findByProviderAndProviderId(provider, Id)
                .orElseGet(() -> memberRepository.save(
                        Member.createSocialUser(Id, email, userName, provider)
                ));
    }
}


