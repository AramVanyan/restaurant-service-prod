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

    @Column(name="sum")
    private Long sum;

    @Column(name = "ticket_creation_time")
    private Timestamp ticketCreationTime;

    @Column(name = "scheduled_delivery_time")
    private Timestamp scheduledDeliveryTime;

    @Column(name = "completed")
    private Timestamp completionTime;
}

