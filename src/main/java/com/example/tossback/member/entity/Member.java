package com.example.tossback.member.entity;

import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.common.enums.UserRoleType;
import com.example.tossback.member.enums.AuthProvider;
import com.example.tossback.member.enums.UserStatus;
import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import com.example.tossback.mypage.book.entity.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(name = "username", columnDefinition = "VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci")
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Long birth;
    private Character gender;
    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private AuthProvider provider;
//    @Column(nullable = false)
    private String providerId; // 카카오 user id
    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType = UserRoleType.ROLE_USER;
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.NORMAL;
    private int useCount = 0;
    private long totalSpentAmount = 0;
    private String memo;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PetInfo> petInfoList = new ArrayList<>();

    public static Member createSocialUser(String SocialId, String email, String userName, AuthProvider provider ) {
        Member member = new Member();
        member.setProvider(provider);
        member.setProviderId(SocialId);
        member.setEmail(email);
        member.setUsername(userName);

        member.setUserId(provider + SocialId);
        member.setPassword(null);

        return member;
    }
    //이용완료시 이 메서드 실행
    public void addBookingActivity(long amount) {
        this.useCount += 1;
        this.totalSpentAmount += amount;
    }

}
