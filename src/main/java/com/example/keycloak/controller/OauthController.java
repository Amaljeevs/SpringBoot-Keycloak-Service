package com.example.keycloak.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.keycloak.dto.OauthDTO;
import com.example.keycloak.dto.RefreshToken;

@RestController
@RequestMapping("/oauth")
public class OauthController {

	@Value("${keycloak.server.loginUrl}")
	String keyCloakServerLoginUrl;

	@Value("${keycloak.server.clientId}")
	String clientId;

	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@PostMapping("/token")
	public ResponseEntity<JSONObject> getToken(@RequestBody OauthDTO auth) {
		JSONObject error = null;
		ResponseEntity<String> response = null;
		try {
			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("client_id", clientId);
			requestBody.add("grant_type", "password");
			requestBody.add("username", auth.getUsername());
			requestBody.add("password", auth.getPassword());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
			response = restTemplate.exchange(keyCloakServerLoginUrl, HttpMethod.POST, requestEntity, String.class);
			JSONParser parser = new JSONParser();
			JSONObject res = (JSONObject) parser.parse(response.getBody());
			return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			error = new JSONObject();
			error.put("error", "invalid_grant");
			error.put("error_description", "Bad credentials");
			return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			error = new JSONObject();
			error.put("error", e.getMessage());
			error.put("error_description", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/token/refresh")
	public ResponseEntity<JSONObject> getRefreshToken(@RequestBody RefreshToken auth) {
		JSONObject error = null;
		try {
			MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			requestBody.add("client_id", clientId);
			requestBody.add("grant_type", "refresh_token");
			requestBody.add("refresh_token", auth.getRefreshToken());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
			ResponseEntity<String> response = restTemplate.exchange(keyCloakServerLoginUrl, HttpMethod.POST,
					requestEntity, String.class);
			JSONParser parser = new JSONParser();
			JSONObject res = (JSONObject) parser.parse(response.getBody());
			return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			error = new JSONObject();
			error.put("error", "invalid_grant");
			error.put("error_description", "Bad credentials");
			return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			error = new JSONObject();
			error.put("error", e.getMessage());
			error.put("error_description", HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
