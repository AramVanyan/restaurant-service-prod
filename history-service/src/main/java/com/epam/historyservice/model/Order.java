package com.epam.historyservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Order {
    private Long id;

    private Long userId;

    private Timestamp createdDate;

    private Date deliveryTime;

    private String creditCard;

    private Long sum;

    private String orderDescription;

}
