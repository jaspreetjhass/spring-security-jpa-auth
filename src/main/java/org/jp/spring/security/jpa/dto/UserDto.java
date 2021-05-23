package org.jp.spring.security.jpa.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

	private String username;
	@JsonIgnore
	private String password;
	private boolean enabled;
	private List<String> authority;

}
