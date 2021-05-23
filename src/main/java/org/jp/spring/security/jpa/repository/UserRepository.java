package org.jp.spring.security.jpa.repository;

import java.util.Optional;

import org.jp.spring.security.jpa.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUsername(String username);

}
