package com.shop.userapi.user;

import com.shop.userapi.user.dto.CreateUserDTO;
import com.shop.userapi.user.dto.UpdateUserDTO;
import com.shop.userapi.user.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
  @Autowired private UserService userService;

  @GetMapping("/find-by-id")
  public UserDTO findUserById(@Valid @NotNull @RequestBody Long id) {
    return userMapper.userToUserDto(userService.findById(id));
  }

  @GetMapping("/find-by-email")
  public UserDTO findUserByEmail(@Valid @NotNull @RequestBody String email) {
    return userMapper.userToUserDto(userService.findByEmail(email));
  }

  @GetMapping("/find-by-cpf")
  public UserDTO findUserByCpf(@Valid @NotNull @RequestBody String cpf) {
    return userMapper.userToUserDto(userService.findByCpf(cpf));
  }

  @PostMapping("/create")
  public UserDTO createUser(@Valid @NotNull @RequestBody CreateUserDTO dto) {
    return userMapper.userToUserDto(userService.create(userMapper.createUserDtoToUser(dto)));
  }

  @DeleteMapping("/delete")
  public void deleteUser(@Valid @NotNull @RequestBody Long id) {
    userService.delete(id);
  }

  @PutMapping("/update/:id")
  public UserDTO updateUser(
      @Valid @NotNull @RequestBody UpdateUserDTO dto,
      @Valid @NotNull @RequestParam(name = "id") Long id) {
    return userMapper.userToUserDto(userService.update(userMapper.updateUserDtoToUser(dto), id));
  }
}
