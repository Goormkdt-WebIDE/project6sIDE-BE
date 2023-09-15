package com.project5s.IDEproject.repository;

import com.project5s.IDEproject.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String userName);
}
