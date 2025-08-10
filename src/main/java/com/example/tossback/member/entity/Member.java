package com.example.tossback.member.entity;

import com.example.tossback.common.entity.BaseEntity;
import com.example.tossback.enums.UserRoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(name = "username")
    private String username;
    private String password;
    private String email;
    private Long phoneNumber;
    private Long birth;
    private String gender;
    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType = UserRoleType.USER;
}
