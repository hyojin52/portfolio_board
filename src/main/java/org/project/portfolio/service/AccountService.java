package org.project.portfolio.service;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.controller.Account;
import org.project.portfolio.controller.request.AccountJoinRequest;
import org.project.portfolio.controller.request.AccountLoginRequest;
import org.project.portfolio.entity.AccountEntity;
import org.project.portfolio.entity.AccountRole;
import org.project.portfolio.exception.ErrorCode;
import org.project.portfolio.exception.PortfolioApplicationException;
import org.project.portfolio.repository.AccountRepository;
import org.project.portfolio.utils.JwtTokenUtils;
import org.project.portfolio.utils.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
  
  private final AccountRepository accountRepository;
  private final BCryptPasswordEncoder encoder;
  
  @Value("${jwt.secret-key}")
  private String secretKey;
  
  @Value("${jwt.token.expired-time-ms}")
  private Long expiredTimeMs;
  
  @Transactional
  public void join(AccountJoinRequest request) {
    if(!Validator.isValidEmail(request.getEmail())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_EMAIL);
    }
    if(!Validator.isValidMobile(request.getMobileNumber())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_MOBILE);
    }
    if(!Validator.isValidName(request.getName())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_NAME);
    }
    if(!Validator.isValidPassword(request.getPassword())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_PASSWORD);
    }
    
    // 이메일 중복 검사
    accountRepository.findByEmail(request.getEmail())
            .ifPresent((it) -> { throw new PortfolioApplicationException(ErrorCode.DUPLICATE_EMAIL); });
    
    AccountEntity accountEntity = AccountEntity.builder()
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .mobileNumber(request.getMobileNumber())
            .name(request.getName())
            .role(AccountRole.USER)
            .build();
    accountRepository.save(accountEntity);
  }
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AccountEntity accountEntity = accountRepository.findByEmail(email).orElseThrow(() ->
            new PortfolioApplicationException(ErrorCode.USER_NOT_FOUND)
    );
    
    return Account.fromEntity(accountEntity);
  }
  
  public String login(AccountLoginRequest request) {
    AccountEntity accountEntity = accountRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new PortfolioApplicationException(ErrorCode.USER_NOT_FOUND)
            );
    
    if(!encoder.matches(request.getPassword(), accountEntity.getPassword())) {
      throw new PortfolioApplicationException(ErrorCode.INVALID_PASSWORD);
    }
    
    String token = JwtTokenUtils.generateToken(request.getEmail(), secretKey, expiredTimeMs);
    return token;
  }
}
