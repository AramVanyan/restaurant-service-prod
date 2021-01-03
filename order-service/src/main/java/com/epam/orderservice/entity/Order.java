package com.epam.orderservice.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "delivery_time", nullable = false)
    private Timestamp deliveryTime;

    @Column(name = "credit_card",nullable = false)
    private String creditCard;

    @Column(name = "order_sum", nullable = false)
    private Long orderSum;

    @Column(name = "order_description",nullable = false)
    private String orderDescription;

}
