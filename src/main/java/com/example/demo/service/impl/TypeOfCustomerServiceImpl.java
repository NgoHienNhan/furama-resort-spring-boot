package com.example.demo.service.impl;

import com.example.demo.bean.CustomerType;
import com.example.demo.repository.TypeOfCustomerRepository;
import com.example.demo.service.TypeOfCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeOfCustomerServiceImpl implements TypeOfCustomerService {
    @Autowired
    private TypeOfCustomerRepository typeOfCustomerRepository;

    @Override
    public Iterable<CustomerType> findAll() {
        return typeOfCustomerRepository.findAll();
    }
}
