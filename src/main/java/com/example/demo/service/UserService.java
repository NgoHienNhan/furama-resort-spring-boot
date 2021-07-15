package com.example.demo.service;

import com.example.demo.bean.User;

public interface UserService {
    Boolean checkUserName(String userName);

    User findById(String id);

    Iterable<User> findAll();

    void save(User user);
}
