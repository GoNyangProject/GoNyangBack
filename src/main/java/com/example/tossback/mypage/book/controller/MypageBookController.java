package com.example.tossback.mypage.book.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.mypage.book.service.MypageBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
public class MypageBookController {

    private final MypageBookService bookService;

    public MypageBookController(MypageBookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/book")
    public ResponseEntity<CommonResponse> getBook(@RequestParam("memberId") long memberId) {
        return new ResponseEntity<>(new CommonResponse(bookService.getBookData(memberId)), HttpStatus.OK);
    }

}
