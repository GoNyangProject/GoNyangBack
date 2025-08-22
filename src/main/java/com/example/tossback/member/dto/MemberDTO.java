package com.example.tossback.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String username;

    private String userId;

    private String password;

    private String email;

    private String phoneNumber;

    private Character gender;

    private Long birth;

}
