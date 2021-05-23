package org.jp.spring.security.jpa.rest;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.jp.spring.security.jpa.dto.UserDto;
import org.jp.spring.security.jpa.entity.Role;
import org.jp.spring.security.jpa.entity.Users;
import org.jp.spring.security.jpa.mapper.UserMapper;
import org.jp.spring.security.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("jpa-auth/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/users")
	public List<UserDto> getAllUser() {
		log.info("enter into getAllUser method.");
		final List<Users> userList = userService.findAll();
		final List<UserDto> userDtoList = userList.parallelStream().map(user -> {
			UserDto temp = userMapper.convertUserToUserDto(user);
			temp.setAuthority(
					user.getRoles().parallelStream().map(role -> role.getRole()).collect(Collectors.toList()));
			return temp;
		}).collect(Collectors.toList());
		log.info("exit from getAllUser method with output : {}.", userDtoList);
		return userDtoList;
	}

	@PostMapping("/users")
	public String createUser(@RequestBody final UserDto userDto) {
		log.info("enter into createUser mwthod with parameters : {}.", userDto);
		Users user = userMapper.convertUserDtoToUser(userDto);
		user.setRoles(userDto.getAuthority().parallelStream().map(authority -> Role.builder().role(authority).build())
				.collect(Collectors.toCollection(HashSet::new)));
		final String password = userService.saveUser(user);
		log.info("exit from createUser method with output : {}.");
		return password;
	}

	@DeleteMapping("/users/{username}")
	public void deleteUser(@PathVariable final String username) {
		log.info("enter into deleteUser mwthod with parameters : {}.", username);
		if (!StringUtils.pathEquals("admin", username))
			userService.deleteByUsername(username);
		log.info("exit from deleteUser method.");
	}

	@PutMapping("/users/{username}")
	public void updateUser(@RequestBody final UserDto userDto, @PathVariable final String username) {
		log.info("enter into updateUser mwthod with parameters : {}.", userDto);
		Users user = userMapper.convertUserDtoToUser(userDto);
		user.setRoles(userDto.getAuthority().parallelStream().map(authority -> Role.builder().role(authority).build())
				.collect(Collectors.toCollection(HashSet::new)));
		userService.saveUser(user);
		log.info("exit from updateUser method.");
	}
}