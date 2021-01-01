package com.epam.paymentservice.mapper;

import com.epam.paymentservice.dto.PaymentDto;
import com.epam.paymentservice.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentDto paymentDto);
    PaymentDto toDto(Payment payment);
}
