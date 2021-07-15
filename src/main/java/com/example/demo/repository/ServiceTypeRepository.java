package com.example.demo.repository;

import com.example.demo.bean.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType,Integer> {
}
