package com.example.twitter.service;

import com.example.twitter.entity.User;

public interface UserService {

    User registerUser(User user);

    User findByUsername(String username);

}
