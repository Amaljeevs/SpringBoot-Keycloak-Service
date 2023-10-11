package com.example.keycloak.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.keycloak.dto.SimpleKeycloakUser;
import com.example.keycloak.entity.User;
import com.example.keycloak.service.KeycloakService;

@RestController
@RequestMapping("api/v1/register")
public class KeycloakController {

	private final KeycloakService keycloakService;

	@Autowired
	public KeycloakController(KeycloakService keycloakService) {
		this.keycloakService = keycloakService;
	}

	@PostMapping
	public User createUser(@Valid @RequestBody SimpleKeycloakUser simpleKeycloakUserMono) {
		return keycloakService.createUserInKeycloakAndConvertToUser(simpleKeycloakUserMono);
	}

	@GetMapping
	public List<User> findAllUsers() {
		return keycloakService.findAllUsers();
	}

	@PostMapping("/createRole")
	public String createRole() {
		return keycloakService.createRole();
	}
}