package ru.astongroup.carmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

    @GetMapping("/hello")
    public String helloInit() {
        return "Hello from Car Controller!";
    }
}