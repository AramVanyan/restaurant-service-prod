package com.epam.orderservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "scheduled_delivery_time", nullable = false)
    private Timestamp scheduledDeliveryTime;

    @Column(name = "completed", nullable = false)
    private Timestamp completionTime;
}
