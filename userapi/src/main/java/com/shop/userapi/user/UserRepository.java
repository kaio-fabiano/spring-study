package com.shop.userapi.user;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.CPF = ?1")
  Optional<User> findByCpf(String cpf);

  @Query(
      "SELECT CASE WHEN COUNT(u) > 0 THEN"
          + " true ELSE false END"
          + " FROM User u"
          + " WHERE u.email = ?1 OR u.CPF = ?2")
  Boolean existsUniqueFields(String email, String cpf);

  @Query(
      "SELECT CASE WHEN COUNT(u) > 0 THEN"
          + " true ELSE false END"
          + " FROM User u"
          + " WHERE u.id <> ?1")
  boolean existsById(@NotNull UUID id);
}
