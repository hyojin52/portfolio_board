package org.project.portfolio.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  // 4xx - 1000
  INVALID_EMAIL(HttpStatus.BAD_REQUEST, "1000" , "Email is invalid"),
  INVALID_MOBILE(HttpStatus.BAD_REQUEST, "1001" , "Mobile phone number is invalid"),
  INVALID_NAME(HttpStatus.BAD_REQUEST, "1002" , "Name is invalid"),
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "1003" , "Password is invalid"),
  DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "1004", "Email is duplicate"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "1005", "User not found"),
  
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "2000", "Token is invalid"),
  // 5xx -5000
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "Internal server error"),
  ;
  
  private HttpStatus httpStatus;
  private String code;
  private String message;
}
