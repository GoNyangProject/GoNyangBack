package com.example.tossback.admin.block.repository;

import com.example.tossback.admin.block.entity.BlockDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BlockDateRepository extends JpaRepository<BlockDate, Long> {
    boolean existsByBlockDate(LocalDate blockDate);
    Optional<BlockDate> findByBlockDate(LocalDate blockDate);
}
