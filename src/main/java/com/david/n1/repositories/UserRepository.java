package com.david.n1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.n1.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

}
