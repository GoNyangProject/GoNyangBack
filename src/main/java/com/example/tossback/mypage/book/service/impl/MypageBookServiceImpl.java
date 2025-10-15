package com.example.tossback.mypage.book.service.impl;

import com.example.tossback.mypage.book.dto.MypageBookResponseDTO;
import com.example.tossback.mypage.book.entity.Book;
import com.example.tossback.mypage.book.repository.BookRepository;
import com.example.tossback.mypage.book.service.MypageBookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MypageBookServiceImpl implements MypageBookService {

    private final BookRepository bookRepository;


    public MypageBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<MypageBookResponseDTO> getBookData(long memberId) {

        List<Book> books = bookRepository.findAllByMemberId(memberId);
        List<MypageBookResponseDTO> bookManageRespons = new ArrayList<>();

        for (Book book : books) {
            MypageBookResponseDTO bookResponseDTO = new MypageBookResponseDTO();
            bookResponseDTO.setOrderId(book.getOrderId());
            bookResponseDTO.setUsername(book.getMember().getUsername());
            bookResponseDTO.setMenuName(book.getMenu().getName());
            bookResponseDTO.setContent(book.getMenu().getContent());
            bookResponseDTO.setBookDate(book.getBookDate());
            bookResponseDTO.setPrice(book.getPrice());
            bookManageRespons.add(bookResponseDTO);
        }
        return bookManageRespons;
    }

}
