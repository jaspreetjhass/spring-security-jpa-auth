package org.jp.spring.security.jpa.security.config;

import org.jp.spring.security.jpa.security.service.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Order(0)
@Configuration
public class JpaSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUserDetailService jpaUserDetailService;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jpaUserDetailService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.antMatcher("/jpa-auth/**").formLogin().and().httpBasic().and().authorizeRequests()
				.mvcMatchers("/jpa-auth/user").hasAnyRole("USER", "ADMIN").mvcMatchers("/jpa-auth/health").permitAll()
				.mvcMatchers("/jpa-auth/**").hasAnyRole("ADMIN");
		http.csrf().disable();
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
