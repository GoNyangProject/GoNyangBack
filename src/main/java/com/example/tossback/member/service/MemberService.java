package com.example.tossback.member.service;

import com.example.tossback.member.dto.CheckId;
import com.example.tossback.member.dto.MemberDTO;

public interface MemberService {
    boolean joinMember(MemberDTO memberDTO);
    CheckId checkId(String id);
}
