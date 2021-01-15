package com.epam.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDto {
    @NotNull
    private Long orderId;

    @NotNull
    private Timestamp scheduledDeliveryTime;

    @NotNull
    private Boolean abort;
}
