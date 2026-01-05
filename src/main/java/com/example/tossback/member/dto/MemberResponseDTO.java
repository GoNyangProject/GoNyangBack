package com.example.tossback.member.dto;

import com.example.tossback.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {
    long memberId;
    String userId;
    String username;

    public static MemberResponseDTO from(Member member) {
        MemberResponseDTO dto = new MemberResponseDTO();
        dto.setMemberId(member.getId());
        dto.setUserId(member.getUserId());
        dto.setUsername(member.getUsername());
        return dto;
    }
}
