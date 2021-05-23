package org.jp.spring.security.jpa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.jp.spring.security.jpa.entity.Role;
import org.jp.spring.security.jpa.entity.Users;
import org.jp.spring.security.jpa.repository.RoleRepository;
import org.jp.spring.security.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userDetailRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<Users> findAll() {
		log.info("enter into findAll method.");
		final List<Users> userList = userDetailRepository.findAll();
		log.info("exit from findAll method.");
		return userList;
	}

	@Transactional
	@Override
	public UserDetails findByUsername(final String username) {
		log.info("enter into findByUsername method with parameters : {}.", username);
		UserDetails userDetails = null;
		final Optional<Users> userOptional = userDetailRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			log.info("user record is found for username : {}.", username);
			final Users users = userOptional.get();

			userDetails = User.builder().username(users.getUsername()).password(users.getPassword())
					.authorities(users.getRoles()).build();
		}
		log.info("exit from findByUsername method.");
		return userDetails;
	}

	@Transactional
	@Override
	public String saveUser(final Users user) {
		log.info("enter into saveUser method with parameters : {}.", user);
		final String password = UUID.randomUUID().toString();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setRoles(user.getRoles().stream().map(r -> {
			final Optional<Role> optionalRole = roleRepository.findByRole(r.getRole());
			if (optionalRole.isPresent()) {
				r.setId(optionalRole.get().getId());
			} else {
				roleRepository.save(r);
			}
			return r;
		}).collect(Collectors.toCollection(HashSet::new)));

		userDetailRepository.save(user);
		log.info("exit from saveUser method with output : {}.", password);
		return password;
	}

	@Override
	public void deleteByUsername(final String username) {
		log.info("enter into deleteByUsername method with parameters : {}.", username);
		final Optional<Users> optionalUser = userDetailRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			log.info("user record is found for username : {}.", username);
			userDetailRepository.delete(optionalUser.get());
		}
		log.info("exit from deleteByUsername method.");
	}

}
