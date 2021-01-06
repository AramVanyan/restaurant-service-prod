package com.epam.orderservice.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long orderSum;

    @NotNull
    private Timestamp deliveryTime;

    @NotNull
    private String creditCard;

    @NotNull
    private String orderDescription;
}
