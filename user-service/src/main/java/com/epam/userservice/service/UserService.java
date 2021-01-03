package com.epam.userservice.service;

import com.epam.userservice.dto.OrderDto;

public interface UserService {
    void publishOrder(OrderDto orderDto);
}
