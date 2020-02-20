package io.fdlessard.codebites.oauth2.sso.apps.ui;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class UiController {

  private WebClient webClient;

  public UiController(WebClient webClient) {
    this.webClient = webClient;
  }

  @GetMapping("/ui")
  public String ui(Principal principal) {
    log.debug("Called UiController.ui() endpoint");
    String name = principal.getName();
    return "You made it to protected ui! " + name;
  }

  @GetMapping("/api1")
  public String api1() {
    log.debug("Calling UiController.api1() endpoint, before remote call");

    return this.webClient
        .get()
        .uri("http://localhost:8081/api1")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  @GetMapping("/api2")
  public String api2() {
    log.debug("Calling UiController.api2() endpoint, before remote call");

    return this.webClient
        .get()
        .uri("http://localhost:8082/api2")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }


}
