package com.shop.userapi.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    String email = "jondoe@gmail.com";
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
    String email = "jondoe@gmail.com";

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
            .email("jondoe@gmail.com")
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
}
