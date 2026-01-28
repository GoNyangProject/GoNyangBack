package com.example.tossback.admin.block.controller;

import com.example.tossback.admin.block.dto.AdminBlockRequest;
import com.example.tossback.admin.block.servcie.AdminBlockService;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/block")
@RequiredArgsConstructor
public class AdminBlockController {

    private final AdminBlockService adminBlockService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getBlockList(){
        return new ResponseEntity<>(new CommonResponse(adminBlockService.getBlockList()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CommonResponse> blockDate(@RequestBody AdminBlockRequest request){
        return new ResponseEntity<>(new CommonResponse(adminBlockService.blockDate(request)), HttpStatus.OK);
    }
    @DeleteMapping("/{date}")
    public ResponseEntity<CommonResponse> unblockDate(@PathVariable String date){
        return new ResponseEntity<>(new CommonResponse(adminBlockService.unblockDate(date)),HttpStatus.OK);
    }
}
