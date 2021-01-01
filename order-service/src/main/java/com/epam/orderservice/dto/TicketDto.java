package com.epam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {
    @NotNull
    private Long orderId;
    @NotNull
    private Boolean toBeCompensated;
}
