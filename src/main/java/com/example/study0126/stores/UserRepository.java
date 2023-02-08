package com.example.study0126.stores;
import com.example.study0126.stores.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
}
