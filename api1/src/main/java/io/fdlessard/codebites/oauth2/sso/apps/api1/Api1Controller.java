package io.fdlessard.codebites.oauth2.sso.apps.api1;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class Api1Controller {

  @Autowired
  private WebClient webClientApi;

  //  @PreAuthorize("hasRole('level-1')") //  working with GrantedAuthoritiesExtractor
//  @PreAuthorize("#principal.credentials.claims['roles'].contains('ROLE-level-1')")
  @GetMapping("/api1")
  public Account api1(JwtAuthenticationToken jwtAuthentication) {

    log.debug("Api1Controller.api1() jwtAuthentication {}", jwtAuthentication);

    return buildAccount();
  }

  @GetMapping("/api12")
  public String api12(Principal principal) {

    log.debug("Api1Controller.api12() principal {}", principal);

    return this.webClientApi
        .get()
        .uri("http://localhost:8082/api2")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  private static Account buildAccount() {
    return Account.builder()
        .id(1l)
        .code("Code 1")
        .name("Account 1")
        .description("Account 1 from API 1")
        .build();
  }

}
