package com.example.tossback.llm.community.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.llm.community.service.AiCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/llm/community")
public class AiCommunityController {
    private final AiCommunityService aiCommunityService;

    @GetMapping("/search")
    public ResponseEntity<CommonResponse> getCommunity(@RequestParam String keyword) {
        return new ResponseEntity<>(new CommonResponse(aiCommunityService.searchCommunity(keyword)), HttpStatus.OK);
    }
}
