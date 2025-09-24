package com.example.tossback.mypage.book.service;

import com.example.tossback.mypage.book.dto.BookResponseDTO;

import java.util.List;

public interface BookService {

    List<BookResponseDTO> getBookData(long memberId);

}
