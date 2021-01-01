package com.epam.historyservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "scheduled_delivery_time",columnDefinition="DATETIME",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledDeliveryTime;

    @Column(name = "completed", columnDefinition="DATETIME",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionTime = new Date(scheduledDeliveryTime.getTime() + 3600*1000*2);
}

