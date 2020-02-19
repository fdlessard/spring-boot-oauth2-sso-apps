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

  @GetMapping("/api")
  String api() {
    log.debug("Calling UiController.api() endpoint, before remote call");

    return this.webClient
        .get()
        .uri("http://localhost:8081/api")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }


}
