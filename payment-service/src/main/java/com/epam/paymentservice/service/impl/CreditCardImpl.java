package com.epam.paymentservice.service.impl;

import com.epam.paymentservice.entity.CreditCard;
import com.epam.paymentservice.repository.CreditCardRepository;
import com.epam.paymentservice.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardImpl implements CreditCardService {
    CreditCardRepository creditCardRepository;

    @Autowired
    public CreditCardImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCard findByCardNumber(String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber).orElse(new CreditCard());
    }
}
