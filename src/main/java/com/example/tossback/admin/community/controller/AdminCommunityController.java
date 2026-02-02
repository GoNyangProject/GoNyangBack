package com.example.tossback.admin.community.controller;

import com.example.tossback.admin.community.service.AdminCommunityService;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/community")
public class AdminCommunityController {
    private final AdminCommunityService adminCommunityService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getCommunityList(@RequestParam(required = false) String search,
                                                           @RequestParam(required = false) String status,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(new CommonResponse(adminCommunityService.getCommunityList(search,status,page,size
        )), HttpStatus.OK);
    }
}
