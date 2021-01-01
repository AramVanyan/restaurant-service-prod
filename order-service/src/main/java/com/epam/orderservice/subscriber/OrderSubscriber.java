package com.epam.orderservice.subscriber;

import com.epam.orderservice.dto.OrderDto;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.mapper.OrderMapper;
import com.epam.orderservice.service.OrderService;
import com.epam.orderservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
public class OrderSubscriber implements MessageListener {

    private PaymentService paymentService;
    private OrderService orderService;
    private ObjectMapper objectMapper;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Autowired
    public OrderSubscriber(OrderService orderService,ObjectMapper objectMapper,PaymentService paymentService) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
        this.paymentService = paymentService;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        OrderDto orderDto = objectMapper.readValue(message.getBody(), OrderDto.class);
        Order order = orderMapper.toEntity(orderDto);
        orderService.save(order);
        PaymentDto paymentDto = paymentService.composePayment(order,false);
        orderService.publishPayment(paymentDto);
        orderService.publishHistoryEvent(order);
    }
}
