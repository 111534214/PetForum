package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);
//    User findByIdNumber(String idNumber);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByIdNumber(String idNumber);

    Optional<User> findByEmailOrIdNumber(String email, String idNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    Optional<User> findByIdNumber(String idNumber);


}
