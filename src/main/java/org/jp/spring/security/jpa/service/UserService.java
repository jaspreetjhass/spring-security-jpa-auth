package org.jp.spring.security.jpa.service;

import java.util.List;

import org.jp.spring.security.jpa.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	List<Users> findAll();

	UserDetails findByUsername(String username);

	String saveUser(Users user);

	void deleteByUsername(String username);

}
