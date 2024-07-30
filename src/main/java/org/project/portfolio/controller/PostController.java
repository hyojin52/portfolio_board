package org.project.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.controller.request.PostCreateRequest;
import org.project.portfolio.controller.request.PostUpdateRequest;
import org.project.portfolio.controller.response.PostResponse;
import org.project.portfolio.controller.response.Result;
import org.project.portfolio.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
  
  @PutMapping("/{id}")
  public Result<PostResponse> update(
          @PathVariable Integer id,
          @RequestBody PostUpdateRequest request,
          Authentication authentication) {
    PostResponse postResponse = postService.update(id, request, authentication.getName());
    return Result.OK(postResponse);
  }
}
