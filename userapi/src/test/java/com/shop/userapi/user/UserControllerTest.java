package com.shop.userapi.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.userapi.user.dtos.CreateUserDTO;
import com.shop.userapi.user.dtos.UpdateUserDTO;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  private static final String email = "jondoe@gmail.com";
  private static final String CPF = "14016040485";
  private MockMvc underTest;
  @Mock private UserRepository userRepository;

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void setUp() {
    this.underTest =
        MockMvcBuilders.standaloneSetup(new UserController(new UserService(userRepository)))
            .build();
  }

  /** Method under test: {@link UserController#findUserById(UUID)} */
  @Test
  void canFindUserById() throws Exception {
    // Given
    final UUID id = UUID.randomUUID();

    User user =
        User.builder()
            .id(id)
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    // When
    given(userRepository.findById(user.getId())).willReturn(java.util.Optional.of(user));

    ResultActions result =
        underTest.perform(
            MockMvcRequestBuilders.get("/users/find-by-id")
                .param("id", id.toString())
                .accept("application/json"));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
  }

  /** Method under test: {@link UserController#findUserByEmail(String)} */
  @Test
  void canFindUserByEmail() throws Exception {
    // Given
    final UUID id = UUID.randomUUID();

    User user =
        User.builder()
            .id(id)
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    // When
    given(userRepository.findByEmail(user.getEmail())).willReturn(java.util.Optional.of(user));

    ResultActions result =
        underTest.perform(
            MockMvcRequestBuilders.get("/users/find-by-email")
                .param("email", email)
                .accept("application/json"));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
  }

  /** Method under test: {@link UserController#findUserByCpf(String)} */
  @Test
  void canFindUserByCPF() throws Exception {
    // Given
    final UUID id = UUID.randomUUID();

    User user =
        User.builder()
            .id(id)
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    // When
    given(userRepository.findByCpf(user.getCPF())).willReturn(java.util.Optional.of(user));

    ResultActions result =
        underTest.perform(
            MockMvcRequestBuilders.get("/users/find-by-cpf")
                .param("cpf", CPF)
                .accept("application/json"));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
  }

  /** Method under test: {@link UserController#findAll()} */
  @Test
  void canFindAllUsers() throws Exception {
    // Given
    final UUID id = UUID.randomUUID();

    User user =
        User.builder()
            .id(id)
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    given(userRepository.findAll()).willReturn(java.util.List.of(user));

    // When
    ResultActions result =
        underTest.perform(MockMvcRequestBuilders.get("/users/find-all").accept("application/json"));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value(email));
  }

  /** Method under test: {@link UserController#createUser(CreateUserDTO)} */
  @Test
  void canCreateUser() throws Exception {
    // Given
    CreateUserDTO dto =
        CreateUserDTO.builder()
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    // When
    ResultActions result =
        underTest.perform(
            MockMvcRequestBuilders.post("/users/create")
                .contentType("application/json")
                .content(asJsonString(dto))
                .accept("application/json"));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
  }

  /** Method under test: {@link UserController#createUser(CreateUserDTO)} */
  @Test
  void willThrowWhenEmailOrCPFIsTaken() throws Exception {
    // Given
    CreateUserDTO dto =
        CreateUserDTO.builder()
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    given(userRepository.existsUniqueFields(dto.email(), dto.CPF())).willReturn(true);

    // When
    // Then
    assertThatThrownBy(
            () ->
                underTest
                    .perform(
                        MockMvcRequestBuilders.post("/users/create")
                            .contentType("application/json")
                            .content(asJsonString(dto))
                            .accept("application/json"))
                    .andExpect(status().isBadRequest()))
        .isInstanceOf(Exception.class);
  }

  /** Method under test: {@link UserController#deleteUser(UUID)} */
  @Test
  void canDelete() throws Exception {
    // Given
    final UUID id = UUID.randomUUID();

    given(userRepository.existsById(id)).willReturn(true);

    // When
    ResultActions result =
        underTest.perform(
            MockMvcRequestBuilders.delete("/users/delete")
                .param("id", id.toString())
                .accept("application/json"));

    // Then
    result.andExpect(status().isOk());
  }

  /** Method under test: {@link UserController#deleteUser(UUID)} */
  @Test
  void willThrowWhenUserNotFoundInDelete() throws Exception {
    // Given
    final UUID id = UUID.randomUUID();

    given(userRepository.existsById(id)).willReturn(false);

    // When
    // Then
    assertThatThrownBy(
            () ->
                underTest
                    .perform(
                        MockMvcRequestBuilders.delete("/users/delete")
                            .param("id", id.toString())
                            .accept("application/json"))
                    .andExpect(status().isBadRequest()))
        .isInstanceOf(Exception.class);
  }

  /** Method under test: {@link UserController#createUser(CreateUserDTO)} */
  @Test
  void canUpdate() throws Exception {
    // Given
    UUID id = UUID.randomUUID();
    User user =
        User.builder()
            .id(id)
            .email(email)
            .name("Jon Doe")
            .CPF(CPF)
            .password("123456")
            .phone("(42) 97890-1234")
            .build();

    UpdateUserDTO updateUser =
        UpdateUserDTO.builder()
            .email("email@gmail.com")
            .name("Joao da Silva")
            .password("password")
            .phone("(99) 9 9999-9999")
            .build();

    given(userRepository.findById(id)).willReturn(java.util.Optional.of(user));

    // When
    ResultActions result =
        underTest.perform(
            MockMvcRequestBuilders.put("/users/update")
                .param("id", id.toString())
                .contentType("application/json")
                .content(asJsonString(updateUser))
                .accept("application/json"));

    // Then
    result.andExpect(status().isOk());
    result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id.toString()));
    result.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updateUser.email()));
    result.andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(updateUser.phone()));
    result.andExpect(MockMvcResultMatchers.jsonPath("$.password").value(updateUser.password()));
    result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updateUser.name()));
  }
}
