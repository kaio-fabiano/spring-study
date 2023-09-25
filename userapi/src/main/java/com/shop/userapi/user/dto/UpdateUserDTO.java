package com.shop.userapi.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
  private String name;
  private String email;
  private String password;
  private String phone;
}
