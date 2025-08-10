package com.example.tossback.member.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.member.dto.MemberDTO;
import com.example.tossback.member.repository.MemberRepository;
import com.example.tossback.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    public MemberController(MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }
    @GetMapping("/checkid")
    public ResponseEntity<CommonResponse> checkId(@RequestParam String id){
        return new ResponseEntity<>(new CommonResponse(memberService.checkId(id)), HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<CommonResponse> join(@Validated @RequestBody MemberDTO memberDTO) {
        return new ResponseEntity<>(new CommonResponse(memberService.joinMember(memberDTO)), HttpStatus.CREATED);
    }

}
