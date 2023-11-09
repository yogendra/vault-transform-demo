package com.hashicorp.transformdemo.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

  private String id;
  private String username;
  private String password;
  private String email;
  private String creditcard;
  private String flag;

}
