package com.example.tossback.member.dto;

import com.example.tossback.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {
    private long memberId;
    private String userId;
    private String username;
    private String role;

    public static MemberResponseDTO from(Member member) {
        MemberResponseDTO dto = new MemberResponseDTO();
        dto.setMemberId(member.getId());
        dto.setUserId(member.getUserId());
        dto.setUsername(member.getUsername());
        dto.setRole(member.getUserRoleType().name());
        return dto;
    }
}
