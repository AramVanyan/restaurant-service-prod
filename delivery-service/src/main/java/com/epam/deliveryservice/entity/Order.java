package com.epam.deliveryservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Timestamp createdDate;

    @Column(name = "delivery_time", columnDefinition="DATETIME",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryTime;

    @Column(name = "credit_card",nullable = false)
    private String creditCard;

    @Column
    private Long sum;

    @Column(name = "order_description",nullable = false)
    private String orderDescription;

}
