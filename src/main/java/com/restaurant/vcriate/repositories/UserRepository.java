package com.restaurant.vcriate.repositories;

import com.restaurant.vcriate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);


    boolean existsByEmail(String email);
}