package io.fdlessard.codebites.oauth2.sso.apps.ui;

import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  private ClientRegistrationRepository clientRegistrationRepository;
  private Environment env;

  public SecurityConfiguration(ClientRegistrationRepository clientRegistrationRepository, Environment env) {
    this.clientRegistrationRepository = clientRegistrationRepository;
    this.env = env;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .anyRequest().authenticated())
        .oauth2Login(oauth2Login ->
            oauth2Login
                .loginPage("/oauth2/authorization/ui-login")
                .failureUrl("/login?error")
                .permitAll()
                .authorizationEndpoint()
                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI)))
        .oauth2Client();
  }

}
