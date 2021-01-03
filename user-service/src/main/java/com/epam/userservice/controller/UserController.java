package com.epam.userservice.controller;

import com.epam.userservice.dto.OrderDto;
import com.epam.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String greetingsUser() {
        return "Hi User";
    }

    @PostMapping("make-order")
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDto orderDto) {
        logger.info("making order");
        userService.publishOrder(orderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
