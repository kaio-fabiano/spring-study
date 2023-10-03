package com.shop.userapi.user;

import com.shop.userapi.config.exception.ApiRequestException;
import java.util.UUID;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final String USER_NOT_FOUND_MESSAGE = "User not found";

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
  @Autowired private UserRepository userRepository;

  public User create(User user) {
    return userRepository.save(user);
  }

  public void delete(UUID id) {
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
}
