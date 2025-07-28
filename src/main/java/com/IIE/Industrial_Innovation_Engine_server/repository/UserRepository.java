package com.IIE.Industrial_Innovation_Engine_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IIE.Industrial_Innovation_Engine_server.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUserNumber(String userNumber);
    
    List<User> findByUserNameContainingOrUserNumberContaining(String userName, String userNumber);
}
