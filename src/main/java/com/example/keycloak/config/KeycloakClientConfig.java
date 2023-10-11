package com.example.keycloak.config;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClientConfig {

	@Value("${keycloak.server.adminUserName}")
	String adminUserName;

	@Value("${keycloak.server.password}")
	String adminPassword;

	@Value("${keycloak.server.clientId}")
	String clientId;

	@Value("${keycloak.server.realm}")
	String realm;

	@Bean
	public Keycloak keycloak(@Value("${keycloak.server.url}") String serverUrl) {
		return Keycloak.getInstance(serverUrl, realm, adminUserName, adminPassword, clientId);
	}

}
