package com.epam.kitchenservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    @NotNull
    private Long orderId;

    @NotNull
    private Boolean toBeCompensated;
}
