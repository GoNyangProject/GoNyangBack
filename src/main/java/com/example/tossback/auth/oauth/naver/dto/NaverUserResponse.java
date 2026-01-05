package com.example.tossback.auth.oauth.naver.dto;

import lombok.Getter;

@Getter
public class NaverUserResponse {
    private NaverUser response;

    @Getter
    public static class NaverUser {
        private String id;
        private String email;
        private String nickname;
    }
}
