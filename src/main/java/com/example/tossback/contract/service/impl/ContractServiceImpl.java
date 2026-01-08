package com.example.tossback.contract.service.impl;

import com.example.tossback.contract.entity.ContractCompany;
import com.example.tossback.contract.repository.ContractRepository;
import com.example.tossback.contract.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repo;

    public ContractServiceImpl(ContractRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ContractCompany> getContract() {
        return repo.findAll();
    }
}
