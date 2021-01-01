package com.epam.historyservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class CreditCard {

    private Long id;

    private Integer balance;

    private List<Payment> listOfPayments = new ArrayList<>();

}
