package com.epam.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credit_card")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreditCard {
    @Id
    @Column(name = "card_number")
    private String cardNumber;

    @Column(nullable = false)
    private Long balance;

    @OneToMany(mappedBy = "creditCard")
    private List<Payment> listOfPayments = new ArrayList<>();

}
