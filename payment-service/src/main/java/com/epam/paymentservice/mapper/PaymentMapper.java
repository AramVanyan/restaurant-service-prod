package com.epam.paymentservice.mapper;

import com.epam.paymentservice.dto.PaymentDto;
import com.epam.paymentservice.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    private CreditCardMapper creditCardMapper;

    @Autowired
    public PaymentMapper(CreditCardMapper creditCardMapper) {
        this.creditCardMapper = creditCardMapper;
    }

    public Payment toEntity(PaymentDto paymentDto) {
        return Payment.builder()
                .creditCard(creditCardMapper.toEntity(paymentDto.getCreditCardDto()))
                .sum(paymentDto.getSum())
                .orderId(paymentDto.getOrderId())
                .build();
    }
    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .creditCardDto(creditCardMapper.toDto(payment.getCreditCard()))
                .sum(payment.getSum())
                .orderId(payment.getOrderId())
                .build();
    }
}
