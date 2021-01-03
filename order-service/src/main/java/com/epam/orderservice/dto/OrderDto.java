package com.epam.orderservice.dto;


import com.sun.istack.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;


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
