package com.hashicorp.transformdemo.service;

import com.hashicorp.transformdemo.model.User;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@Slf4j
public class UiService {

  private String apiAddress =
    System.getenv("API_ADDRESS") != null ? System.getenv("API_ADDRESS") : "http://localhost:8081";
  private final RestTemplateBuilder restTemplateBuilder;


  public UiService(RestTemplateBuilder builder) {
    this.restTemplateBuilder = builder;
  }

  private RestTemplate rt() {
    return restTemplateBuilder.build();
  }

  public User[] getTransformedUsers() throws Exception {
    var result = rt().getForObject(apiAddress + "/api/v1/get-transformed-users", User[].class);
    log.info("getTransformedUsers:{}", (Object)result);

    return result;
  }

  public User[] getSimpleTransformedUsers() throws Exception {
    var result = rt().getForObject(apiAddress + "/api/v1/get-simple-transformed-users",
      User[].class);

    log.info("getSimpleTransformedUsers:{}", (Object)result);
    return result;
  }

  public User[] getSimplestTransformedUsers() throws Exception {
    var result = rt().getForObject(apiAddress + "/api/v1/get-simplest-transformed-users",
      User[].class);

    log.info("getSimplestTransformedUsers:{}", (Object)result);
    return result;

  }

  public User[] getEncryptedUsers() throws Exception {
    var result = rt().getForObject(apiAddress + "/api/v1/get-encrypted-users", User[].class);
    log.info("getEncryptedUsers:{}", (Object)result);
    return result;
  }

  public User[] getDefaultTokenizationUsers() throws Exception {
    var result = rt().getForObject(apiAddress + "/api/v1/get-default-tokenization-users",
      User[].class);
    log.info("getDefaultTokenizationUsers:{}", (Object)result);
    return result;
  }

  public User[] getConvergentTokenizationUsers() throws Exception {
    var result = rt().getForObject(apiAddress + "/api/v1/get-convergent-tokenization-users",
      User[].class);
    log.info("getConvergentTokenizationUsers:{}", (Object)result);
    return result;
  }

  public void addOneEncryptedUser(String username, String password, String email, String creditcard,
    String method) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    Map<String, String> input = new LinkedHashMap<>();
    input.put("username", username);
    input.put("password", password);
    input.put("email", email);
    input.put("creditcard", creditcard);

    log.info("addOneEncryptedUser/method:{}", method);

    HttpEntity<Map<String, String>> entity = new HttpEntity<>(input, headers);

    var targetUrl = UriComponentsBuilder.fromUriString(apiAddress)
      .path("api/v1/" + method + "/add-user")
      .queryParam("username", username)
      .queryParam("password", password)
      .queryParam("email", email)
      .queryParam("creditcard", creditcard)
      .build()
      .toString();

    log.info("addOneEncryptedUser: url:[%s]", targetUrl);
    var response = rt().postForObject(targetUrl, entity, String.class);
    log.info("addOneEncryptedUser: response:[%s]", response);
  }

  public User getOneDecryptedUser(String username) {
    return rt().getForObject(apiAddress + "/api/v1/decrypt?username=" + username, User.class);
  }

  public User getOneDecodedUser(String username, String flag) {
    return rt().getForObject(apiAddress + "/api/v1/decode?username=" + username + "&flag=" + flag,
      User.class);
  }
}
