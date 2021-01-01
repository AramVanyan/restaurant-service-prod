package com.epam.deliveryservice.mapper;

import com.epam.deliveryservice.dto.DeliveryDto;
import com.epam.deliveryservice.entity.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    Delivery toEntity(DeliveryDto deliveryDto);
    DeliveryDto toDto(Delivery order);
}