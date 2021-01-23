package com.ecom.userservice.service;

import com.ecom.userservice.entity.User;

public interface UserService {

    User saveUser(User user);
    //User getUser(User user);
    //boolean authenticateUser(final String email, final String password);
    User getUserByEmail(String email);
}
