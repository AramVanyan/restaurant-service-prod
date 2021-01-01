package com.epam.historyservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Payment {
    private Long id;

    private Long orderId;

    private Long sum;

    private CreditCard creditCard;
}
