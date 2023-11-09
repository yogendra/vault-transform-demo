package com.hashicorp.transformdemo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.config")
@Data
@NoArgsConstructor
public class Config {
  private String keyName = "my-key";
  private boolean skipLoadingSampleData = false;
}
