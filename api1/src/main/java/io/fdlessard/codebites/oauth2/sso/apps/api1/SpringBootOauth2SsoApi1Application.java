package io.fdlessard.codebites.oauth2.sso.apps.api1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SpringBootOauth2SsoApi1Application {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootOauth2SsoApi1Application.class, args);
  }

}
