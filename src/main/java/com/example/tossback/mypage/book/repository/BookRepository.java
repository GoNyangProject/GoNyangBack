package com.example.tossback.mypage.book.repository;

import com.example.tossback.mypage.book.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, AdminBookRepositoryCustom {

    boolean existsByOrderId(String orderId);

    List<Book> findAllByMemberId(long memberId);

    @Query("SELECT b FROM Book b WHERE b.bookDate BETWEEN :startDate AND :endDate")
    List<Book> findBooksInMonth(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Book findByOrderId(String orderId);

    @Query("SELECT b.bookDate FROM Book b WHERE FUNCTION('DATE_FORMAT', b.bookDate, '%Y-%m-%d') = :date AND b.menu.id = :menuId")
    List<LocalDateTime> findReservedTimesByDateAndMenu(@Param("date") String date, @Param("menuId") Long menuId);

    @Query("SELECT b FROM Book b WHERE b.member.userId = :userId ORDER BY b.bookDate DESC")
    List<Book> findLatestBookByUserId(@Param("userId") String userId, Pageable pageable);
}

