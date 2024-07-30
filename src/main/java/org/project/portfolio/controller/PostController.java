package org.project.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.controller.request.PostCreateRequest;
import org.project.portfolio.controller.response.PostResponse;
import org.project.portfolio.controller.response.Result;
import org.project.portfolio.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
  
  private final PostService postService;
  
  @PostMapping
  public Result<PostResponse> create(
          @RequestBody PostCreateRequest request,
          Authentication authentication) {
    PostResponse postResponse = postService.create(request, authentication.getName());
    return Result.OK(postResponse);
  }
}
