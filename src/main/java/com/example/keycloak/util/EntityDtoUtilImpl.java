package com.example.keycloak.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.keycloak.dto.SimpleKeycloakUser;
import com.example.keycloak.entity.User;

@Component
public class EntityDtoUtilImpl implements EntityDtoUtil{

    @Override
    public User mapSimpleKeycloakToUser(SimpleKeycloakUser simpleKeycloakUser) {
        User user = new User();
        BeanUtils.copyProperties(simpleKeycloakUser,user);
        user.setNom(simpleKeycloakUser.getFirstName());
        user.setPrenom(simpleKeycloakUser.getLastName());
        return user;
    }
}
