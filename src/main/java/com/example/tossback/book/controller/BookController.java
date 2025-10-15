package com.example.tossback.book.controller;

import com.example.tossback.book.dto.BookRequestDTO;
import com.example.tossback.book.service.BookService;
import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<CommonResponse> getBook(@RequestBody BookRequestDTO bookRequestDTO) {
        return new ResponseEntity<>(new CommonResponse(bookService.addBook(bookRequestDTO)), HttpStatus.OK);
    }

}
