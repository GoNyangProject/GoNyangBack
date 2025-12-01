package com.example.tossback.mypage.book.repository;

import com.example.tossback.mypage.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByOrderId(String orderId);

    List<Book> findAllByMemberId(long memberId);

    @Query("SELECT b FROM Book b WHERE b.bookDate BETWEEN :startDate AND :endDate")
    List<Book> findBooksInMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Book findByOrderId(String orderId);

}
