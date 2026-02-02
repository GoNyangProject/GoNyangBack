package com.example.tossback.admin.petInfo.controller;

import com.example.tossback.admin.petInfo.service.AdminPetInfoService;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/pet")
@RequiredArgsConstructor
public class AdminPetInfoController {
    private final AdminPetInfoService adminPetService;

    @GetMapping("/list/{memberId}")
    public ResponseEntity<CommonResponse> getPetList(@PathVariable String memberId) {
        return new ResponseEntity<>(new CommonResponse(adminPetService.getPetList(memberId)), HttpStatus.OK);
    }
}
