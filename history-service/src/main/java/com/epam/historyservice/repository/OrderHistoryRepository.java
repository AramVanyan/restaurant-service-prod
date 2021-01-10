package com.epam.historyservice.repository;

import com.epam.historyservice.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderDetails,Long> {
    OrderDetails findByOrderId(long orderId);

    @Modifying
    @Query("update OrderDetails od set od.sum= :sum where od.orderId = :orderId")
    void updatePayment(@Param(value = "sum") Long sum, @Param(value = "orderId") Long orderId);

    @Modifying
    @Query("update OrderDetails od set od.ticketNumber = :ticketNumber where od.orderId = :orderId")
    void updateTicket(@Param(value = "ticketNumber") String ticketNumber, @Param(value = "orderId") Long orderId);

    @Modifying
    @Query("update OrderDetails od set od.scheduledDeliveryTime = :scheduledDeliveryTime," +
            "od.completionTime = :completionTime" +
            " where od.orderId = :orderId")
    void updateDelivery(@Param(value = "scheduledDeliveryTime") Timestamp scheduledDeliveryTime,
                        @Param(value = "completionTime") Timestamp completionTime,
                        @Param(value = "orderId") Long orderId);

    @Modifying
    @Query("update OrderDetails od set od.orderDescription = :orderDescription," +
            "od.userId = :userId" +
            " where od.orderId = :orderId")
    void updateOrder(@Param(value = "orderDescription") String orderDescription,
                     @Param(value = "userId") Long userId,
                     @Param(value = "orderId") Long orderId);
}
