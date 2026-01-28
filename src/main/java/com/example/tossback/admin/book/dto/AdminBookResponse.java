package com.example.tossback.admin.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminBookResponse {
    // 1. 예약 기본 정보
    private String orderId;       // 취소/상세 조회를 위한 식별자 (PK가 String이네요!)
    private LocalDateTime bookDate; // 캘린더 점 찍기 및 리스트 시간 표시용
    private int price;            // 예약 당시 가격 (Menu의 현재 가격과 다를 수 있음)

    // 2. 예약자(Member) 정보
    private String memberId;      // 회원 아이디
    private String username;      // 회원 이름
    private String userPhone;     // 회원 연락처 (Member 엔티티에 phone 필드가 있다고 가정)

    // 3. 예약 메뉴(Menu) 정보
    private String menuName;      // 어떤 고양이 관리/서비스인지

    // 4. 상태 (BaseEntity의 deletedAt 등을 활용해 상태를 가공할 수 있음)
    private boolean isCancelled;  // deletedAt이 존재하면 true로 처리
}
