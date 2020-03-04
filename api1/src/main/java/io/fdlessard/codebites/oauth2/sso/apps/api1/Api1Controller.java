package io.fdlessard.codebites.oauth2.sso.apps.api1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Api1Controller {

//  @PreAuthorize("hasRole('level-1')") //  working with GrantedAuthoritiesExtractor
//  @PreAuthorize("#principal.credentials.claims['roles'].contains('ROLE-level-1')")
  @GetMapping("/api1")
  public String api1(JwtAuthenticationToken jwtAuthentication) {

    log.debug("Called Api1Controller.api1() endpoint");

    String name = jwtAuthentication.getName();

    return "Made it to protected api1 on resource server! " + name;
  }

}
