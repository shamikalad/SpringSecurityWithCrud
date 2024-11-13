package com.example.springsecuritycruddemo.services.impl;

import com.example.springsecuritycruddemo.model.User;
import com.example.springsecuritycruddemo.repository.UserRepository;
import com.example.springsecuritycruddemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public  UserRepository userRepository;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User userByUserId = userRepository.getUserById(id);
        userRepository.delete(userByUserId);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
