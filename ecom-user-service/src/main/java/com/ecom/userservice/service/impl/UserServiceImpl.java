package com.ecom.userservice.service.impl;

import com.ecom.userservice.entity.User;
import com.ecom.userservice.repository.UserRepository;
import com.ecom.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /*@Override
    public User getUser(User user) {

        return null;
    }*/

/*    @Override
    public boolean authenticateUser(String email, String password) {
        return false;
    }*/

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
