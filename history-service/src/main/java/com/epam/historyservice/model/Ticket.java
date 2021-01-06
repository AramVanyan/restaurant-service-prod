package com.epam.historyservice.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Ticket {

    private Long id;

    private Timestamp creationTime;

    private Long orderId;

}
