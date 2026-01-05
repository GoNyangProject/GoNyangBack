package com.example.tossback.auth.oauth.google.dto;

import lombok.Getter;

@Getter
public class GoogleUserResponse {
    private String sub;
    private String email;
    private String name;
    private String picture;
}
