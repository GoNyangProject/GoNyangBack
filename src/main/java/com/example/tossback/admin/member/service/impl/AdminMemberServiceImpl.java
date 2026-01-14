package com.example.tossback.admin.member.service.impl;

import com.example.tossback.admin.member.dto.*;
import com.example.tossback.admin.member.service.AdminMemberService;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.enums.UserStatus;
import com.example.tossback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminMemberServiceImpl implements AdminMemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminMemberListResponse getMemberList(String search, int page, int size, String sort, String status) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AdminMemberList> memberPage = memberRepository.findAllAdminMembers(search, sort, status, pageable);
        return AdminMemberListResponse.builder()
                .content(memberPage.getContent())
                .totalPages(memberPage.getTotalPages())
                .totalElements(memberPage.getTotalElements())
                .build();
    }
    @Override
    public AdminMemberMemoResponse getMemberMemo(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: " + id));
        AdminMemberMemoResponse dto = new AdminMemberMemoResponse();
        dto.setMemo(member.getMemo());
        return dto;
    }

    @Override
    public Boolean updateMemberMemo(AdminMemberMemoRequest adminMemberMemoRequest) {
        Member member = memberRepository.findById(adminMemberMemoRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: " + adminMemberMemoRequest.getId()));
        try {
            member.setMemo(adminMemberMemoRequest.getMemo());
            return true;
        } catch (Exception e) {
            log.error("메모 업데이트 실패 {} ", e.getMessage());
            return false;
        }

    }

    @Override
    public Boolean deleteMemberMemo(AdminMemberDeleteRequest adminMemberDeleteRequest) {
        Member member = memberRepository.findById(adminMemberDeleteRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: " + adminMemberDeleteRequest.getId()));
        try {
            member.setMemo("");
            return true;
        } catch (Exception e) {
            log.error("메모 삭제 중 오류 발생: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean updateMemberStatus(AdminMemberStatusRequest adminMemberStatusRequest) {
        Member member = memberRepository.findById(adminMemberStatusRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        try{
            member.setStatus(UserStatus.valueOf(adminMemberStatusRequest.getStatus()));
            return true;
        }catch (Exception e){
            log.error("상태 변경 실패 {}" , e.getMessage());
            return false;
        }
    }
}
