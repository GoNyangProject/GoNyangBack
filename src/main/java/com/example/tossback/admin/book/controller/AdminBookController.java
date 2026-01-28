package com.example.tossback.admin.book.controller;

import com.example.tossback.admin.book.service.AdminBookService;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/book")
public class AdminBookController {

    private final AdminBookService adminBookService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getBookList() {
        return new ResponseEntity<>(new CommonResponse(adminBookService.getBookList()), HttpStatus.OK);
    }
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<CommonResponse> cancelBooking(@PathVariable String orderId) {
        return new ResponseEntity<>(new CommonResponse(adminBookService.cancelBooking(orderId)), HttpStatus.OK);
    }
}
