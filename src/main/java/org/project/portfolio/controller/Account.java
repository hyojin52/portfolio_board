package org.project.portfolio.controller;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.portfolio.entity.AccountEntity;
import org.project.portfolio.entity.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements UserDetails {
  private Integer id;
  
  private String email;
  
  private String password;
  
  private String mobileNumber;
  
  private AccountRole role;
  
  private String name;
  
  private LocalDateTime createdAt;
  
  private LocalDateTime updatedAt;
  
  public static Account fromEntity(AccountEntity entity) {
    return Account.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .name(entity.getName())
            .role(entity.getRole())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }
  
  @Override
  public String getUsername() {
    return this.email;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return true;
  }
}
