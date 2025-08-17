package com.example.tossback.book.service;

import com.example.tossback.book.dto.BookResponse;

import java.util.List;

public interface BookService {

    List<BookResponse> getBookData(long memberId);

}
