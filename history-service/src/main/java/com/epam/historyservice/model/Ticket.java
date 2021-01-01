package com.epam.historyservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class Ticket {

    private Long id;

    private Date creationTime;

    private Long orderId;

}
