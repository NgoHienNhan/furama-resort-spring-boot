package com.example.demo.repository;

import com.example.demo.bean.CustomerType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TypeOfCustomerRepository extends PagingAndSortingRepository<CustomerType,Integer> {
}
