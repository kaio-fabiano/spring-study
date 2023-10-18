package com.shop.userapi.user;

import static com.shop.userapi.user.UserService.USER_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shop.userapi.config.exception.ApiRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;
  private UserService underTest;

  private static User buildUser(UUID id) {
    return User.builder()
        .id(id)
        .email("existing@example.com")
        .name("John Doe")
        .CPF("12345678901")
        .password("123456")
        .phone("(82) 9 9999-9999")
        .build();
  }

  @BeforeEach
  void setUp() {
    underTest = new UserService(userRepository);
  }

  /** Method under test: {@link UserService#create(User)} */
  @Test
  void canCreateUser() {
    // Given
    User user = buildUser(UUID.randomUUID());

    // When
    underTest.create(user);

    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    verify(userRepository).save(userArgumentCaptor.capture());

    User capturedUser = userArgumentCaptor.getValue();

    assertThat(capturedUser).isEqualTo(user);
  }

  /** Method under test: {@link UserService#create(User)} */
  @Test
  void willThrowWhenEmailOrCPFIsTaken() {
    // Given
    User user = buildUser(UUID.randomUUID());

    given(userRepository.existsUniqueFields(user.getEmail(), user.getCPF())).willReturn(true);

    // When
    // Then
    assertThatThrownBy(() -> underTest.create(user))
        .isInstanceOf(ApiRequestException.class)
        .hasMessageContaining("Email or CPF already exists");

    verify(userRepository, never()).save(any());
  }

  /** Method under test: {@link UserService#delete(UUID)} */
  @Test
  void canDelete() {
    // Given
    UUID id = UUID.randomUUID();
    given(userRepository.existsById(id)).willReturn(true);

    // When
    underTest.delete(id);

    // Then
    verify(userRepository).deleteById(id);
  }

  /** Method under test: {@link UserService#delete(UUID)} */
  @Test
  void willThrowWhenUserNotFoundInDelete() {
    // Given
    UUID id = UUID.randomUUID();
    given(userRepository.existsById(id)).willReturn(false);

    // When
    // Then
    assertThatThrownBy(() -> underTest.delete(id))
        .isInstanceOf(ApiRequestException.class)
        .hasMessageContaining("User not found");

    verify(userRepository, never()).deleteById(any());
  }

  /** Method under test: {@link UserService#update(User, UUID)} */
  @Test
  void canUpdate() {
    // Given
    UUID id = UUID.randomUUID();
    User user = buildUser(id);

    User updateUser =
        User.builder()
            .id(id)
            .email("jondoe@gmail.com")
            .name("Jon Doe")
            .CPF("12345678901")
            .password("password")
            .phone("(99) 9 9999-9999")
            .build();

    given(userRepository.findById(id)).willReturn(java.util.Optional.of(user));

    // When
    User result = underTest.update(updateUser, id);

    // Then
    assertThat(result.getId()).isEqualTo(user.getId());
    assertThat(result.getName()).isEqualTo(user.getName());
    assertThat(result.getCPF()).isEqualTo(user.getCPF());
    assertThat(result.getEmail()).isEqualTo(user.getEmail());
    assertThat(result.getPassword()).isEqualTo(updateUser.getPassword());
    assertThat(result.getPhone()).isEqualTo(updateUser.getPhone());

    verify(userRepository).findById(id);
  }

  /** Method under test: {@link UserService#update(User, UUID)} */
  @Test
  void willThrowWhenUpdateNonExistingUser() {
    // Given
    UUID id = UUID.randomUUID();
    User updatedUser = buildUser(id);

    given(userRepository.findById(id)).willReturn(Optional.empty());

    // When
    ApiRequestException exception =
        Assertions.assertThrows(
            ApiRequestException.class,
            () -> {
              underTest.update(updatedUser, id);
            });

    // Then
    Assertions.assertEquals(USER_NOT_FOUND_MESSAGE, exception.getMessage());
    verify(userRepository).findById(id);
    verify(userRepository, never()).save(any());
  }

  /** Method under test: {@link UserService#findById(UUID)} */
  @Test
  void canFindByIdExistingUser() {
    // Given
    UUID id = UUID.randomUUID();
    User existingUser = buildUser(id);

    given(userRepository.findById(id)).willReturn(Optional.of(existingUser));

    // When
    User result = underTest.findById(id);

    // Then
    assertThat(existingUser).isEqualTo(result);
  }

  /** Method under test: {@link UserService#findById(UUID)} */
  @Test
  void willThrowWhenFindByIdNonExistingUser() {
    // Given
    UUID id = UUID.randomUUID();

    given(userRepository.findById(id)).willReturn(Optional.empty());

    // When
    // Then
    assertThatThrownBy(() -> underTest.findById(id))
        .isInstanceOf(ApiRequestException.class)
        .hasMessageContaining(USER_NOT_FOUND_MESSAGE);
  }

  /** Method under test: {@link UserService#findByEmail(String)} */
  @Test
  void canFindByEmailExistingUser() {
    // Given
    String email = "johndoe@gmail.com";
    User existingUser = buildUser(UUID.randomUUID());

    given(userRepository.findByEmail(email)).willReturn(Optional.of(existingUser));

    // When
    User result = underTest.findByEmail(email);

    // Then
    assertThat(existingUser).isEqualTo(result);
  }

  /** Method under test: {@link UserService#findByEmail(String)} */
  @Test
  void willThrowWhenFindByEmailNonExistingUser() {
    // Given
    String email = "johndoe@gmail.com";

    given(userRepository.findByEmail(email)).willReturn(Optional.empty());

    // When
    // Then
    assertThatThrownBy(() -> underTest.findByEmail(email))
        .isInstanceOf(ApiRequestException.class)
        .hasMessageContaining(USER_NOT_FOUND_MESSAGE);
  }

  /** Method under test: {@link UserService#findByCpf(String)} */
  @Test
  void canFindByCPFExistingUser() {
    // Given
    String CPF = "12345678901";
    User existingUser = buildUser(UUID.randomUUID());

    existingUser.setCPF(CPF);

    given(userRepository.findByCpf(CPF)).willReturn(Optional.of(existingUser));

    // When
    User result = underTest.findByCpf(CPF);

    // Then
    assertThat(existingUser).isEqualTo(result);
  }

  /** Method under test: {@link UserService#findByCpf(String)} */
  @Test
  void willThrowWhenFindByCPFNonExistingUser() {
    // Given
    String CPF = "12345678901";

    given(userRepository.findByCpf(CPF)).willReturn(Optional.empty());

    // When
    // Then
    assertThatThrownBy(() -> underTest.findByCpf(CPF))
        .isInstanceOf(ApiRequestException.class)
        .hasMessageContaining(USER_NOT_FOUND_MESSAGE);
  }

  /** Method under test: {@link UserService#getAllUsers()} */
  @Test
  void canGetAllUsers() {
    // Given
    List<User> users = new ArrayList<>();
    users.add(buildUser(UUID.randomUUID()));
    users.add(buildUser(UUID.randomUUID()));
    users.add(buildUser(UUID.randomUUID()));
    when(userRepository.findAll()).thenReturn(users);

    // When
    List<User> result = underTest.getAllUsers();

    // Then
    assertEquals(3, result.size());
    assertSame(users, result);
    verify(userRepository).findAll();
  }
}
