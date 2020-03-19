package io.fdlessard.codebites.oauth2.sso.apps.api2;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Api2Controller {

  @GetMapping("/api2")
  public Account api2(Principal principal) {

    log.debug("Api2Controller.api2() endpoint");
    return buildAccount();
  }

  private static Account buildAccount() {
    return Account.builder()
        .id(2l)
        .code("Code 2")
        .name("Account 2")
        .description("Account 2 from API 2")
        .build();
  }
}
