package com.example.tossback.board.community.controller;

import com.example.tossback.board.community.dto.CommunitySaveRequest;
import com.example.tossback.board.community.service.CommunityService;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getCommunityList(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam BoardCode boardCode,
                                                           @RequestParam(required = false) String search,
                                                           @RequestParam(required = false, defaultValue = "latest") String sort) {
        return new ResponseEntity<>(new CommonResponse(communityService.getCommunityList(page, size, boardCode, search, sort)), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(new CommonResponse(communityService.uploadFile(file)), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<CommonResponse> saveCommunity(@RequestBody CommunitySaveRequest saveRequest) {
        return new ResponseEntity<>(new CommonResponse(communityService.saveCommunity(saveRequest)), HttpStatus.OK);
    }
}
