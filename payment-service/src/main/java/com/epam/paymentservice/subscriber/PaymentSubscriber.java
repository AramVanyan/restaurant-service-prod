package com.epam.paymentservice.subscriber;


import com.epam.paymentservice.dto.CreditCardDto;
import com.epam.paymentservice.dto.HistoryEvent;
import com.epam.paymentservice.dto.PaymentDto;
import com.epam.paymentservice.entity.Payment;
import com.epam.paymentservice.event.Event;
import com.epam.paymentservice.event.EventResult;
import com.epam.paymentservice.event.EventType;
import com.epam.paymentservice.mapper.CreditCardMapper;
import com.epam.paymentservice.mapper.PaymentMapper;
import com.epam.paymentservice.service.CreditCardService;
import com.epam.paymentservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
public class PaymentSubscriber implements MessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CreditCardService creditCardService;
    private PaymentService paymentService;
    private CreditCardMapper creditCardMapper;
    private PaymentMapper paymentMapper;

    @Autowired
    public PaymentSubscriber(PaymentService paymentService, PaymentMapper paymentMapper, CreditCardService creditCardService,
                             CreditCardMapper creditCardMapper) {
        this.creditCardService = creditCardService;
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
        this.creditCardMapper = creditCardMapper;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] bytes) {
        PaymentDto paymentDto = objectMapper.readValue(message.getBody(), PaymentDto.class);
        log.info("Payment " + paymentDto + "received");

        CreditCardDto creditCardDto = creditCardMapper.toDto(creditCardService.findByCardNumber(paymentDto.getCreditCardDto()
                                                       .getCreditCardNumber()));
        paymentDto.setCreditCardDto(creditCardDto);

        if (paymentDto.getAbort()) paymentService.deletePayment(paymentDto.getOrderId());
        else {
            Payment payment = paymentMapper.toEntity(paymentDto);

            Event event = Event.builder()
                    .orderId(payment.getOrderId())
                    .eventType(EventType.PAYMENT)
                    .build();

            if (payment.getSum() <= payment.getCreditCard().getBalance()) {
                HistoryEvent historyEvent = HistoryEvent.builder()
                        .eventType(EventType.PAYMENT)
                        .body(payment)
                        .build();
                event.setEventResult(EventResult.SUCCESS);
                paymentService.save(payment);
                paymentService.publishHistoryEvent(historyEvent);
            } else event.setEventResult(EventResult.FAILED);
            paymentService.publishEvent(event);
        }
    }
}
