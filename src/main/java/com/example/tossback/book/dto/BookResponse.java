package com.example.tossback.book.dto;

import com.example.tossback.book.entity.Book;
import com.example.tossback.book.entity.Menu;
import com.example.tossback.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookResponse {

    private String username;
    private String menuName;
    private String content;
    private LocalDateTime bookDate;

}
