package com.example.tossback.mypage.accountInfo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAndPetInfoResponse {
    private String username;
    private String email;
    private String phoneNumber;
    private List<PetInfoResponse> pets;
}
