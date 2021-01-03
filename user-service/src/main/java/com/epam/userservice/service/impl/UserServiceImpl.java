package com.epam.userservice.service.impl;

import com.epam.userservice.dto.OrderDto;
import com.epam.userservice.publisher.OrderPublisher;
import com.epam.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private OrderPublisher orderPublisher;

    public UserServiceImpl(OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    public void publishOrder(OrderDto orderDto) {
        orderPublisher.publish(orderDto);
    }
}
