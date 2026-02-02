package com.example.tossback.member.dto;

import com.example.tossback.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class CustomMemberDetails implements UserDetails {

    private final Member member;

    public CustomMemberDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        if (member.getUserRoleType() != null) {
            collection.add(new SimpleGrantedAuthority(member.getUserRoleType().name()));
        }
        return collection;
    }
    public String getPetImagePath() {
        if (member.getPetInfoList() == null || member.getPetInfoList().isEmpty()) {
            return null;
        }

        return member.getPetInfoList().stream()
                .filter(pet -> pet != null && pet.getDeletedAt() == null)
                .map(pet -> pet.getPetImagePath())
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public long getMemberId() {
        return member.getId();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    public String getUserId() {return member.getUserId();}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomMemberDetails that = (CustomMemberDetails) o;
        return Objects.equals(this.member != null ? this.member.getUsername() : null,
                that.member != null ? that.member.getUsername() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member != null ? member.getUsername() : null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}