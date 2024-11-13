package com.example.springsecuritycruddemo.services.jwt;

import com.example.springsecuritycruddemo.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class CustomerServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.example.springsecuritycruddemo.model.User> userByUsername = userRepository.getUserByUsername(username);


        System.out.println(username);
        System.out.println(userByUsername);
        if(userByUsername.isEmpty())
        {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User NOt Found");
        }
        return new User(userByUsername.get().getUsername(),userByUsername.get().getPassword(),userByUsername.get().getAuthorities());
    }
}
