package com.epam.paymentservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreditCardDto {
    @NotNull
    private String creditCardNumber;

    @NotNull
    private Long balance;
}
