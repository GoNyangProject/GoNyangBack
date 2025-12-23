package com.example.tossback.contract.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "CONTRACT_COMPANY")
public class ContractCompany {

    @Id
    @Column(name = "company_id", length = 20)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "repr_nm", length = 50)
    private String reprNm;

    @Column(name = "cont_stat_cd", length = 10)
    private String contStatCd;

    @Column(name = "strt_dt")
    private LocalDate strtDt; // 시/분/초가 필요 없으면 LocalDate 추천

    @Column(name = "end_dt")
    private LocalDate endDt;

    @Column(name = "remark", length = 1000)
    private String remark;

    @Column(name = "img_url")
    private String imgUrl;
}
