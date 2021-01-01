package com.epam.orderservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class PaymentDto {

    @NotNull
    private String creditCardDto;

    @NotNull
    private Long sum;

    @NotNull
    private Long orderId;

    @NotNull
    private Boolean toBeCompensated;

}
