package io.fdlessard.codebites.oauth2.sso.apps.api1;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = this.addHeaders((HttpServletResponse) res);

    if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, res);
    }
  }

  private HttpServletResponse addHeaders(HttpServletResponse response) {

    response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE, PATCH");
    response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
    response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with, authorization, content-type");

    return response;
  }

}