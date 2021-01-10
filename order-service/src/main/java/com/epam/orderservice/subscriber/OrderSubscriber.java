package com.epam.orderservice.subscriber;

import com.epam.orderservice.dto.HistoryEvent;
import com.epam.orderservice.dto.OrderDto;
import com.epam.orderservice.dto.PaymentDto;
import com.epam.orderservice.entity.Order;
import com.epam.orderservice.event.EventType;
import com.epam.orderservice.mapper.OrderMapper;
import com.epam.orderservice.service.OrderService;
import com.epam.orderservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.logging.Logger;

@Service
@Slf4j
@NoArgsConstructor
public class OrderSubscriber implements MessageListener {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PaymentService paymentService;
    private OrderService orderService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderSubscriber(OrderService orderService, PaymentService paymentService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.orderMapper = orderMapper;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        logger.info("get create order request");
        OrderDto orderDto = objectMapper.readValue(message.getBody(), OrderDto.class);
        Order order = orderMapper.toEntity(orderDto);
        order.setCreatedDate(Timestamp.from(Instant.now()));
        orderService.save(order);
        PaymentDto paymentDto = paymentService.composePayment(order,false);
        orderService.publishPayment(paymentDto);
        HistoryEvent historyEvent = HistoryEvent.builder()
                .eventType(EventType.ORDER)
                .body(order)
                .build();
        orderService.publishHistoryEvent(historyEvent);
    }
}


