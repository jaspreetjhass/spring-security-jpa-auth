package org.jp.spring.security.jpa.repository;

import java.util.Optional;

import org.jp.spring.security.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRole(String role);
	
}
