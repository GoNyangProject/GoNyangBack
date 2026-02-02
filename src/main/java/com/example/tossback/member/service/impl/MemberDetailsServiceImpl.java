package com.example.tossback.member.service.impl;

import com.example.tossback.member.dto.CustomMemberDetails;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findWithPetInfosByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        return new CustomMemberDetails(member);
    }


}


