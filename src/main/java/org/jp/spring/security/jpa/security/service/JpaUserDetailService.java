package org.jp.spring.security.jpa.security.service;

import java.util.Objects;

import org.jp.spring.security.jpa.dto.UserDto;
import org.jp.spring.security.jpa.entity.Users;
import org.jp.spring.security.jpa.mapper.UserMapper;
import org.jp.spring.security.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JpaUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		log.info("enter into loadUserByUsername method with parameters : {}.", username);
		final UserDetails userDetails = userService.findByUsername(username);
		if (Objects.isNull(userDetails))
			throw new UsernameNotFoundException("user details not found");
		log.info("exit from loadUserByUsername method.");
		return userDetails;
	}

	public UserDto saveUser(final UserDto userDto) {
		log.info("enter into saveUser method with parameters : {}.", userDto);
		final Users user = userMapper.convertUserDtoToUser(userDto);
		userService.saveUser(user);
		log.info("exit from saveUser method with output : {}.", userDto);
		return userDto;
	}

	public UserDto updateUser(final UserDto userDto) {
		log.info("enter into updateUser method with parameters : {}.", userDto);
		saveUser(userDto);
		log.info("exit from updateUser method with output : {}.", userDto);
		return userDto;
	}

	public void deleteUserByUsername(final String username) {
		log.info("enter into deleteUserByUsername method with parameters : {}.", username);
		userService.deleteByUsername(username);
		log.info("enter from deleteUserByUsername method.");
	}

}
