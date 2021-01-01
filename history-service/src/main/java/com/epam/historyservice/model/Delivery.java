package com.epam.historyservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class Delivery {

    private Long id;

    private Long orderId;

    private Date scheduledDeliveryTime;

    private Date completionTime = new Date(scheduledDeliveryTime.getTime() + 3600*1000*2);
}
