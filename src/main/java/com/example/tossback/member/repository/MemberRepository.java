package com.example.tossback.member.repository;

import com.example.tossback.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long > {

    Member findByUserId(String userId);

    boolean existsByUserId(String id);

    Member findById(long id);

//    boolean existsByUserId(String userId);
}
