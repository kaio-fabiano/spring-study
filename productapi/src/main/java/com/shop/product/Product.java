package com.shop.product;

import com.shop.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", columnDefinition = "varchar(255) not null")
  private String name;

  @Column(name = "description", columnDefinition = "varchar(255) not null")
  private String description;

  @Column(name = "price", columnDefinition = "double precision not null")
  private Double price;

  @Column(name = "quantity", columnDefinition = "int not null")
  private Integer quantity;

  @Column(name = "user_id", columnDefinition = "uuid not null")
  private UUID userId;
}
