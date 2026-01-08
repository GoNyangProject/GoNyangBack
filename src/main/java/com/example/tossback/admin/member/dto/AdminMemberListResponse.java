package com.example.tossback.admin.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminMemberListResponse {
    private List<AdminMemberList> content;
    private int totalPages;
    private long totalElements;
}
