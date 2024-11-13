package com.example.springsecuritycruddemo.repository;

import com.example.springsecuritycruddemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> getUserByUsername(String username);

  User getUserById(Long id);
}
