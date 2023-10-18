package com.shop.userapi.user;

import com.shop.userapi.config.exception.ApiRequestException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

  public static final String USER_NOT_FOUND_MESSAGE = "User not found";
  private final UserRepository userRepository;
  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  public User create(User user) {
    if (Boolean.TRUE.equals(
        this.userRepository.existsUniqueFields(user.getEmail(), user.getCPF()))) {
      throw new ApiRequestException("Email or CPF already exists");
    }

    this.userRepository.save(user);

    return user;
  }

  public void delete(UUID id) {
    if (!this.userRepository.existsById(id)) {
      throw new ApiRequestException(USER_NOT_FOUND_MESSAGE);
    }
    userRepository.deleteById(id);
  }

  public User update(User user, UUID id) {
    User entity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND_MESSAGE));

    User updatedEntity = userMapper.mergeUser(user, entity);

    userRepository.save(updatedEntity);

    return updatedEntity;
  }

  public User findById(UUID id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND_MESSAGE));
  }

  public User findByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND_MESSAGE));
  }

  public User findByCpf(String cpf) {
    return userRepository
        .findByCpf(cpf)
        .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND_MESSAGE));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}
