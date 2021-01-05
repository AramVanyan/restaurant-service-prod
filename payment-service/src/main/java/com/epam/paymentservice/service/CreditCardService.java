package com.epam.paymentservice.service;


import com.epam.paymentservice.entity.CreditCard;

public interface CreditCardService {
    CreditCard findByCardNumber(String cardNumber);
}
