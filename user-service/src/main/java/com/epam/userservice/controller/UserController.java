package com.epam.userservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class UserController {

    //Api Gateway
    @GetMapping("homepage")
    public ResponseEntity<String> getHomepage(Principal principal) {
        return ResponseEntity.ok("Hi " + principal.getName());
    }

    @PostMapping("makeOrder")
    public ResponseEntity<Object> makeOrder(@RequestBody Object orderRequest) {

        //publish to order service
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
