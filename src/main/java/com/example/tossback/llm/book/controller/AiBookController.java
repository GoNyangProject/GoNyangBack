package com.example.tossback.llm.book.controller;


import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.llm.book.service.AiBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/llm/book")
@RequiredArgsConstructor
public class AiBookController {

    private final AiBookService llmService;

    @GetMapping("/check")
    public ResponseEntity<CommonResponse> getBookData(@RequestParam(name = "date") String date, @RequestParam(name = "serviceType") String serviceType){
        return new ResponseEntity<>(new CommonResponse(llmService.getBookData(date , serviceType)), HttpStatus.OK);
    }
    @GetMapping("/my-book")
    public ResponseEntity<CommonResponse> getMyReservation() {
        return new ResponseEntity<>(new CommonResponse(llmService.getMyLatestBook()), HttpStatus.OK);
    }
}
