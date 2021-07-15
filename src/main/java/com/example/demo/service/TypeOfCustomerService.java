package com.example.demo.service;

import com.example.demo.bean.CustomerType;

public interface TypeOfCustomerService {
    Iterable<CustomerType> findAll();
}
