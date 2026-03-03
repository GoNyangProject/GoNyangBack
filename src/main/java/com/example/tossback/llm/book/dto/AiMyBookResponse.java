package com.example.tossback.llm.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiMyBookResponse {
    private String bookDate;
    private String menuName;
    private Integer price;
    private String message;
}