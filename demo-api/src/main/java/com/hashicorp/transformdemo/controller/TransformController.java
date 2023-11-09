package com.hashicorp.transformdemo.controller;

import com.hashicorp.transformdemo.TransformUtil;
import com.hashicorp.transformdemo.TransitUtil;
import com.hashicorp.transformdemo.entity.User;
import com.hashicorp.transformdemo.repository.UserJpaRepository;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransformController {

  //    private User u = new User();
  private final UserJpaRepository userJpaRepository;
  private final TransformUtil transformUtil;
  private final TransitUtil transitUtil;


  public TransformController(UserJpaRepository userJpaRepository, TransformUtil transformUtil,
    TransitUtil transitUtil) {
    this.userJpaRepository = userJpaRepository;
    this.transformUtil = transformUtil;
    this.transitUtil = transitUtil;
  }

  @GetMapping(value = "/api/v1/get-all-users")
  public Object getAllUsers() {
    return userJpaRepository.findAll();
  }

  @GetMapping(value = "/api/v1/get-encrypted-users")
  public Object getEncryptedUsers() {
    return userJpaRepository.findUsersByFlag("transit");
  }

  @GetMapping(value = "/api/v1/get-transformed-users")
  public Object getTransformedUsers() {
    return userJpaRepository.findUsersByFlag("transformation");
  }

  @GetMapping(value = "/api/v1/get-simple-transformed-users")
  public Object getSimpleTransformedUsers() {
    return userJpaRepository.findUsersByFlag("simple-transformation");
  }

  @GetMapping(value = "/api/v1/get-simplest-transformed-users")
  public Object getSimplestTransformedUsers() {
    return userJpaRepository.findUsersByFlag("simplest-transformation");
  }

  @GetMapping(value = "/api/v1/get-default-tokenization-users")
  public Object getDefaultTokenizedUsers() {
    return userJpaRepository.findUsersByFlag("default-tokenization");
  }

  @GetMapping(value = "/api/v1/get-convergent-tokenization-users")
  public Object getConvergentTokenizedUsers() {
    return userJpaRepository.findUsersByFlag("convergent-tokenization");
  }

  @PostMapping(value = "/api/v1/transit/add-user")
  public Object addOneEncryptedUser(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard) {
    var u = User.builder()
      .id(UUID.randomUUID()
        .toString())
      .username(username)
      .password(transitUtil.encrypt(password))
      .email(transitUtil.encrypt(email))
      .creditcard(transitUtil.encrypt(creditcard))
      .flag("transit")
      .build();
    return userJpaRepository.save(u);
  }

  @PostMapping(value = "/api/v1/transformation/add-user")
  public Object addOneTransformedUser(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard) {
    var u = User.builder()
      .id(UUID.randomUUID()
        .toString())
      .username(username)
      .password(transitUtil.encrypt(password))
      .email(transformUtil.encode(email, "email"))
      .creditcard(transformUtil.encode(creditcard, "creditcard-symbolnumericalpha"))
      .flag("transformation")
      .build();
    return userJpaRepository.save(u);
  }

  @PostMapping(value = "/api/v1/simple-transformation/add-user")
  public Object addOneSimpleTransformedUser(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard) {
    var u = User.builder()
      .id(UUID.randomUUID()
        .toString())
      .username(username)
      .password(transitUtil.encrypt(password))
      .email(transformUtil.encode(email, "email-exdomain"))
      .creditcard(transformUtil.encode(creditcard, "creditcard-numericupper"))
      .flag("simple-transformation")
      .build();
    return userJpaRepository.save(u);
  }


  @PostMapping(value = "/api/v1/simplest-transformation/add-user")
  public Object addOneSimplestTransformedUser(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard) {
    var u = User.builder()
      .id(UUID.randomUUID()
        .toString())
      .username(username)
      .password(transitUtil.encrypt(password))
      .email(email)
      .creditcard(transformUtil.encode(creditcard, "creditcard-numeric"))
      .flag("simplest-transformation")
      .build();
    return userJpaRepository.save(u);
  }

  @PostMapping(value = "/api/v1/default-tokenization/add-user")
  public Object addOneDefaultTokenizedUser(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard) {
    var u = User.builder()
      .id(UUID.randomUUID()
        .toString())
      .username(username)
      .password(transitUtil.encrypt(password))
      .email(transformUtil.encode(email, "default-tokenization"))
      .creditcard(transformUtil.encode(creditcard, "default-tokenization"))
      .flag("default-tokenization")
      .build();
    return userJpaRepository.save(u);
  }

  @PostMapping(value = "/api/v1/convergent-tokenization/add-user")
  public Object addOneConvergentTokenizedUser(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam String email,
    @RequestParam String creditcard) {
    var u = User.builder()
      .id(UUID.randomUUID()
        .toString())
      .username(username)
      .password(transitUtil.encrypt(password))
      .email(transformUtil.encode(email, "convergent-tokenization"))
      .creditcard(transformUtil.encode(creditcard, "convergent-tokenization"))
      .flag("convergent-tokenization")
      .build();
    return userJpaRepository.save(u);
  }

  @RequestMapping(value = "/api/v1/decrypt")
  public Object decryptOneUser(@RequestParam String username) {

    var u = userJpaRepository.findUserByUsername(username);
    Map<String, String> decryptedUser = new LinkedHashMap<>();
    decryptedUser.put("username", username);
    decryptedUser.put("email", decodeBase64(transitUtil.decrypt(u.getEmail())));
    decryptedUser.put("creditcard", decodeBase64(transitUtil.decrypt(u.getCreditcard())));
    return decryptedUser;
  }


  @RequestMapping(value = "/api/v1/decode")
  public Object decodeOneUser(@RequestParam String username, String flag) {

    var u = userJpaRepository.findUserByUsername(username);
    Map<String, String> decodedUser = new LinkedHashMap<>();
    decodedUser.put("username", username);
    String cc;
    switch (flag) {
      case "transformation":
        decodedUser.put("email", transformUtil.decode(u.getEmail(), "email"));
        cc = transformUtil.decode(u.getCreditcard(), "creditcard-symbolnumericalpha");
        decodedUser.put("creditcard", transformUtil.masking(cc, "ccn-masking"));
        break;
      case "simple-transformation":
        decodedUser.put("email", transformUtil.decode(u.getEmail(), "email-exdomain"));
        cc = transformUtil.decode(u.getCreditcard(), "creditcard-numericupper");
        decodedUser.put("creditcard", transformUtil.masking(cc, "ccn-masking"));
        break;
      case "simplest-transformation":
        decodedUser.put("email", u.getEmail());
        cc = transformUtil.decode(u.getCreditcard(), "creditcard-numeric");
        decodedUser.put("creditcard", transformUtil.masking(cc, "ccn-masking"));
        break;
      case "default-tokenization":
        decodedUser.put("email", transformUtil.decode(u.getEmail(), "default-tokenization"));
        cc = transformUtil.decode(u.getCreditcard(), "default-tokenization");
        decodedUser.put("creditcard", transformUtil.masking(cc, "ccn-masking"));
        break;
      case "convergent-tokenization":
        decodedUser.put("email", transformUtil.decode(u.getEmail(), "convergent-tokenization"));
        cc = transformUtil.decode(u.getCreditcard(), "convergent-tokenization");
        decodedUser.put("creditcard", transformUtil.masking(cc, "ccn-masking"));
        break;
    }
    return decodedUser;
  }

  public String decodeBase64(String text) {
    return new String(Base64.getDecoder()
      .decode(text));
  }
}
