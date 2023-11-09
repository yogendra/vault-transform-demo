package com.hashicorp.transformdemo;


import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TransformUtil {

  private final VaultApiClient vaultApiClient;

  public TransformUtil( VaultApiClient vaultApiClient) {
    this.vaultApiClient = vaultApiClient;
  }

  public String encode(String value, String transformationName) {
    Map<String, Object> input = new LinkedHashMap<>();
    input.put("transformation", transformationName);
    input.put("value", value);
    var data = vaultApiClient.postToVault("/v1/transform/encode/payments", input);
    return data.getEncodedValue();

  }

  public String decode(String value, String transformationName) {
    var input = new LinkedHashMap<String, Object>();
    input.put("transformation", transformationName);
    input.put("value", value);
    var data = vaultApiClient.postToVault("/v1/transform/decode/payments", input);
    return data.getDecodedValue();
  }

  public String masking(String value, String transformationName) {
    var input = new LinkedHashMap<String, Object>();
    input.put("transformation", transformationName);
    input.put("value", value);
    var data = vaultApiClient.postToVault("/v1/transform/encode/payments", input);
    return data.getEncodedValue();
  }
}

