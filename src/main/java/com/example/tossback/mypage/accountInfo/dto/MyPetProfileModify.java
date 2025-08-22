package com.example.tossback.mypage.accountInfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPetProfileModify {
    private String userId;
    private Long petId;
    private String imageBase64;
    private String name;
    private String breed;
    private Integer age;
    private String gender;
    private String note;
}

