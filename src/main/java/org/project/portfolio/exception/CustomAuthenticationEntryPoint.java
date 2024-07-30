package org.project.portfolio.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.portfolio.controller.response.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  
  
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(ErrorCode.INVALID_TOKEN.getHttpStatus().value());
    response.getWriter().write(Result.ERROR(ErrorCode.INVALID_TOKEN.name()).toStream());
  }
}
