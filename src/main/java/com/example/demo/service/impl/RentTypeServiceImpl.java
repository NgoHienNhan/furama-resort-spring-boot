package com.example.demo.service.impl;

import com.example.demo.bean.RentType;
import com.example.demo.repository.RentTypeRepository;
import com.example.demo.service.RentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentTypeServiceImpl implements RentTypeService {
    @Autowired
    private RentTypeRepository rentTypeRepository;

    @Override
    public Iterable<RentType> findAll() {
        return rentTypeRepository.findAll();
    }
}
