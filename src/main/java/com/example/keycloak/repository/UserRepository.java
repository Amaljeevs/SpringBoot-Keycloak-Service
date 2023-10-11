package com.example.keycloak.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.keycloak.entity.User;

public interface UserRepository extends JpaRepository<User,String> {
}
