package ru.astongroup.carbooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Booking Controller!";
    }
}