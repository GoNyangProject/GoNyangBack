package com.example.tossback.llm.book.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AiBookResponse {
    private String date;
    private List<String> availableSlots;
    private String message;
}
