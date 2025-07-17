package com.example.tossback.member.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.member.dto.MemberDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class MemberController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/join")
    public CommonResponse join(@Validated @RequestBody MemberDTO memberDTO) {

        String password = bCryptPasswordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(password);

        return null;
    }

}
