package com.example.tossback.auth.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.member.dto.MemberResponseDTO;
import com.example.tossback.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class authController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<CommonResponse> getCurrentUser(@CookieValue(value = "accessToken", required = false) String accessToken) {
        if (accessToken == null) {
            return new ResponseEntity<>(new CommonResponse(null, "토큰이 없습니다."), HttpStatus.UNAUTHORIZED);
        }
        MemberResponseDTO memberResponseDTO = memberService.getCurrentUser(accessToken);
        if (memberResponseDTO == null) {
            return new ResponseEntity<>(new CommonResponse(null, "로그인 정보가 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new CommonResponse("로그인 사용자 정보 조회 성공", memberResponseDTO), HttpStatus.OK);

    }


}
