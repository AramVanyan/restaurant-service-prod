package com.epam.historyservice.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class OrderDetailsDto {

    @NotNull
    private String orderDescription;

    @NotNull
    private Long sum;

    @NotNull
    private Date scheduledDeliveryTime;

    @NotNull
    private Date completionTime = new Date(scheduledDeliveryTime.getTime() + 3600*1000*2);
}
