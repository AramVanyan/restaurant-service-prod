package com.epam.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {
    @NotNull
    private String creditCardNumber;

    @NotNull
    private Long balance;

    public CreditCardDto(@NotNull String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
