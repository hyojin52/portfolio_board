package org.project.portfolio.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCreateRequest {
  
  private final String title;
  private final String content;
}
