package com.example.tossback.mypage.accountInfo.dto;

import com.example.tossback.mypage.accountInfo.enums.UserFieldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileModify {
    private String userId;
    private UserFieldType fieldType;
    private String value;
}
