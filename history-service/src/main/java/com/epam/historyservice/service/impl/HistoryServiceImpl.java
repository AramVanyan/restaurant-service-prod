package com.epam.historyservice.service.impl;

import com.epam.historyservice.entity.OrderDetails;
import com.epam.historyservice.model.Delivery;
import com.epam.historyservice.model.Order;
import com.epam.historyservice.model.Payment;
import com.epam.historyservice.model.Ticket;
import com.epam.historyservice.repository.OrderHistoryRepository;
import com.epam.historyservice.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryServiceImpl  implements HistoryService {


    private final OrderHistoryRepository repository;

    @Override
    public OrderDetails getOrderDetails(Long orderId) {
      return  repository.findByOrderId(orderId);
    }

    @Override
    public void addData(Payment payment) {
        OrderDetails orderDetails=getOrderDetails(payment.getOrderId());
        if(orderDetails==null){
            orderDetails=new OrderDetails();
        }
        orderDetails.setSum(payment.getSum());
        repository.save(orderDetails);
    }

    @Override
    public void addData(Ticket ticket) {
        OrderDetails orderDetails=getOrderDetails(ticket.getOrderId());
        if(orderDetails==null){
            orderDetails=new OrderDetails();
        }
        repository.save(orderDetails);

    }

    @Override
    public void addData(Order order) {
        OrderDetails orderDetails=getOrderDetails(order.getId());
        if(orderDetails==null){
            orderDetails=new OrderDetails();
        }
        orderDetails.setOrderDescription(order.getOrderDescription());
        orderDetails.setUserId(order.getUserId());
        repository.save(orderDetails);

    }

    @Override
    public void addData(Delivery delivery) {
        OrderDetails orderDetails=getOrderDetails(delivery.getOrderId());
        if(orderDetails==null){
            orderDetails=new OrderDetails();
        }
        orderDetails.setOrderId(delivery.getOrderId());
        orderDetails.setScheduledDeliveryTime(delivery.getScheduledDeliveryTime());
        orderDetails.setCompletionTime(delivery.getCompletionTime());
        repository.save(orderDetails);

    }
}
