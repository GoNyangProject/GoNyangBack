package com.example.tossback.mypage.accountInfo.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.example.tossback.mypage.accountInfo.enums.UserFieldType;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import com.example.tossback.mypage.accountInfo.dto.*;
import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import com.example.tossback.mypage.accountInfo.repository.PetInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountInfoServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PetInfoRepository petInfoRepository;

    @InjectMocks
    private AccountInfoServiceImpl accountInfoService;

    @Test
    @DisplayName("유저와 펫 목록 조회")
    void getUserAndPetInfo_Success() {
        // 1. Given (상황 설정)
        String userId = "ryan0524";

        PetInfo pet1 = new PetInfo();
        pet1.setId(1L);
        pet1.setPetName("냥이1");
        pet1.setPetAge(3);
        pet1.setPetGender('M');
        pet1.setCatBreed("치즈태비");

        Member member = new Member();
        member.setUserId(userId);
        member.setUsername("강인구");
        member.setEmail("ryan@naver.com");
        member.setPetInfoList(List.of(pet1));

        given(memberRepository.findByUserId(userId)).willReturn(member);

        // 2. When (실행)
        UserAndPetInfoResponse response = accountInfoService.getUserAndPetInfo(userId);

        // 3. Then (검증)
        assertThat(response.getUsername()).isEqualTo("강인구");
        assertThat(response.getPets()).hasSize(1);
        assertThat(response.getPets().get(0).getPetName()).isEqualTo("냥이1");
        assertThat(response.getPets().get(0).getPetAge()).isEqualTo("3");
    }

    @Test
    @DisplayName("유저 프로필 수정")
    void modifyProfile_Name_Success() {
        // Given (상황 설정)
        String userId = "ryan0524";
        Member mockMember = new Member();
        mockMember.setUserId(userId);
        mockMember.setUsername("강인구");

        UserProfileModify request = new UserProfileModify();
        request.setUserId(userId);
        request.setFieldType(UserFieldType.NAME);
        request.setValue("김성우");

        given(memberRepository.findByUserId(userId)).willReturn(mockMember);

        // When (실행)
        UserModifyResponse response = accountInfoService.modifyProfile(request);

        // Then (검증)
        assertThat(response.getStatus()).isEqualTo("ok");
        assertThat(mockMember.getUsername()).isEqualTo("김성우");
    }
    @Test
    @DisplayName("유저 프로필 수정 실패")
    void modifyProfile_UserNotFound() {
        // Given
        UserProfileModify request = new UserProfileModify();
        request.setUserId("non-exist");

        given(memberRepository.findByUserId("non-exist")).willReturn(null);

        // When & Then (에러가 터지는지 확인)
        assertThatThrownBy(() -> accountInfoService.modifyProfile(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 유저를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("펫 프로필 변경")
    void modifyPetProfile_Success() {
        //given (상황)
        String userId = "ryan0524";

        Member member = new Member();
        member.setUserId(userId);
        member.setId(1L);

        PetInfo pet1 = new PetInfo();
        pet1.setId(1L);
        pet1.setPetName("룰루");
        pet1.setCatBreed("스코티시폴드");
        pet1.setPetAge(3);
        pet1.setPetGender('M');
        pet1.setCatNotes("기여워");
        pet1.setMember(member);

        MyPetProfileModify request = new MyPetProfileModify();
        request.setUserId(userId);
        request.setPetId(1L);
        request.setName("수정된룰루");
        request.setBreed("스코티시폴드");
        request.setAge(5);
        request.setGender("F");
        request.setNote("더 기여워짐");
        request.setImageBase64("/old/path/image.jpg");

        given(memberRepository.findByUserId(userId)).willReturn(member);
        given(petInfoRepository.findById(1L)).willReturn(Optional.of(pet1));
        //when (실행)
        MyPetProfileModifyResponse response = accountInfoService.modifyPetProfile(request);
        //then (검증)
        assertThat(response.getStatus()).isEqualTo("ok");
        assertThat(pet1.getPetName()).isEqualTo("수정된룰루");
        assertThat(pet1.getPetAge()).isEqualTo(5);
    }

    @Test
    @DisplayName("펫 프로필 변경 실패")
    void modifyPetProfile_UserNotFound() {
        //given
        String userId = "non-user";
        MyPetProfileModify request = new MyPetProfileModify();
        request.setUserId(userId);
        given(memberRepository.findByUserId(userId)).willReturn(null);
        //when
        //then
        assertThatThrownBy(()-> accountInfoService.modifyPetProfile(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 유저를 찾을 수 없습니다.");
    }
    @Test
    @DisplayName("펫 프로필 변경 실패(펫을 찾을 수 없을 때")
    void modifyPetProfile_PetNotFound(){
        //given
        String userId = "ryan0524";
        Member member = new Member();
        member.setUserId(userId);

        MyPetProfileModify request = new MyPetProfileModify();
        request.setUserId(userId);
        request.setPetId(999L);

        given(memberRepository.findByUserId(userId)).willReturn(member);
        given(petInfoRepository.findById(999L)).willReturn(Optional.empty());

        //when and then
        assertThatThrownBy(()-> accountInfoService.modifyPetProfile(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 펫을 찾을 수 없습니다.");

    }
    @Test
    @DisplayName("펫 삭제 성공")
    void deletePet_Success() {
        //given
        String userId = "ryan0524";
        Member member = new Member();
        member.setUserId(userId);
        member.setId(1L);

        PetInfo pet1 = new PetInfo();
        pet1.setId(1L);
        pet1.setPetName("룰루");
        pet1.setCatBreed("스코티시 폴드");
        pet1.setPetAge(3);
        pet1.setPetGender('M');
        pet1.setCatNotes("귀여움");
        pet1.setMember(member);

        given(memberRepository.findByUserId(userId)).willReturn(member);
        given(petInfoRepository.findById(1L)).willReturn(Optional.of(pet1));

        PetDeleteRequest request = new PetDeleteRequest();
        request.setUserId(userId);
        request.setPetId(1L);
        //when
        PetDeleteResponse response = accountInfoService.deleteProfilePet(request);
        //then
        assertThat(response.getStatus()).isEqualTo(200);
        verify(petInfoRepository, times(1)).delete(pet1);
    }
    @Test
    @DisplayName("펫 삭제 실패")
    void deletePet_NotMyPet() {
        // Given
        String userId = "ryan0524";
        Member me = new Member();
        me.setId(1L);
        me.setUserId(userId);

        Member someoneElse = new Member();
        someoneElse.setId(2L);

        PetInfo othersPet = new PetInfo();
        othersPet.setMember(someoneElse);

        PetDeleteRequest request = new PetDeleteRequest();
        request.setUserId(userId);
        request.setPetId(99L);

        given(memberRepository.findByUserId(userId)).willReturn(me);
        given(petInfoRepository.findById(99L)).willReturn(java.util.Optional.of(othersPet));

        // When & Then
        assertThatThrownBy(() -> accountInfoService.deleteProfilePet(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 유저의 펫이 아닙니다.");
    }
}