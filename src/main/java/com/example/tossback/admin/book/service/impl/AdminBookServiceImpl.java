package com.example.tossback.admin.book.service.impl;

import com.example.tossback.admin.book.dto.AdminBookResponse;
import com.example.tossback.admin.book.service.AdminBookService;
import com.example.tossback.mypage.book.entity.Book;
import com.example.tossback.mypage.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminBookServiceImpl implements AdminBookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AdminBookResponse> getBookList() {
        return bookRepository.findAllAdminReservations();
    }

    @Transactional
    @Override
    public Boolean cancelBooking(String orderId) {
        Book book = bookRepository.findByOrderId(orderId);
        if (book == null) {
            throw new IllegalArgumentException("해당 예약번호(" + orderId + ")를 찾을 수 없습니다.");
        }
        if (book.getDeletedAt() != null) {
            throw new IllegalStateException("이미 취소 처리된 예약입니다.");
        }
        book.setDeletedAt(LocalDateTime.now());

        return null;
    }
}
