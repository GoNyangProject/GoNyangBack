package com.example.tossback.llm.book.service.impl;

import com.example.tossback.llm.book.dto.AiBookResponse;
import com.example.tossback.llm.book.dto.AiMyBookResponse;
import com.example.tossback.llm.book.service.AiBookService;
import com.example.tossback.mypage.book.entity.Book;
import com.example.tossback.mypage.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiBookServiceImpl implements AiBookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public AiBookResponse getBookData(String date, String serviceType) {
        Long menuId = switch (serviceType) {
            case "기본 미용" -> 1L;
            case "목욕 패키지" -> 2L;
            case "발톱 & 브러싱" -> 3L;
            case "스페셜 미용" -> 4L;
            case "스킨케어" -> 5L;
            case "시니어 케어" -> 6L;
            case "프리미엄 패키지" -> 7L;
            default -> 1L;
        };
        List<LocalDateTime> reservedDateTimes = bookRepository.findReservedTimesByDateAndMenu(date, menuId);

        List<String> reservedTimes = reservedDateTimes.stream()
                .map(dt -> dt.format(DateTimeFormatter.ofPattern("HH:mm")))
                .toList();

        List<String> allSlots = List.of("10:00", "12:00", "14:00", "16:00", "18:00");

        List<String> availableSlots = allSlots.stream()
                .filter(slot -> !reservedTimes.contains(slot))
                .collect(Collectors.toList());

        return AiBookResponse.builder()
                .date(date)
                .availableSlots(availableSlots)
                .message(serviceType + " 메뉴의 예약 가능 시간이다냥!")
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public AiMyBookResponse getMyLatestBook() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            log.warn("로그인되지 않은 사용자의 예약 조회 요청이다냥!");
            return AiMyBookResponse.builder()
                    .message("집사님! 로그인을 먼저 해주셔야 예약 내역을 확인할 수 있다냥! 🐾 로그인 후 다시 물어봐달라냥!")
                    .build();
        }
        String userId = auth.getName();
        List<Book> books = bookRepository.findLatestBookByUserId(userId,PageRequest.of(0, 1));

        if (books.isEmpty()) {
            return AiMyBookResponse.builder()
                    .message("집사님, 아직 예약 내역이 없다냥! 첫 예약을 잡아보는 건 어떠냐냥? 🐾")
                    .build();
        }

        Book latestBook = books.get(0);
        return AiMyBookResponse.builder()
                .bookDate(latestBook.getBookDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .menuName(latestBook.getMenu().getName())
                .price(latestBook.getMenu().getPrice())
                .message("집사님의 소중한 예약 내역을 찾아왔다냥!")
                .build();
    }
}
