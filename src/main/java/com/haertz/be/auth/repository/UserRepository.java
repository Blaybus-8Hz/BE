package com.haertz.be.auth.repository;

import com.haertz.be.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthInfoEmail(String email);

    Boolean existsByAuthInfoEmail(String email);
}
