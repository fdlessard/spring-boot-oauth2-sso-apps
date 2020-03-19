package io.fdlessard.codebites.oauth2.sso.apps.ui;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class UiController {

  @Autowired
  private WebClient webClientApi1;
  @Autowired
  private WebClient webClientApi2;

  @GetMapping("/ui")
  public Account uiAccount(Principal principal) {

    log.debug("UiController.uiAccount()");

    return buildAccount();
  }

  @GetMapping("/api1")
  public Account api1(Principal principal) {

    log.debug("UiController.api1() principal {}", principal);

    return this.webClientApi1
        .get()
        .uri("http://localhost:8081/api1")
        .retrieve()
        .bodyToMono(Account.class)
        .block();
  }

  @GetMapping("/api12")
  public String api12(Principal principal) {

    log.debug("UiController.api12() principal {}", principal);

    return this.webClientApi1
        .get()
        .uri("http://localhost:8081/api12")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  @GetMapping("/api2")
  public Account api2(Principal principal) {

    log.debug("UiController.api2() principal {}", principal);

    return this.webClientApi2
        .get()
        .uri("http://localhost:8082/api2")
        .retrieve()
        .bodyToMono(Account.class)
        .block();
  }

  private static Account buildAccount() {
    return Account.builder()
        .id(0l)
        .code("code 0")
        .name("Account 0")
        .description("Account 0 from UI")
        .build();
  }

}
