package com.epam.historyservice.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class Delivery {

    private Long id;

    private Long orderId;

    private Timestamp scheduledDeliveryTime;

    private Timestamp completionTime;
}
