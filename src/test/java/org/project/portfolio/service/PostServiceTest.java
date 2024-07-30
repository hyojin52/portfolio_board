package org.project.portfolio.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.portfolio.controller.request.PostCreateRequest;
import org.project.portfolio.entity.AccountEntity;
import org.project.portfolio.entity.PostEntity;
import org.project.portfolio.exception.ErrorCode;
import org.project.portfolio.exception.PortfolioApplicationException;
import org.project.portfolio.repository.AccountRepository;
import org.project.portfolio.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {
  
  @Autowired
  private PostService postService;
  
  @MockBean
  private PostRepository postRepository;
  
  @MockBean
  private AccountRepository accountRepository;
  
  @Test
  public void 게시글_생성() {
    String title = "title";
    String body = "body";
    PostCreateRequest postCreateRequest = new PostCreateRequest(title, body);
    String email = "sara@gmail.com";
    
    when(accountRepository.findByEmail(email)).thenReturn(Optional.of(mock(AccountEntity.class)));
    when(postRepository.save(any())).thenReturn(mock(PostEntity.class));
    
    Assertions.assertDoesNotThrow(() -> postService.create(postCreateRequest, email));
  }
  
  @Test
  public void 게시글_생성시_권한_없는_경우() {
    String title = "title";
    String body = "body";
    PostCreateRequest postCreateRequest = new PostCreateRequest(title, body);
    String email = "sara@gmail.com";
    
    when(accountRepository.findByEmail(email)).thenReturn(Optional.empty());
    
    PortfolioApplicationException e = Assertions.assertThrows(PortfolioApplicationException.class, () -> postService.create(postCreateRequest, email));
    Assertions.assertEquals(e.getErrorCode(), ErrorCode.USER_NOT_FOUND);
  }
  
  @Test
  public void 게시글_생성시_글자수_200_초과인_경우() {
    String more_then_200_title = "title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than";
    String body = "body";
    PostCreateRequest postCreateRequest = new PostCreateRequest(more_then_200_title, body);
    String email = "sara@gmail.com";
    
    when(accountRepository.findByEmail(email)).thenReturn(Optional.of(mock(AccountEntity.class)));
    
    PortfolioApplicationException e = Assertions.assertThrows(PortfolioApplicationException.class, () -> postService.create(postCreateRequest, email));
    Assertions.assertEquals(e.getErrorCode(), ErrorCode.INVALID_TITLE);
  }
  
  @Test
  public void 게시글_생성시_글자수_1000_초과인_경우() {
    String title = "title";
    String more_than_1000_body = "title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200_title_more_than_200";
    PostCreateRequest postCreateRequest = new PostCreateRequest(title, more_than_1000_body);
    String email = "sara@gmail.com";
    
    when(accountRepository.findByEmail(email)).thenReturn(Optional.of(mock(AccountEntity.class)));
    
    PortfolioApplicationException e = Assertions.assertThrows(PortfolioApplicationException.class, () -> postService.create(postCreateRequest, email));
    Assertions.assertEquals(e.getErrorCode(), ErrorCode.INVALID_CONTENT);
  }
}
