package com.example.tossback.member.repository;

import com.example.tossback.member.entity.Member;
import com.example.tossback.member.enums.AuthProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long >,MemberRepositoryCustom {

    Member findByUserId(String userId);

    boolean existsByUserId(String id);

    Member findById(long id);

    Optional<Member> findByProviderAndProviderId(AuthProvider provider, String providerId);

    @EntityGraph(attributePaths = {"petInfoList"})
    Optional<Member> findWithPetInfosByUserId(String userId);

    Page<Member> findByUsernameContainingOrUserIdContaining(String userName, String userId, Pageable pageable);
//    boolean existsByUserId(String userId);
}
