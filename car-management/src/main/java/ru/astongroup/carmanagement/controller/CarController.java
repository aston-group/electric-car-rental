package ru.astongroup.carmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;
import ru.astongroup.carmanagement.service.CarService;

import java.util.List;

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
    public ResponseEntity<CarCreateDTO> createCar(@RequestBody CarCreateDTO carCreateDTO) {
        return ResponseEntity.ok(carService.createCar(carCreateDTO));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CarCreateDTO> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarCreateDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @DeleteMapping("del/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}