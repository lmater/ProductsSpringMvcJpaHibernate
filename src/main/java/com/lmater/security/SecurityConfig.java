package com.lmater.security;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.unit.DataSize;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	

    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		  auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles(
//		  "user","admin");
//		  auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles(
//		  "user");

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select login as principal,pass as credentials,active from users where login=?")
				.authoritiesByUsernameQuery("select login as principal, role as role from users_roles where login=?")
				.passwordEncoder(new MessageDigestPasswordEncoder("MD5"))
				.rolePrefix("ROLE_");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.formLogin();//. loginPage("/login");
		http
        .authorizeRequests()
        .antMatchers( "/css/**").permitAll()
        .antMatchers("/admin/*").hasRole("ADMIN")
        .antMatchers("/user/*").hasRole("USER")
        .anyRequest().authenticated()
        // we'll enable this in a later updates
        .and()
		.exceptionHandling().accessDeniedPage("/user/403");

	}
}
