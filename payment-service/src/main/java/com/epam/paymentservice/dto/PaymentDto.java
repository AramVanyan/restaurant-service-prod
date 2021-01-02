package com.epam.paymentservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
public class PaymentDto {
    @NotNull
    private CreditCardDto creditCardDto;

    @NotNull
    private Long sum;

    @NotNull
    private Long orderId;

    @NotNull
    private Boolean toBeCompensated;

}
