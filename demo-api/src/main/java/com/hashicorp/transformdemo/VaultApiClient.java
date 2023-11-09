package com.hashicorp.transformdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class VaultApiClient {

  @Value(value = "${spring.cloud.vault.scheme}")
  private String vaultScheme;

  @Value(value = "${spring.cloud.vault.host}")
  private String vaultHost;

  @Value(value = "${spring.cloud.vault.port}")
  private int vaultPort;


  public VData postToVault(String api, Map<String, Object> input) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(input, headers);

    String url = String.format("%1$s://%2$s:%3$s/%4$s", vaultScheme, vaultHost, vaultPort, api);

    log.debug("postToVault: api:[{}], params:[{}]", api, input);

    return new RestTemplate().postForObject(url, entity, VResponse.class)
      .getData();
  }

  @Data
  static class VResponse {
    VData data;
  }
  @Data
  static class VData {

    @JsonProperty("encoded_value")
    String encodedValue;

    @JsonProperty("decoded_value")
    String decodedValue;

    @JsonProperty("ciphertext")
    String cipherText;

    @JsonProperty("plaintext")
    String plainText;

  }


}
