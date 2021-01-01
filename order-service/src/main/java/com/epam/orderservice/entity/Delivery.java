package com.epam.orderservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "scheduled_delivery_time",columnDefinition="DATETIME",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledDeliveryTime;

    @Column(name = "completed", columnDefinition="DATETIME",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date completionTime = new Date(scheduledDeliveryTime.getTime() + 3600*1000*2);
}
