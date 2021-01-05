package com.epam.historyservice.model;

import java.util.ArrayList;
import java.util.List;


public class CreditCard {

    private Long id;

    private Integer balance;

    private List<Payment> listOfPayments = new ArrayList<>();

}
