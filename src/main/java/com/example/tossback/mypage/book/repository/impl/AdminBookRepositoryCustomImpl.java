package com.example.tossback.mypage.book.repository.impl;

import com.example.tossback.admin.book.dto.AdminBookResponse;
import com.example.tossback.mypage.book.repository.AdminBookRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.tossback.member.entity.QMember.member;
import static com.example.tossback.mypage.book.entity.QBook.book;
import static com.example.tossback.menu.entity.QMenu.menu;

@RequiredArgsConstructor
public class AdminBookRepositoryCustomImpl implements AdminBookRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<AdminBookResponse> findAllAdminReservations() {
        return queryFactory
                .select(Projections.constructor(AdminBookResponse.class,
                        book.orderId,
                        book.bookDate,
                        book.price,
                        member.userId,
                        member.username,
                        book.reservedPhone,
                        menu.name,
                        book.deletedAt.isNotNull().as("isCancelled")
                ))
                .from(book)
                .join(book.member, member)
                .join(book.menu, menu)
                .orderBy(book.bookDate.desc())
                .fetch();
    }
}
