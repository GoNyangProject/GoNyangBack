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
    private String userType;
    private String petImagePath;

    public static MemberResponseDTO from(Member member) {
        MemberResponseDTO dto = new MemberResponseDTO();
        dto.setMemberId(member.getId());
        dto.setUserId(member.getUserId());
        dto.setUsername(member.getUsername());
        dto.setRole(member.getUserRoleType().name());
        if (member.getPetInfoList() != null) {
            String path = member.getPetInfoList().stream()
                    .filter(pet -> pet != null && pet.getDeletedAt() == null)
                    .map(pet -> pet.getPetImagePath())
                    .filter(java.util.Objects::nonNull)
                    .findFirst()
                    .orElse(null);
            dto.setPetImagePath(path);
        }
        return dto;
    }
}
