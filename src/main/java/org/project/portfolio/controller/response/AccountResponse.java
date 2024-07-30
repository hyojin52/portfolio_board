package org.project.portfolio.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.portfolio.controller.request.AccountJoinRequest;
import org.project.portfolio.entity.AccountEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponse {
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  public static AccountResponse fromEntity(AccountEntity entity) {
    return AccountResponse.builder()
            .email(entity.getEmail())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
  }
}
