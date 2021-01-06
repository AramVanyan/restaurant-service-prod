package com.epam.historyservice.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Payment {
    private Long id;

    private Long orderId;

    private Long sum;

    private CreditCard creditCard;
}
