package com.example.tossback.member.service.impl;

import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.exception.CommonException;
import com.example.tossback.member.dto.CheckId;
import com.example.tossback.member.dto.MemberDTO;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import com.example.tossback.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean joinMember(MemberDTO memberDTO) {
        Member member = new Member();
        if(memberDTO.getPassword() != null && memberDTO.getUsername()!=null && memberDTO.getUserId() !=null) {
            try{
                member.setUserId(memberDTO.getUserId());
                member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));
                member.setUsername(memberDTO.getUsername());
                member.setEmail(memberDTO.getEmail());
                member.setBirth(memberDTO.getBirth());
                member.setGender(memberDTO.getGender());
                member.setPhoneNumber(memberDTO.getPhoneNumber());
                LocalDateTime now = LocalDateTime.now();
                member.setCreatedAt(now);
                memberRepository.save(member);
            }catch (Exception e){
                log.error(e.getMessage());
                throw new CommonException("회원가입 실패: " + e.getMessage(), ErrorCode.FAIL_JOIN);
            }
        }
        return true;
    }
    @Override
    public CheckId checkId(String id) {
       boolean exists = memberRepository.existsByUserId(id);
       return new CheckId(!exists);
    }
}
