package com.example.tossback.mypage.book.service;

import com.example.tossback.mypage.book.dto.MypageBookResponseDTO;

import java.util.List;

public interface MypageBookService {

    List<MypageBookResponseDTO> getBookData(long memberId);

}
