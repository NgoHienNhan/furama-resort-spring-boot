package com.example.demo.service;

import com.example.demo.bean.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> showAll(Pageable pageable);

    Page<Customer> findByName(Pageable pageable, String name);

    Iterable<Customer> findAll();

    Customer findById(String id);

    void save(Customer customer);

    void delete(Customer customer);
}
