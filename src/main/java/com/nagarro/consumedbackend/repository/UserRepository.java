package com.nagarro.consumedbackend.repository;

import com.nagarro.consumedbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
