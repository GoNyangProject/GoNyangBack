package com.example.tossback.member.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.member.dto.MemberDTO;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public MemberController(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/join")
    public CommonResponse join(@Validated @RequestBody MemberDTO memberDTO) {

        Member member = new Member();
        String password = bCryptPasswordEncoder.encode(memberDTO.getPassword());
        member.setUserId(memberDTO.getUserId());
        member.setPassword(password);
        member.setUsername(memberDTO.getUsername());
        memberRepository.save(member);

        return null;
    }

}
