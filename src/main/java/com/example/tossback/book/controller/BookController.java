package com.example.tossback.book.controller;

import com.example.tossback.book.dto.BookRequestDTO;
import com.example.tossback.book.service.BookService;
import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<CommonResponse> addBook(@RequestBody BookRequestDTO bookRequestDTO) {
        return new ResponseEntity<>(new CommonResponse(bookService.addBook(bookRequestDTO)), HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<CommonResponse> cancelBook(@RequestBody BookRequestDTO bookRequestDTO) {
        System.out.println(bookRequestDTO);
        return new ResponseEntity<>(new CommonResponse(bookService.cancelBook(bookRequestDTO.getOrderId())), HttpStatus.OK);
    }

}
