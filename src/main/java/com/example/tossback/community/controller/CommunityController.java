package com.example.tossback.community.controller;

import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.community.dto.CommunitySaveRequest;
import com.example.tossback.community.service.CommunityService;
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
                                                           @RequestParam BoardCode boardCode) {
        return new ResponseEntity<>(new CommonResponse(communityService.getCommunityList(page, size, boardCode)), HttpStatus.OK);
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
