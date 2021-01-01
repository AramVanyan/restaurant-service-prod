package com.epam.paymentservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column
    private Long sum;

    @ManyToOne
    @JoinColumn(name = "credit_card")
    private CreditCard creditCard;
}
