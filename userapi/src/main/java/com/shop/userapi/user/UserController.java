package com.shop.userapi.user;

import com.shop.userapi.user.dto.CreateUserDTO;
import com.shop.userapi.user.dto.UpdateUserDTO;
import com.shop.userapi.user.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
  private UserService userService;

  @GetMapping("/find-by-id")
  public ResponseEntity<UserDTO> findUserById(@Valid @NotNull @RequestParam UUID id) {
    return ResponseEntity.ok(userMapper.userToUserDto(userService.findById(id)));
  }

  @GetMapping("/find-by-email")
  public ResponseEntity<UserDTO> findUserByEmail(@Valid @NotNull @RequestParam String email) {
    return ResponseEntity.ok(userMapper.userToUserDto(userService.findByEmail(email)));
  }

  @GetMapping("/find-by-cpf")
  public ResponseEntity<UserDTO> findUserByCpf(@Valid @NotNull @RequestParam String cpf) {
    return ResponseEntity.ok(userMapper.userToUserDto(userService.findByCpf(cpf)));
  }

  @GetMapping("/find-all")
  public ResponseEntity<List<UserDTO>> findAll() {
    return ResponseEntity.ok(userMapper.usersToUsersDto(userService.getAllUsers()));
  }

  @PostMapping("/create")
  public ResponseEntity<UserDTO> createUser(@Valid @NotNull @RequestBody CreateUserDTO dto) {
    return ResponseEntity.ok(
        userMapper.userToUserDto(userService.create(userMapper.createUserDtoToUser(dto))));
  }

  @DeleteMapping("/delete")
  public void deleteUser(@Valid @NotNull @RequestParam UUID id) {
    userService.delete(id);
  }

  @PutMapping("/update")
  public ResponseEntity<UserDTO> updateUser(
      @Valid @NotNull @RequestBody UpdateUserDTO dto,
      @Valid @NotNull @RequestParam(name = "id") UUID id) {
    return ResponseEntity.ok(
        userMapper.userToUserDto(userService.update(userMapper.updateUserDtoToUser(dto), id)));
  }
}
