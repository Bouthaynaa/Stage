package com.example.stage.repository;

import com.example.stage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String email);
    @Query(value= "Select * FROM users where email= :email", nativeQuery =true)
    User getUserByEmail(@Param("email")String email);

    User findByVerificationToken(String token);
}
