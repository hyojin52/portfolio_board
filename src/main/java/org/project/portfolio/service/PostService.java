package org.project.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.controller.request.PostCreateRequest;
import org.project.portfolio.controller.request.PostUpdateRequest;
import org.project.portfolio.controller.response.PostResponse;
import org.project.portfolio.entity.AccountEntity;
import org.project.portfolio.entity.PostEntity;
import org.project.portfolio.exception.ErrorCode;
import org.project.portfolio.exception.PortfolioApplicationException;
import org.project.portfolio.repository.AccountRepository;
import org.project.portfolio.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
  private final AccountRepository accountRepository;
  private final PostRepository postRepository;
  
  public PostResponse create(PostCreateRequest request, String email) {
    AccountEntity accountEntity = accountRepository.findByEmail(email).orElseThrow(
            () -> new PortfolioApplicationException(ErrorCode.USER_NOT_FOUND)
    );
    
    if(request.getTitle().length() > 200) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_TITLE);
    }
    
    if(request.getContent().length() > 1000) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_CONTENT);
    }
    
    PostEntity buildEntity = PostEntity.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .account(accountEntity)
            .build();
    return PostResponse.fromEntity(postRepository.save(buildEntity));
  }
  
  public PostResponse update(Integer postId, PostUpdateRequest request, String email) {
    AccountEntity accountEntity = accountRepository.findByEmail(email).orElseThrow(
            () -> new PortfolioApplicationException(ErrorCode.USER_NOT_FOUND)
    );
    
    PostEntity postEntity = postRepository.findById(postId).orElseThrow(
            () -> new PortfolioApplicationException(ErrorCode.POST_NOT_FOUND)
    );
    
    if(request.getTitle().length() > 200) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_TITLE);
    }
    
    if(request.getContent().length() > 1000) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_CONTENT);
    }
    
    LocalDateTime createdAt = postEntity.getCreatedAt();
    if(LocalDateTime.now().isAfter(createdAt.plusDays(10))) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_POST_UPDATE);
    }
    
    postEntity.setTitle(request.getTitle());
    postEntity.setContent(request.getContent());
    return PostResponse.fromEntity(postRepository.save(postEntity));
  }
}
