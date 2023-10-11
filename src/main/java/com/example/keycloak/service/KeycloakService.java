package com.example.keycloak.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.keycloak.dto.SimpleKeycloakUser;
import com.example.keycloak.entity.User;
import com.example.keycloak.util.EntityDtoUtil;

@Service
public class KeycloakService {

	@Value("${keycloak.server.clientId}")
	String clientId;

	@Value("${keycloak.server.realm}")
	private String realm;
	private final Keycloak instance;
	private final UserService usersService;
	private final EntityDtoUtil entityDtoUtil;

	@Autowired
	public KeycloakService(Keycloak instance, UserService usersService, EntityDtoUtil entityDtoUtil) {
		this.instance = instance;
		this.usersService = usersService;
		this.entityDtoUtil = entityDtoUtil;
	}

	public User createUserInKeycloakAndConvertToUser(SimpleKeycloakUser simpleKeycloakUser) {

		UserRepresentation user = new UserRepresentation();
		user.setUsername(simpleKeycloakUser.getUsername());
		user.setFirstName(simpleKeycloakUser.getFirstName());
		user.setLastName(simpleKeycloakUser.getLastName());
		user.setEmail(simpleKeycloakUser.getEmail());
		user.setRealmRoles(null);
		user.setEnabled(true);
		user.setRealmRoles(Collections.emptyList());
		user.setEnabled(true);
		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
		credentialRepresentation.setType("password");
		credentialRepresentation.setValue(simpleKeycloakUser.getPassword());
		credentialRepresentation.setTemporary(false);
		user.setCredentials(Collections.singletonList(credentialRepresentation));
		Response response = instance.realm(realm).users().create(user);
		String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
		// Setting Roles for User's

		// test assign roles

		// Assign roles to the user

		RoleRepresentation role = instance.realm(realm).roles().get(simpleKeycloakUser.getRoleName())
				.toRepresentation();
		instance.realm(realm).users().get(userId).roles().realmLevel().add(Arrays.asList(role));
		

		// -------------------------------------
		simpleKeycloakUser.setId(userId);
		return usersService.saveUser(entityDtoUtil.mapSimpleKeycloakToUser(simpleKeycloakUser));
	}

	public static void assignClientRolesToUser(Keycloak keycloak, String realm, String userId, String clientId,
			List<String> roles) {
		RealmResource realmResource = keycloak.realm(realm);
		UserResource userResource = realmResource.users().get(userId);

		// Get the client roles for the specified client
		List<RoleRepresentation> clientRoles = realmResource.clients().get(clientId).roles().list();

		// Iterate through the roles and add them to the user
		for (String roleName : roles) {
			for (RoleRepresentation role : clientRoles) {
				if (role.getName().equals(roleName)) {
					userResource.roles().clientLevel(clientId).add(Arrays.asList(role));
				}
			}
		}
	}

	public List<User> findAllUsers() {
		return usersService.findAllUsers();
	}

	public String createRole() {
		List<String> roles = Arrays.asList("payzo-admin", "payzo-preemployee", "payzo-employee", "payzo-employer");
		for (String roleName : roles) {
			RoleRepresentation role = new RoleRepresentation();
			role.setName(roleName);
			if (roleName == "payzo-admin") {
				role.setComposite(false);
				instance.realm(realm).roles().create(role);
				continue;
			}
			role.setComposite(true);
			instance.realm(realm).roles().create(role);
		}
		return "Roles Created! and mapped with resource permission";
	}

}
