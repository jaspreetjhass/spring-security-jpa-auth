package org.jp.spring.security.jpa.mapper;

import org.jp.spring.security.jpa.dto.UserDto;
import org.jp.spring.security.jpa.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto convertUserToUserDto(Users user);

	Users convertUserDtoToUser(UserDto userDto);

}
