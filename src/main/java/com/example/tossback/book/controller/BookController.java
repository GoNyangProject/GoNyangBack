package com.example.tossback.book.controller;

import com.example.tossback.book.service.BookService;
import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<CommonResponse> getBook(@RequestParam("memberId") long memberId) {
        return new ResponseEntity<>(new CommonResponse(bookService.getBookData(memberId)), HttpStatus.OK);
    }

}
