package com.example.tossback.member.service.impl;

import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.exception.CommonException;
import com.example.tossback.config.jwt.util.CookieUtil;
import com.example.tossback.config.jwt.util.JWTUtil;
import com.example.tossback.config.redis.util.RedisUtil;
import com.example.tossback.member.dto.CheckId;
import com.example.tossback.member.dto.MemberDTO;
import com.example.tossback.member.dto.MemberResponseDTO;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.enums.AuthProvider;
import com.example.tossback.member.repository.MemberRepository;
import com.example.tossback.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    public MemberServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository, JWTUtil jwtUtil, RedisUtil redisUtil) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean joinMember(MemberDTO memberDTO) {
        Member member = new Member();
        if (memberDTO.getPassword() != null && memberDTO.getUsername() != null && memberDTO.getUserId() != null) {
            try {
                member.setUserId(memberDTO.getUserId());
                member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));
                member.setUsername(memberDTO.getUsername());
                member.setEmail(memberDTO.getEmail());
                member.setBirth(memberDTO.getBirth());
                member.setGender(memberDTO.getGender());
                member.setPhoneNumber(memberDTO.getPhoneNumber());
                LocalDateTime now = LocalDateTime.now();
                member.setCreatedAt(now);
                member.setProvider(AuthProvider.LOCAL);
                member.setProviderId("NONE");
                memberRepository.save(member);
            } catch (Exception e) {
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

    @Override
    public MemberResponseDTO getCurrentUser(String accessToken) {
        if (accessToken == null || !jwtUtil.validateToken(accessToken)) {
            return null;
        }

        String userId = jwtUtil.getUserId(accessToken);

        Member user = memberRepository.findByUserId(userId);
        if (user == null) return null;

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setMemberId(user.getId());
        memberResponseDTO.setUsername(user.getUsername());
        memberResponseDTO.setUserId(userId);
        memberResponseDTO.setRole(user.getUserRoleType().name());
        memberResponseDTO.setUserType(user.getUserRoleType().name());

        if (user.getPetInfoList() != null && !user.getPetInfoList().isEmpty()) {
            String imagePath = user.getPetInfoList().get(0).getPetImagePath();
            memberResponseDTO.setPetImagePath(imagePath);
        }

        return memberResponseDTO;
    }

    @Override
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = CookieUtil.getCookieValue(request, "accessToken");
        if (accessToken != null) {
            try {
                String userId = jwtUtil.getUserId(accessToken);
                redisUtil.deleteData("refreshToken:" + userId);
                log.info("로그아웃 성공 - Redis 토큰 삭제 완료: {}", userId);
            } catch(Exception e) {
                log.warn("로그아웃 중 Redis 토큰 삭제 실패(이미 없거나 만료됨): {}", e.getMessage());
                return false;
            }
        }
        return true;
    }
}

