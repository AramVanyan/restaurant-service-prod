package com.epam.userservice.controller;

import com.epam.userservice.dto.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @PostMapping("make-order")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDto orderDto) {
        logger.info("making order");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
