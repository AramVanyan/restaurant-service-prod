package com.epam.paymentservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credit_card")
@NoArgsConstructor
@Data
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long balance;

    @OneToMany(mappedBy = "creditCard")
    private List<Payment> listOfPayments = new ArrayList<>();

}
