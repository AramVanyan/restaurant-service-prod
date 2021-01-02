package com.epam.orderservice.dto;


import com.sun.istack.NotNull;
import lombok.*;

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
    private Long sum;

    @NotNull
    private Date deliveryTime;

    @NotNull
    private String creditCard;

    @NotNull
    private String orderDescription;
}
