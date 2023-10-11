package com.example.keycloak.util;

import com.example.keycloak.dto.SimpleKeycloakUser;
import com.example.keycloak.entity.User;

@FunctionalInterface
public interface EntityDtoUtil {

    /**
     * this method should be implemented in order to split SimpleKeycloakUser into a keyCloak user and a simple user while
     * the two users share the same id through the field "id"
     * @param simpleKeycloakUser
     * @return User
     */
    User mapSimpleKeycloakToUser(SimpleKeycloakUser simpleKeycloakUser);

}
