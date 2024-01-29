package com.example.userservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "_order")
public class Order {
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    LocalDate orderDate;
    BigDecimal totalAmount;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
