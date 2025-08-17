package com.example.tossback.book.repository;

import com.example.tossback.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByMemberId(long memberId);
}
