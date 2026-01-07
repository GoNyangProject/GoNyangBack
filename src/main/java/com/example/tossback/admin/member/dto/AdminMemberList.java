package com.example.tossback.admin.member.dto;

import com.example.tossback.member.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AdminMemberList {

    private Long id;
    private String displayName;
    private LocalDateTime createdAt;
    private int useCount;
    private long totalSpentAmount;
    private UserStatus status;
    private String memo;

}
