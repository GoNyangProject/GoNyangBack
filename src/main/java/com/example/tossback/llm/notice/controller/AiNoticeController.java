package com.example.tossback.llm.notice.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.llm.notice.service.AiNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/llm/notices")
@RequiredArgsConstructor
public class AiNoticeController {

    private final AiNoticeService aiNoticeService;

    @GetMapping()
    public ResponseEntity<CommonResponse> getNotice() {
        return new ResponseEntity<>(new CommonResponse(aiNoticeService.getRecentNotice()), HttpStatus.OK);
    }
}
