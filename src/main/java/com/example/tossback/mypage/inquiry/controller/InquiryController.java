package com.example.tossback.mypage.inquiry.controller;


import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.mypage.inquiry.dto.InquiryDetailsRequest;
import com.example.tossback.mypage.inquiry.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }
    @GetMapping
    public ResponseEntity<CommonResponse> getInquiry(@RequestParam String userId) {
        return new ResponseEntity<>(new CommonResponse(inquiryService.getInquiry(userId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CommonResponse> getInquiryDetails(@RequestBody InquiryDetailsRequest inquiryDetailsRequest) {
        return new ResponseEntity<>(new CommonResponse(inquiryService.getInquiryDetails(inquiryDetailsRequest)), HttpStatus.OK);
    }

}
