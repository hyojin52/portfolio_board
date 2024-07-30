package org.project.portfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.portfolio.controller.request.PostCreateRequest;
import org.project.portfolio.controller.response.PostResponse;
import org.project.portfolio.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class PostControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private ObjectMapper objectMapper;
  
  @MockBean
  private PostService postService;
  
  @Test
  @WithMockUser
  public void 게시글_생성() throws Exception {
    String title = "title";
    String body = "body";
    PostCreateRequest postCreateRequest = new PostCreateRequest(title, body);
    when(postService.create(postCreateRequest, "sara@gmail.com")).thenReturn(mock(PostResponse.class));
    
    mockMvc.perform(post("/api/v1/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(postCreateRequest))
            ).andDo(print())
            .andExpect(status().isOk());
  }
  
  @Test
  @WithAnonymousUser
  public void 권한없는_유저가_게시글_생성() throws Exception {
    String title = "title";
    String body = "body";
    
    mockMvc.perform(post("/api/v1/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, body)))
            ).andDo(print())
            .andExpect(status().isUnauthorized());
  }
}
