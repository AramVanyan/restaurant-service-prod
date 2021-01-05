package com.epam.paymentservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
