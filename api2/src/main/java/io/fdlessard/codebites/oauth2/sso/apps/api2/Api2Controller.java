package io.fdlessard.codebites.oauth2.sso.apps.api2;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Api2Controller {

  @GetMapping("/api2")
  public String api1(Principal principal) {

    log.debug("Called Api2Controller.api2() endpoint");

    String name = principal.getName();

    return "Made it to protected api2 on resource server! " + name;
  }
}
