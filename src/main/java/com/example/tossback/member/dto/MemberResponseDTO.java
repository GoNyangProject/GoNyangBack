package com.example.tossback.member.dto;

import com.example.tossback.common.enums.UserRoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {
    long memberId;
    String userId;
    String username;
    String userType;
}
