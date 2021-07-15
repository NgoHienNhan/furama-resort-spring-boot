package com.example.demo.service.impl;

import com.example.demo.bean.AttachService;
import com.example.demo.repository.AttachServiceRepository;
import com.example.demo.service.AttachServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachServiceServiceImpl implements AttachServiceService {
    @Autowired
    private AttachServiceRepository attachServiceRepository;

    @Override
    public Iterable<AttachService> findAll() {
        return attachServiceRepository.findAll();
    }
}
