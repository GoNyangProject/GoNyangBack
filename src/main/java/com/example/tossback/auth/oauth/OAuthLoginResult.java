package com.example.tossback.auth.oauth;

import com.example.tossback.member.dto.MemberResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthLoginResult {
    private String accessToken;
    private String refreshToken;
    private MemberResponseDTO user;
}

