package com.epam.historyservice.service.impl;

import com.epam.historyservice.entity.OrderDetails;
import com.epam.historyservice.repository.OrderHistoryRepository;
import com.epam.historyservice.service.HistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class HistoryServiceImpl  implements HistoryService {
    private final OrderHistoryRepository repository;

    @Override
    public void addPaymentInfo(Object paymentInfo) {
        LinkedHashMap payment= (LinkedHashMap)paymentInfo;
        String orderId = payment.get("orderId").toString();
        String sum = payment.get("sum").toString();

        OrderDetails orderDetails= repository.findByOrderId(Long.parseLong(orderId));

        if (orderDetails != null) log.info(orderDetails.toString());
        else log.info("null");

        if(orderDetails==null){
            orderDetails=new OrderDetails();
            orderDetails.setOrderSum(Long.valueOf(sum));
            repository.save(orderDetails);
            log.info("save payment");

        } else {
            orderDetails.setOrderSum(Long.valueOf(sum));
            repository.updatePayment(Long.valueOf(sum), Long.valueOf(orderId));
        }
    }

    @Override
    public void addTicketInfo(Object ticketInfo) {
        LinkedHashMap ticket = (LinkedHashMap)ticketInfo;
        String orderId = ticket.get("orderId").toString();
        String ticketNumber = ticket.get("ticketNumber").toString();

        OrderDetails orderDetails= repository.findByOrderId(Long.parseLong(orderId));

        if (orderDetails != null) log.info(orderDetails.toString());
        else log.info("null");


        if(orderDetails == null){
            orderDetails = new OrderDetails();
            orderDetails.setTicketNumber(ticketNumber);
            repository.save(orderDetails);
            log.info("save ticket");
        } else {
            orderDetails.setTicketNumber(ticketNumber);
            repository.updateTicket(ticketNumber, Long.valueOf(orderId));
        }
    }

    @Override
    public void addDeliveryInfo(Object deliveryInfo) {
        LinkedHashMap delivery= (LinkedHashMap)deliveryInfo;
        String orderId = delivery.get("orderId").toString();
        String scheduledDeliveryTime = delivery.get("scheduledDeliveryTime").toString();
        String completionTime = delivery.get("completionTime").toString();

        OrderDetails orderDetails= repository.findByOrderId(Long.parseLong(orderId));

        if (orderDetails != null) log.info(orderDetails.toString());
        else log.info("null");

        if(orderDetails==null){
            orderDetails=new OrderDetails();
            orderDetails.setOrderId(Long.valueOf(orderId));
            orderDetails.setScheduledDeliveryTime(Timestamp.valueOf(scheduledDeliveryTime));
            orderDetails.setCompletionTime(Timestamp.valueOf(completionTime));
            repository.save(orderDetails);
            log.info("save delivery");
        } else {
            orderDetails.setScheduledDeliveryTime(Timestamp.valueOf(scheduledDeliveryTime));
            orderDetails.setCompletionTime(Timestamp.valueOf(completionTime));
            repository.updateDelivery(Timestamp.valueOf(scheduledDeliveryTime), Timestamp.valueOf(completionTime), Long.valueOf(orderId));
        }
    }

    @Override
    public void addOrderInfo(Object orderInfo) {
        LinkedHashMap order= (LinkedHashMap)orderInfo;
        String orderId = order.get("id").toString();
        String orderDescription = order.get("orderDescription").toString();
        String userId = order.get("userId").toString();

        OrderDetails orderDetails= repository.findByOrderId(Long.parseLong(orderId));
        if (orderDetails != null) log.info(orderDetails.toString());
        else log.info("null");

        if(orderDetails == null){
            orderDetails = new OrderDetails();
            orderDetails.setOrderId(Long.valueOf(orderId));
            orderDetails.setOrderDescription(orderDescription);
            orderDetails.setUserId(Long.valueOf(userId));
            repository.save(orderDetails);
            log.info("save order");
         } else {
            orderDetails.setOrderDescription(orderDescription);
            orderDetails.setUserId(Long.valueOf(userId));
            repository.updateOrder(orderDescription, Long.valueOf(userId), Long.valueOf(orderId));
        }
    }
}
