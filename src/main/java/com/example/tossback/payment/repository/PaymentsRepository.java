package com.example.tossback.payment.repository;

import com.example.tossback.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
