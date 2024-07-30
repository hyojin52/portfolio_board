package org.project.portfolio.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountLoginRequest {
  private String email;
  private String password;
}
