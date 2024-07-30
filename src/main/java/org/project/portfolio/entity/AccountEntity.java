package org.project.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.portfolio.entity.enums.AccountRole;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity extends AuditingEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(length = 50, nullable = false)
  private String email;
  
  @Column(length = 255, nullable = false)
  private String password;
  
  @Column(length = 50, nullable = false)
  private String mobileNumber;
  
  @Column(length = 10, nullable = false)
  @Enumerated(EnumType.STRING)
  private AccountRole role = AccountRole.USER;
  
  @Column(length = 20, nullable = false)
  private String name;
}
