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
  public Account api1(JwtAuthenticationToken jwtAuthentication) {

    log.debug("Api1Controller.api1() endpoint");
    log.debug("Api1Controller.api1() jwtAuthentication {}", jwtAuthentication);
    String name = jwtAuthentication.getName();

    return buildAccount();
  }

  private Account buildAccount() {
    return Account.builder()
        .id(1l)
        .code("code 1")
        .name("Account 1")
        .description("Account 1 from API 1")
        .build();
  }

}
