package org.project.portfolio.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.portfolio.entity.PostEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponse {
  private String title;
  private String content;
  private AccountResponse account;
  private LocalDateTime createdDt;
  private LocalDateTime updatedDt;
  
  public static PostResponse fromEntity(PostEntity postEntity) {
    return PostResponse.builder()
            .title(postEntity.getTitle())
            .content(postEntity.getContent())
            .account(AccountResponse.fromEntity(postEntity.getAccount()))
            .createdDt(postEntity.getCreatedAt())
            .updatedDt(postEntity.getUpdatedAt())
            .build();
  }
}
