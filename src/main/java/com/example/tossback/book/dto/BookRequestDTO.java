package com.example.tossback.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookRequestDTO {
    private String orderId;
    private long memberId;
    private long menuId;
    private int price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime bookDate;
}
