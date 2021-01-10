package com.epam.historyservice.service.impl;

import com.epam.historyservice.entity.OrderDetails;
import com.epam.historyservice.repository.OrderHistoryRepository;
import com.epam.historyservice.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

@Service
@AllArgsConstructor
public class HistoryServiceImpl  implements HistoryService {
    private final OrderHistoryRepository repository;

    @Override
    public OrderDetails getOrderDetails(Long orderId) {
        return  repository.findByOrderId(orderId);
    }

    @Override
    public void addPaymentInfo(Object paymentInfo) {
        LinkedHashMap payment= (LinkedHashMap)paymentInfo;
        String orderId = payment.get("orderId").toString();
        String sum = payment.get("sum").toString();

        OrderDetails orderDetails=getOrderDetails(Long.valueOf(orderId));

        if(orderDetails==null){
            orderDetails=new OrderDetails();
            orderDetails.setSum(Long.valueOf(sum));
            repository.save(orderDetails);
        } else {
            orderDetails.setSum(Long.valueOf(sum));
            repository.updatePayment(Long.valueOf(sum), Long.valueOf(orderId));
        }
    }

    @Override
    public void addTicketInfo(Object ticketInfo) {
        LinkedHashMap ticket = (LinkedHashMap)ticketInfo;
        String orderId = ticket.get("orderId").toString();
        String ticketNumber = ticket.get("ticketNumber").toString();

        OrderDetails orderDetails = getOrderDetails(Long.valueOf(orderId));
        if(orderDetails == null){
            orderDetails = new OrderDetails();
            orderDetails.setTicketNumber(ticketNumber);
            repository.save(orderDetails);
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

        OrderDetails orderDetails=getOrderDetails(Long.valueOf(orderId));
        if(orderDetails==null){
            orderDetails=new OrderDetails();
            orderDetails.setScheduledDeliveryTime((Timestamp) delivery.get("scheduledDeliveryTime"));
            orderDetails.setCompletionTime((Timestamp)delivery.get("completionTime"));
            repository.save(orderDetails);
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

        OrderDetails orderDetails=getOrderDetails(Long.valueOf(orderId));
        if(orderDetails == null){
            orderDetails = new OrderDetails();
            orderDetails.setOrderDescription(orderDescription);
            orderDetails.setUserId(Long.valueOf(userId));
            repository.save(orderDetails);
        } else {
            orderDetails.setOrderDescription(orderDescription);
            orderDetails.setUserId(Long.valueOf(userId));
            repository.updateOrder(orderDescription, Long.valueOf(userId), Long.valueOf(orderId));
        }
    }
}
