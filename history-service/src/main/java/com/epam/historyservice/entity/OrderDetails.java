package com.epam.historyservice.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="order_description")
    private String orderDescription;

    @Column(name = "order_sum")
    private Long orderSum;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "scheduled_delivery_time")
    private Timestamp scheduledDeliveryTime;

    @Column(name = "completed")
    private Timestamp completionTime;
}

