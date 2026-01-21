package com.example.tossback.community.controller;

import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.book.dto.BookRequestDTO;
import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getCommunityList(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam BoardCode boardCode) {
        return new ResponseEntity<>(new CommonResponse(communityService.getCommunityList(page, size, boardCode)), HttpStatus.OK);
    }
}
