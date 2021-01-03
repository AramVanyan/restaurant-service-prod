package com.epam.userservice.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
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
