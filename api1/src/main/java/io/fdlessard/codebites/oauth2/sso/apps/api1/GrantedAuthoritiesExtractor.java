package io.fdlessard.codebites.oauth2.sso.apps.api1;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {

  @Override
  protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

    Collection<GrantedAuthority> authorities = super.extractAuthorities(jwt);
    Collection<String> roles = (Collection<String>) jwt.getClaims().get("roles");

    if(roles == null || roles.isEmpty()) {
      return  authorities;
    }

    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toCollection(() -> authorities));
  }
}
