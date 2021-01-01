package com.epam.orderservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreditCardDto {
    @NotNull
    private String creditCardId;
}
