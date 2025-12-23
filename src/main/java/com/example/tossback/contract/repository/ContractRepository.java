package com.example.tossback.contract.repository;

import com.example.tossback.contract.entity.ContractCompany;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContractRepository extends JpaRepository<ContractCompany, Long> {
}
