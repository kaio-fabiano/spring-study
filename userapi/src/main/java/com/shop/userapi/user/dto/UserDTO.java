package com.shop.userapi.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private UUID id;
  private String CPF;
  private String name;
  private String email;
  private String password;
  private String phone;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
