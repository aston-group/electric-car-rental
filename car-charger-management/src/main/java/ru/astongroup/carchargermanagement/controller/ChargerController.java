package ru.astongroup.carchargermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chargers")
public class ChargerController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Charger Controller!";
    }
}