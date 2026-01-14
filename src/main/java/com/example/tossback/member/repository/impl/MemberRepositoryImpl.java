package com.example.tossback.member.repository.impl;

import com.example.tossback.admin.member.dto.AdminMemberList;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.entity.QMember;
import com.example.tossback.member.enums.UserStatus;
import com.example.tossback.member.repository.MemberRepositoryCustom;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminMemberList> findAllAdminMembers(String search, String sort, String status, Pageable pageable) {
        QMember member = QMember.member;

        List<Member> content = queryFactory
                .selectFrom(member)
                .where(searchContains(search), statusEq(status))
                .orderBy(getSortOrder(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        searchContains(search),
                        statusEq(status)
                )
                .fetchOne();
        long total = (count != null) ? count : 0L;

        return new PageImpl<>(content, pageable, total).map(m -> AdminMemberList.builder()
                .id(m.getId())
                .displayName(String.format("%s(%s)", m.getUsername(), m.getUserId()))
                .createdAt(m.getCreatedAt())
                .useCount(m.getUseCount())
                .totalSpentAmount(m.getTotalSpentAmount())
                .status(m.getStatus())
                .memo(m.getMemo())
                .build());
    }

    private BooleanExpression searchContains(String search) {
        if (search == null || search.isEmpty()) return null;
        return QMember.member.username.contains(search)
                .or(QMember.member.userId.contains(search));
    }

    private BooleanExpression statusEq(String status) {
        if (status == null || "ALL".equals(status)) return null;
        return QMember.member.status.eq(UserStatus.valueOf(status));
    }

    private OrderSpecifier<?> getSortOrder(String sort) {
        QMember member = QMember.member;
        return switch (sort) {
            case "NAME" -> member.username.asc();
            case "USE" -> member.useCount.desc();
            case "SUM" -> member.totalSpentAmount.desc();
            default -> member.createdAt.desc();
        };
    }
}
