package com.epam.paymentservice.subscriber;


import com.epam.paymentservice.dto.PaymentDto;
import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;
import com.epam.paymentservice.event.EventResult;
import com.epam.paymentservice.event.EventType;
import com.epam.paymentservice.mapper.PaymentMapper;
import com.epam.paymentservice.service.PaymentService;
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
public class PaymentSubscriber implements MessageListener {
    private PaymentService paymentService;
    private ObjectMapper objectMapper;
    private final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);


    @Autowired
    public PaymentSubscriber(PaymentService paymentService,ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
        this.paymentService = paymentService;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        PaymentDto paymentDto = objectMapper.readValue(message.getBody(), PaymentDto.class);

        if (paymentDto.getToBeCompensated()) paymentService.compensatePayment(paymentDto.getOrderId());
        else {
            Payment payment = paymentMapper.toEntity(paymentDto);

            Event event = Event.builder()
                    .orderId(payment.getOrderId())
                    .eventType(EventType.PAYMENT)
                    .build();

            if (payment.getSum() > 15000) {
                event.setEventResult(EventResult.SUCCESS);
                paymentService.save(payment);
            } else event.setEventResult(EventResult.FAILED);

            paymentService.publishEvent(event);
        }
    }
}
