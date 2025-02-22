package ru.astongroup.carmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;
import ru.astongroup.carmanagement.service.CarService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @GetMapping("/hello")
    public String helloInit() {
        return "Hello from Car Controller!!!";
    }

    @PostMapping("/create")
    public CarCreateDTO createCar(@RequestBody CarCreateDTO carCreateDTO){
        return carService.createCar(carCreateDTO);
    }

    @GetMapping("/get/{id}")
    public CarCreateDTO getCarById(@PathVariable long id) {
        return carService.getById(id);
    }
}