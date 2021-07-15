package com.example.demo.repository;

import com.example.demo.bean.Contract;
import com.example.demo.bean.ContractDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractDetailRepository extends JpaRepository<ContractDetail,Integer> {
    ContractDetail findByContract(Contract contract);
}
