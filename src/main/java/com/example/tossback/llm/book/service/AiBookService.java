package com.example.tossback.llm.book.service;

import com.example.tossback.llm.book.dto.AiBookResponse;
import com.example.tossback.llm.book.dto.AiMyBookResponse;

public interface AiBookService {
    AiBookResponse getBookData(String date, String serviceType);
    AiMyBookResponse getMyLatestBook();
}
