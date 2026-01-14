package com.example.tossback.member.repository;

import com.example.tossback.admin.member.dto.AdminMemberList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<AdminMemberList> findAllAdminMembers(String search, String sort, String status, Pageable pageable);
}
