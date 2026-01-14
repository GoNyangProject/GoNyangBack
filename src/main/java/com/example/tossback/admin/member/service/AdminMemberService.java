package com.example.tossback.admin.member.service;

import com.example.tossback.admin.member.dto.*;

public interface AdminMemberService {
    AdminMemberListResponse getMemberList(String search, int page, int size, String sort, String status);
    AdminMemberMemoResponse getMemberMemo(Long id);
    Boolean updateMemberMemo(AdminMemberMemoRequest adminMemberMemoRequest);
    Boolean deleteMemberMemo(AdminMemberDeleteRequest adminMemberDeleteRequest);
    Boolean updateMemberStatus(AdminMemberStatusRequest adminMemberStatusRequest);
}
