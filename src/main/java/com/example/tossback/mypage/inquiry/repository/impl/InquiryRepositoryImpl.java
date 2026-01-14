package com.example.tossback.mypage.inquiry.repository.impl;

import com.example.tossback.mypage.inquiry.entity.Inquiry;
import com.example.tossback.mypage.inquiry.enums.InquiryCategory;
import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import com.example.tossback.mypage.inquiry.repository.InquiryRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.tossback.mypage.inquiry.entity.QInquiry.inquiry;

@Slf4j
@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Inquiry> findInquiryListWithFilters(String search, String category, String status, Pageable pageable) {

        List<Inquiry> content = queryFactory
                .selectFrom(inquiry)
                .where(
                        categoryEq(category),
                        statusEq(status),
                        searchContains(search)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(inquiry.inquiryStatus.asc(), inquiry.createdAt.desc())
                .fetch();

        Long count = queryFactory
                .select(inquiry.count())
                .from(inquiry)
                .where(
                        categoryEq(category),
                        statusEq(status),
                        searchContains(search)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count != null ? count : 0L);
    }

    private BooleanExpression categoryEq(String category) {
        if (!StringUtils.hasText(category) || "ALL".equals(category)) return null;
        try {
            return inquiry.category.eq(InquiryCategory.valueOf(category));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private BooleanExpression statusEq(String status) {
        if (!StringUtils.hasText(status) || "ALL".equals(status)) return null;
        try {
            return inquiry.inquiryStatus.eq(InquiryStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private BooleanExpression searchContains(String search) {
        if (!StringUtils.hasText(search)) return null;
        return inquiry.title.containsIgnoreCase(search)
                .or(inquiry.userId.containsIgnoreCase(search))
                .or(inquiry.inquiryNumber.containsIgnoreCase(search));
    }
}
