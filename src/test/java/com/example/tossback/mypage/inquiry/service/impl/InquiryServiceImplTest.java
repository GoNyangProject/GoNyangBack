package com.example.tossback.mypage.inquiry.service.impl;

import com.example.tossback.mypage.inquiry.dto.CreateInquiry;
import com.example.tossback.mypage.inquiry.dto.InquiryDetailsRequest;
import com.example.tossback.mypage.inquiry.dto.InquiryDetailsResponse;
import com.example.tossback.mypage.inquiry.dto.InquiryResponseDTO;
import com.example.tossback.mypage.inquiry.entity.Inquiry;
import com.example.tossback.mypage.inquiry.enums.InquiryStatus;
import com.example.tossback.mypage.inquiry.repository.InquiryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InquiryServiceImplTest {

    @Mock
    private InquiryRepository inquiryRepository;

    @InjectMocks
    private InquiryServiceImpl inquiryService;

    @Test
    @DisplayName("문의 목록 조회 성공")
    void getInquiry_success() {
        //given
        String userId = "ryan0524";

        Inquiry inquiry = new Inquiry();
        inquiry.setId(1L);
        inquiry.setUserId(userId);
        inquiry.setTitle("문의사항");
        inquiry.setCreatedAt(LocalDateTime.now());
        inquiry.setInquiryStatus(InquiryStatus.PENDING);

        Inquiry inquiry2 = new Inquiry();
        inquiry2.setId(2L);
        inquiry2.setUserId(userId);
        inquiry2.setTitle("두 번째 문의");
        inquiry2.setCreatedAt(LocalDateTime.now());
        inquiry2.setInquiryStatus(InquiryStatus.PENDING);

        List<Inquiry> mockInquiryList = new ArrayList<>();
        mockInquiryList.add(inquiry);
        mockInquiryList.add(inquiry2);

        given(inquiryRepository.findByUserId(userId)).willReturn(mockInquiryList);
        //when
        List<InquiryResponseDTO> response = inquiryService.getInquiry(userId);

        //then
        assertThat(response).hasSize(2); // 리스트 크기 확인
        assertThat(response.get(0).getTitle()).isEqualTo("문의사항");
        assertThat(response.get(1).getTitle()).isEqualTo("두 번째 문의");
        assertThat(response.get(0).getStatus()).isEqualTo(InquiryStatus.PENDING);
        assertThat(response.get(1).getStatus()).isEqualTo(InquiryStatus.PENDING);

        verify(inquiryRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("문의목록 아이디없을꼉우")
    void getInquiry_userIdNotFound() {
        // given
        String userId = "ryan0524";
        given(inquiryRepository.findByUserId(userId)).willReturn(Collections.emptyList());

        // when
        List<InquiryResponseDTO> response = inquiryService.getInquiry(userId);

        // then
        assertThat(response).isEmpty();
        assertThat(response).isNotNull();
    }
    
    @Test
    @DisplayName("문의 상세 정보 가져오기")
    void getInquiryDetails_success() {
        //given
        String inquiryNumber = "123456";
        InquiryDetailsRequest request = new InquiryDetailsRequest();
        request.setInquiryNumber(inquiryNumber);
        Inquiry inquiry = new Inquiry();
        inquiry.setId(1L);
        inquiry.setTitle("해당된 문의");
        inquiry.setAnswerUserId("관리자");
        inquiry.setAnswer("해당된 문의 확인");
        inquiry.setAnsweredAt(LocalDateTime.now());
        inquiry.setContent("확인함");
        inquiry.setInquiryNumber(inquiryNumber);
        
        given(inquiryRepository.findByInquiryNumber(inquiryNumber)).willReturn(inquiry);
        //when
        InquiryDetailsResponse response = inquiryService.getInquiryDetails(request);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("해당된 문의");
        assertThat(response.getAnswer()).isEqualTo("해당된 문의 확인");
        assertThat(response.getAnswerUserId()).isEqualTo("관리자");

        verify(inquiryRepository, times(1)).findByInquiryNumber(inquiryNumber);
    }

    @Test
    @DisplayName("문의 상세 정보 가져오기 실패 - 데이터 없음")
    void getInquiryDetails_inquiryDetailsNotFound() {
        // given
        String inquiryNumber = "123456";
        InquiryDetailsRequest request = new InquiryDetailsRequest();
        request.setInquiryNumber(inquiryNumber);

        given(inquiryRepository.findByInquiryNumber(inquiryNumber)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> inquiryService.getInquiryDetails(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 문의를 찾을 수 없습니다.");
    }
    @Test
    @DisplayName("문의 생성 성공")
    void createInquiry_success(){
        //given
        CreateInquiry createInquiry = new CreateInquiry();
        createInquiry.setUserId("ryan0524");
        createInquiry.setTitle("제목");
        createInquiry.setContent("내용");

        given(inquiryRepository.findLastInquiryNumberByPrefix(anyString())).willReturn(null);

        // when
        Boolean result = inquiryService.createInquiry(createInquiry);

        // then
        assertThat(result).isTrue();
        verify(inquiryRepository, times(1)).save(any(Inquiry.class));
    }

    @Test
    @DisplayName("문의 등록 시 번호가 오늘 날짜 형식으로 잘 생성되는지 확인")
    void createInquiry_number_format_check() {
        // given
        CreateInquiry createInquiry = new CreateInquiry();
        String expectedPrefix = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "-";
        given(inquiryRepository.findLastInquiryNumberByPrefix(anyString())).willReturn(null);

        ArgumentCaptor<Inquiry> inquiryCaptor = ArgumentCaptor.forClass(Inquiry.class);

        // when
        inquiryService.createInquiry(createInquiry);

        // then
        verify(inquiryRepository).save(inquiryCaptor.capture());
        Inquiry savedInquiry = inquiryCaptor.getValue();

        assertThat(savedInquiry.getInquiryNumber()).startsWith(expectedPrefix);
        assertThat(savedInquiry.getInquiryNumber()).hasSize(15);
    }

    @Test
    @DisplayName("기존 번호가 있을 때 다음 번호가 잘 생성되는지 테스트")
    void createInquiry_next_number_success() {
        // given
        String todayPrefix = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "-";
        String mockLastNumber = todayPrefix + "000005";

        given(inquiryRepository.findLastInquiryNumberByPrefix(anyString())).willReturn(mockLastNumber);

        ArgumentCaptor<Inquiry> inquiryCaptor = ArgumentCaptor.forClass(Inquiry.class);

        // when
        inquiryService.createInquiry(new CreateInquiry());

        // then
        verify(inquiryRepository).save(inquiryCaptor.capture());
        String savedNumber = inquiryCaptor.getValue().getInquiryNumber();

        assertThat(savedNumber).isEqualTo(todayPrefix + "000006");
    }
}