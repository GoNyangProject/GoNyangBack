package com.example.tossback.menu.service;

import com.example.tossback.menu.dto.MenuResponseDTO;
import com.example.tossback.mypage.book.dto.BookResponseDTO;

import java.util.List;

public interface MenuService {

    List<MenuResponseDTO> getMenuList();

    List<BookResponseDTO> getBookByMonth(String date);
}
