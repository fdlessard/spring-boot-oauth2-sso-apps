package io.fdlessard.codebites.oauth2.sso.apps.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfiguration {

  @Bean
  WebClient webClientApi1(
      ClientRegistrationRepository clientRegistrations,
      OAuth2AuthorizedClientRepository authorizedClients
  ) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations,
            authorizedClients);
    oauth2.setDefaultOAuth2AuthorizedClient(true);
    oauth2.setDefaultClientRegistrationId("api-1");
    // oauth2.setDefaultClientRegistrationId("ui-login");
    return WebClient.builder()
        .apply(oauth2.oauth2Configuration())
        .filters(exchangeFilterFunctions -> {
          exchangeFilterFunctions.add(logRequest());
          exchangeFilterFunctions.add(logResponse());
        })
        .build();
  }

  @Bean
  WebClient webClientApi2(
      ClientRegistrationRepository clientRegistrations,
      OAuth2AuthorizedClientRepository authorizedClients
  ) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations,
            authorizedClients);
    oauth2.setDefaultOAuth2AuthorizedClient(true);
    oauth2.setDefaultClientRegistrationId("api-2");
//      oauth2.setDefaultClientRegistrationId("ui-login");
    return WebClient.builder()
        .apply(oauth2.oauth2Configuration())
        .filters(exchangeFilterFunctions -> {
          exchangeFilterFunctions.add(logRequest());
          exchangeFilterFunctions.add(logResponse());
        })
        .build();
  }

  private static ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      if (log.isDebugEnabled()) {
        StringBuilder sb = new StringBuilder("Request: \n")
            .append(clientRequest.method())
            .append(" ")
            .append(clientRequest.url());
        clientRequest
            .headers()
            .forEach((name, values) -> values.forEach(value -> sb
                .append("\n")
                .append(name)
                .append(":")
                .append(value)));
        log.debug(sb.toString());
      }
      return Mono.just(clientRequest);
    });


  }

  private static ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      if (log.isDebugEnabled()) {
        StringBuilder sb = new StringBuilder("Response: \n")
            .append("Status: ")
            .append(clientResponse.rawStatusCode());
        clientResponse
            .headers()
            .asHttpHeaders()
            .forEach((key, value1) -> value1.forEach(value -> sb
                .append("\n")
                .append(key)
                .append(":")
                .append(value)));
        log.debug(sb.toString());
      }
      return Mono.just(clientResponse);
    });
  }

}
