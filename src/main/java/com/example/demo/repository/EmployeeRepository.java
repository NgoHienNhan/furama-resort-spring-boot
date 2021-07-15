package com.example.demo.repository;

import com.example.demo.bean.Employee;
import com.example.demo.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Employee findByUser(User user);

    Page<Employee> findByNameLike(Pageable pageable,String name);
}
