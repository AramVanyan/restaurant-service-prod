package com.epam.paymentservice.mapper;

import com.epam.paymentservice.dto.CreditCardDto;
import com.epam.paymentservice.entity.CreditCard;
import org.springframework.stereotype.Component;

@Component
public class CreditCardMapper {
    public CreditCard toEntity(CreditCardDto creditCardDto) {
        return CreditCard.builder()
                .cardNumber(creditCardDto.getCreditCardNumber())
                .balance(creditCardDto.getBalance())
                .build();
    }
    public CreditCardDto toDto(CreditCard creditCard) {
        return CreditCardDto.builder()
                .creditCardNumber(creditCard.getCardNumber())
                .balance(creditCard.getBalance())
                .build();
    }
}
