package com.example.tossback.mypage.book.service.impl;

import com.example.tossback.mypage.book.dto.BookResponseDTO;
import com.example.tossback.mypage.book.entity.Book;
import com.example.tossback.mypage.book.repository.BookRepository;
import com.example.tossback.mypage.book.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponseDTO> getBookData(long memberId) {

        List<Book> books = bookRepository.findAllByMemberId(memberId);
        List<BookResponseDTO> bookManageRespons = new ArrayList<>();

        for (Book book : books) {
            BookResponseDTO bookResponseDTO = new BookResponseDTO();
            bookResponseDTO.setUuid(book.getUuid());
            bookResponseDTO.setUsername(book.getMember().getUsername());
            bookResponseDTO.setMenuName(book.getMenu().getName());
            bookResponseDTO.setContent(book.getMenu().getContent());
            bookResponseDTO.setBookDate(book.getBookDate());
            bookManageRespons.add(bookResponseDTO);
        }
        return bookManageRespons;
    }

}
