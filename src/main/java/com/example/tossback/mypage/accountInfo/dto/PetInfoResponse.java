package com.example.tossback.mypage.accountInfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetInfoResponse {
    private Long petId;
    private String petImagePath;
    private String petName;
    private String catBreed;
    private String petAge;
    private String petGender;
    private String catNotes;
}
