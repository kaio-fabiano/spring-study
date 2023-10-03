package com.shop.userapi.user;

import com.shop.userapi.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "CPF", nullable = false, columnDefinition = "VARCHAR(14) NOT NULL")
  private String CPF;

  @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255) NOT NULL")
  private String name;

  @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(255) NOT NULL")
  private String email;

  @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255) NOT NULL")
  private String password;

  @Column(name = "phone", nullable = false, columnDefinition = "VARCHAR(255) NOT NULL")
  private String phone;
}
