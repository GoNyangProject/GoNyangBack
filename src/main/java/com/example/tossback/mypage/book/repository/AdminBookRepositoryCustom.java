package com.example.tossback.mypage.book.repository;

import com.example.tossback.admin.book.dto.AdminBookResponse;

import java.util.List;

public interface AdminBookRepositoryCustom {
    List<AdminBookResponse> findAllAdminReservations();
}
