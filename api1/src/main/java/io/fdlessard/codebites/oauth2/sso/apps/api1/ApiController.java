package io.fdlessard.codebites.oauth2.sso.apps.api1;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

//  @PreAuthorize("hasRole('level-1')") //  working with GrantedAuthoritiesExtractor
//  @PreAuthorize("#principal.credentials.claims['roles'].contains('ROLE-level-1')")
  @GetMapping("/api1")
  public String api1(Principal principal) {
    log.debug("Called UiController.api1() endpoint");
    log.debug("You made it to the other side");
    String name = principal.getName();
    return "Made it to protected api1 on resource server! " + name;
  }
}
