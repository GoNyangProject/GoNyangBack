package com.example.tossback.admin.block.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class AdminBlockResponse {
    private Long id;
    private LocalDate blockDate;
    private String reason;
}