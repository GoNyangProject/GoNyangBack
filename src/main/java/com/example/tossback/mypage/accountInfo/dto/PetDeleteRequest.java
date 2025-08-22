package com.example.tossback.mypage.accountInfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetDeleteRequest {
    private String userId;
    private Long petId;
}
