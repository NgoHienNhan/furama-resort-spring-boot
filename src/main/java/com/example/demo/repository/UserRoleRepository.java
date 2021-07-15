package com.example.demo.repository;

import com.example.demo.bean.Role;
import com.example.demo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<Role,Integer> {
    List<Role> findByUsers(User user);
}
