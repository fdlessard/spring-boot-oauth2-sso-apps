package io.fdlessard.codebites.oauth2.sso.apps.ui;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  public SecurityConfiguration() {
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
                .authorizationEndpoint())
        .oauth2Client();
  }

}
