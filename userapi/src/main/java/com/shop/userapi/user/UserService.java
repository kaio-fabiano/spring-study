package com.shop.userapi.user;

import java.util.UUID;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    User updatedEntity = userMapper.mergeUser(user, entity);
    userRepository.save(updatedEntity);
    return updatedEntity;
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElse(null);
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  public User findByCpf(String cpf) {
    return userRepository.findByCpf(cpf).orElse(null);
  }
}
