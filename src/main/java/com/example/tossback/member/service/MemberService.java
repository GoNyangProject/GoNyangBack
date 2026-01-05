package com.example.tossback.member.service;

import com.example.tossback.member.dto.CheckId;
import com.example.tossback.member.dto.MemberDTO;
import com.example.tossback.member.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    boolean joinMember(MemberDTO memberDTO);
    CheckId checkId(String id);
    MemberResponseDTO getCurrentUser(String accessToken);
    Boolean logout(HttpServletRequest request, HttpServletResponse response);
}
