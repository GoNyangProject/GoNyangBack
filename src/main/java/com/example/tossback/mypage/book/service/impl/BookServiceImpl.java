package com.example.tossback.mypage.book.service.impl;

import com.example.tossback.mypage.book.dto.BookResponse;
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
    public List<BookResponse> getBookData(long memberId) {

        List<Book> books = bookRepository.findAllByMemberId(memberId);
        List<BookResponse> bookResponses = new ArrayList<>();

        for (Book book : books) {
            BookResponse bookResponse = new BookResponse();
            bookResponse.setUuid(book.getUuid());
            bookResponse.setUsername(book.getMember().getUsername());
            bookResponse.setMenuName(book.getMenu().getName());
            bookResponse.setContent(book.getMenu().getContent());
            bookResponse.setBookDate(book.getBookDate());
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }

}
