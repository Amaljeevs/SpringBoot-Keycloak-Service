package com.example.keycloak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/oauth/token").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/register").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/register/createRole").permitAll()
				.anyRequest().authenticated();
		http.oauth2ResourceServer().jwt();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors().and().csrf().disable();
		return http.build();
	}

}
