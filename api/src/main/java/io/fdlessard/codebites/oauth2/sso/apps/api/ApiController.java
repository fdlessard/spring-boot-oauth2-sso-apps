package io.fdlessard.codebites.oauth2.sso.apps.api;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

  @GetMapping("/api")
  public String api(Principal principal) {
    log.debug("Called UiController.api() endpoint");
    log.debug("You made it to the other side");
    String name = principal.getName();
    return "Made it to protected api on resource server! " + name;
  }
}
