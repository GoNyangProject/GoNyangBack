package com.example.tossback.admin.book.service;

import com.example.tossback.admin.book.dto.AdminBookResponse;

import java.util.List;

public interface AdminBookService {
    List<AdminBookResponse> getBookList();
    Boolean cancelBooking(String orderId);
}
