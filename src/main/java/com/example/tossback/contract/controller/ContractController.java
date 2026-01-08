package com.example.tossback.contract.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.contract.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping()
    public ResponseEntity<CommonResponse> getContract() {
        return new ResponseEntity<>(new CommonResponse(contractService.getContract()), HttpStatus.OK);
    }

}
