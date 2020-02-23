package io.fdlessard.codebites.oauth2.sso.apps.ui;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
public class SpringBootOauth2SsoUiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootOauth2SsoUiApplication.class, args);
  }

  @Configuration
  public static class WebClientConfig {

    @Bean
    WebClient webClient(
        ClientRegistrationRepository clientRegistrations,
        OAuth2AuthorizedClientRepository authorizedClients
    ) {
      ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
          new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations,
              authorizedClients);
      oauth2.setDefaultOAuth2AuthorizedClient(true);
      oauth2.setDefaultClientRegistrationId("azure");
//      oauth2.setDefaultClientRegistrationId("okta");
      return WebClient.builder()
          .apply(oauth2.oauth2Configuration())
          .filters(exchangeFilterFunctions -> {
            exchangeFilterFunctions.add(logRequest());
            exchangeFilterFunctions.add(logResponse());
          })
          .build();
    }
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
