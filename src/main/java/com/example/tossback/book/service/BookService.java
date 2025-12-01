package com.example.tossback.book.service;

import com.example.tossback.book.dto.BookRequestDTO;

public interface BookService {

    boolean addBook(BookRequestDTO bookRequestDTO);

    boolean cancelBook(String orderId);

}
