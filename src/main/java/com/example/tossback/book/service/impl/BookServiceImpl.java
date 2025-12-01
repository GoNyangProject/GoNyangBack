package com.example.tossback.book.service.impl;

import com.example.tossback.book.dto.BookRequestDTO;
import com.example.tossback.book.service.BookService;
import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.exception.CommonException;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import com.example.tossback.menu.entity.Menu;
import com.example.tossback.menu.repository.MenuRepository;
import com.example.tossback.mypage.book.entity.Book;
import com.example.tossback.mypage.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository, MemberRepository memberRepository, MenuRepository menuRepository) {
        this.memberRepository = memberRepository;
        this.menuRepository = menuRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean addBook(BookRequestDTO bookRequestDTO) {
        Menu menu = menuRepository.findById(bookRequestDTO.getMenuId());
        Member member = memberRepository.findById(bookRequestDTO.getMemberId());

        try {

            boolean bookCheck = bookRepository.existsByOrderId(bookRequestDTO.getOrderId());
            if (!bookCheck) {
                Book book = new Book();
                book.setOrderId(bookRequestDTO.getOrderId());
                book.setPrice(bookRequestDTO.getPrice());
                book.setMember(member);
                book.setMenu(menu);
                book.setBookDate(bookRequestDTO.getBookDate());
                bookRepository.save(book);
            } else {
                throw new CommonException("예약 실패", ErrorCode.FAIL_BOOK);
            }

        } catch (Exception e) {
            throw new CommonException("예약 실패 : " + e.getMessage(), ErrorCode.FAIL_BOOK);
        }
        return true;
    }

    @Override
    public boolean cancelBook(String orderId) {
        LocalDateTime now = LocalDateTime.now();
        Book book = bookRepository.findByOrderId(orderId);
        book.setDeletedAt(now);
        bookRepository.save(book);
        return true;
    }
}
