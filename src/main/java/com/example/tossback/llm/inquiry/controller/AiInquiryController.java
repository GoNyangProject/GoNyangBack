package com.example.tossback.llm.inquiry.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.llm.inquiry.service.AiInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/llm/inquiry")
public class AiInquiryController {

    private final AiInquiryService aiInquiryService;

    @GetMapping("/my")
    public ResponseEntity<CommonResponse> getInquiryStatus(){
        return new ResponseEntity<>(new CommonResponse(aiInquiryService.getInquiryStatus()), HttpStatus.OK);
    }
}
