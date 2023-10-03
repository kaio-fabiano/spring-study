package com.shop.userapi.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

  @Autowired private UserRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  void itShouldCheckIfUserExistsByEmail() {
    // Given
    String email = "johndoe@gmail.com";
    User user =
        User.builder()
            .email(email)
            .name("Jon Doe")
            .CPF("12345678901")
            .password("123456")
            .phone("(82) 9 9999-9999")
            .build();
    underTest.save(user);

    // When
    boolean expected = underTest.findByEmail(email).isPresent();

    // Then
    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckIfUserDoesNotExistsByEmail() {
    // Given
    String email = "johndoe@gmail.com";

    // When
    boolean expected = underTest.findByEmail(email).isPresent();

    // Then
    assertThat(expected).isFalse();
  }

  @Test
  void itShouldCheckIfUserExistsByCPF() {
    // Given
    String CPF = "14016040485";
    User user =
        User.builder()
            .email("johndoe@gmail.com")
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(82) 9 9999-9999")
            .build();
    underTest.save(user);

    // When
    boolean expected = underTest.findByCpf(CPF).isPresent();

    // Then
    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckIfUserDoesNotExistsByCPF() {
    // Given
    String CPF = "12345678901";

    // When
    boolean expected = underTest.findByCpf(CPF).isPresent();

    // Then
    assertThat(expected).isFalse();
  }

  @Test
  void itShouldCheckIfExistsEmailOrCPF() {
    // Given
    String email = "johndoe@gmail.com";
    String CPF = "14016040485";
    User user =
        User.builder()
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(82) 9 9999-9999")
            .build();
    underTest.save(user);

    // When
    boolean expected = underTest.existsUniqueFields(email, CPF);

    // Then
    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckIfDoesNotExistsEmailOrCPF() {
    // Given
    String email = "johndoe@gmail.com";
    String CPF = "14016040485";

    // When
    boolean expected = underTest.existsUniqueFields(email, CPF);

    // Then
    assertThat(expected).isFalse();
  }

  @Test
  void itShouldCheckIfUserExistsById() {
    // Given
    UUID id = UUID.randomUUID();
    User user =
        User.builder()
            .id(id)
            .email("johndoe@gmail.com")
            .name("Jon Doe")
            .CPF("14016040485")
            .password("123456")
            .phone("(82) 9 9999-9999")
            .build();
    underTest.save(user);

    // When
    boolean expected = underTest.existsById(id);

    // Then
    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckIfUserDoesNotExistsById() {
    // Given
    UUID id = UUID.randomUUID();

    // When
    boolean expected = underTest.existsById(id);

    // Then
    assertThat(expected).isFalse();
  }
}
