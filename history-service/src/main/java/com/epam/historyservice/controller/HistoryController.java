package com.epam.historyservice.controller;

import com.epam.historyservice.entity.OrderDetails;
import com.epam.historyservice.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class HistoryController {

    private final HistoryService service;


    @GetMapping("/history/{userId}")
    public OrderDetails getOrderDetails(@PathVariable("userId") String userId) {

        return service.getOrderDetails(Long.parseLong(userId));
    }
}
