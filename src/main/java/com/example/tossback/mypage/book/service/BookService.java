package com.example.tossback.mypage.book.service;

import com.example.tossback.mypage.book.dto.BookResponse;

import java.util.List;

public interface BookService {

    List<BookResponse> getBookData(long memberId);

}
