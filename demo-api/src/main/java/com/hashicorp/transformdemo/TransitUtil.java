package com.hashicorp.transformdemo;

import static java.lang.String.format;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TransitUtil {

  private final VaultApiClient vaultApiClient;

  private final Config config;

  public TransitUtil(VaultApiClient vaultApiClient, Config config) {
    this.vaultApiClient = vaultApiClient;
    this.config = config;
  }


  public String encrypt(String plaintext) {
    Map<String, Object> input = new LinkedHashMap<>();
    input.put("plaintext", Base64.getEncoder()
      .encodeToString(plaintext.getBytes()));
    var api = format("/v1/transit/encrypt/%1$s", config.getKeyName());
    var data = vaultApiClient.postToVault(api, input);
    return data.getCipherText();
  }

  public String decrypt(String ciphertext) {
    Map<String, Object> input = new LinkedHashMap<>();
    input.put("ciphertext", ciphertext);
    var api = format("/v1/transit/decrypt/%1$s", config.getKeyName());
    var data = vaultApiClient.postToVault(api, input);
    return data.getPlainText();
  }

}
