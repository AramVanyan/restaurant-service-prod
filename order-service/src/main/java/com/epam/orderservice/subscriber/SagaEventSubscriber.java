package com.epam.orderservice.subscriber;


import com.epam.orderservice.entity.Order;
import com.epam.orderservice.event.EventResult;
import com.epam.orderservice.service.DeliveryService;
import com.epam.orderservice.service.KitchenService;
import com.epam.orderservice.service.OrderService;
import com.epam.orderservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.epam.orderservice.event.Event;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class SagaEventSubscriber implements MessageListener {
    private OrderService orderService;
    private ObjectMapper objectMapper;
    private PaymentService paymentService;
    private KitchenService kitchenService;
    private DeliveryService deliveryService;

    @Autowired
    public SagaEventSubscriber(ObjectMapper objectMapper, OrderService orderService,PaymentService paymentService,
                               KitchenService kitchenService,DeliveryService deliveryService){
        this.objectMapper = objectMapper;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.kitchenService = kitchenService;
        this.deliveryService = deliveryService;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        Event event = objectMapper.readValue(message.getBody(), Event.class);
        Order order = orderService.findById(event.getOrderId());

        switch (event.getEventType()) {
            case PAYMENT:
                if (event.getEventResult().equals(EventResult.SUCCESS))
                    kitchenService.composeTicket(order,false);
                else orderService.compensateOrder(order.getId());
                break;
            case KITCHEN:
                if (event.getEventResult().equals(EventResult.SUCCESS))
                    deliveryService.composeDelivery(order,false);
                else orderService.publishPayment(paymentService.composePayment(order,true));
                break;
            case DELIVERY:
                if (event.getEventResult().equals(EventResult.SUCCESS))
                    deliveryService.composeDelivery(order,false);
                else {
                    orderService.publishTicket(kitchenService.composeTicket(order,true));
                    orderService.publishPayment(paymentService.composePayment(order,true));
                }
                break;
            default:
                log.info("default");
        }
    }
}
