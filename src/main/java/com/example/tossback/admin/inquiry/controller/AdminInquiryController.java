package com.example.tossback.admin.inquiry.controller;

import com.example.tossback.admin.inquiry.dto.AdminRegisterAnswer;
import com.example.tossback.admin.inquiry.service.AdminInquiryService;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {

    private final AdminInquiryService inquiryService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getInquiryList(@RequestParam(required = false) String search,
                                                         @RequestParam(required = false) String category,
                                                         @RequestParam(required = false) String inquiryStatus,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(new CommonResponse(inquiryService.getInquiryList(search, category, inquiryStatus, page, size)), HttpStatus.OK);
    }
    @PostMapping("/answer")
    public ResponseEntity<CommonResponse> registerAnswer(@RequestBody AdminRegisterAnswer answer) {
        return new ResponseEntity<>(new CommonResponse(inquiryService.registerAnswer(answer)), HttpStatus.OK);
    }

}
