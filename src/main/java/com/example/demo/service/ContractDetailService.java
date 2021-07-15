package com.example.demo.service;

import com.example.demo.bean.Contract;
import com.example.demo.bean.ContractDetail;

public interface ContractDetailService {
    ContractDetail findByIdContract(Contract contract);

    void save(ContractDetail contractDetail);
}
