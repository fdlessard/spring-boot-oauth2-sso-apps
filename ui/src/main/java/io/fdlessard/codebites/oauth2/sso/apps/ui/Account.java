package io.fdlessard.codebites.oauth2.sso.apps.ui;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

  private Long id;
  private String code;
  private String name;
  private String description;
}
