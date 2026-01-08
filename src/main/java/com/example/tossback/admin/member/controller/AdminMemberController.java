package com.example.tossback.admin.member.controller;

import com.example.tossback.admin.member.dto.AdminMemberDeleteRequest;
import com.example.tossback.admin.member.dto.AdminMemberMemoRequest;
import com.example.tossback.admin.member.dto.AdminMemberStatusRequest;
import com.example.tossback.admin.member.service.AdminMemberService;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/member")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam(required = false) String search,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(new CommonResponse(adminMemberService.getMemberList(search, page, size)), HttpStatus.OK);
    }

    @GetMapping("/{id}/memo")
    public ResponseEntity<CommonResponse> getMemberMemo(@PathVariable long id) {
        return new ResponseEntity<>(new CommonResponse(adminMemberService.getMemberMemo(id)), HttpStatus.OK);
    }

    @PostMapping("/memo")
    public ResponseEntity<CommonResponse> updateMemberMemo(@RequestBody AdminMemberMemoRequest adminMemberMemoRequest) {
        return new ResponseEntity<>(new CommonResponse(adminMemberService.updateMemberMemo(adminMemberMemoRequest)), HttpStatus.OK);
    }

    @PostMapping("/memo/delete")
    public ResponseEntity<CommonResponse> deleteMemberMemo(@RequestBody AdminMemberDeleteRequest adminMemberDeleteRequest) {
        return new ResponseEntity<>(new CommonResponse(adminMemberService.deleteMemberMemo(adminMemberDeleteRequest)), HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<CommonResponse> updateMemberStatus(@RequestBody AdminMemberStatusRequest adminMemberStatusRequest) {
        return new ResponseEntity<>(new CommonResponse(adminMemberService.updateMemberStatus(adminMemberStatusRequest)), HttpStatus.OK);
    }
}
