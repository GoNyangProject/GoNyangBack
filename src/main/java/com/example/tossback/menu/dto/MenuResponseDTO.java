package com.example.tossback.menu.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuResponseDTO {
    private long id;
    private String menuName;
    private int bookCount;
    private String content;
    private int price;
    private BigDecimal score;
}
