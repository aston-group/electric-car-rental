package ru.astongroup.carmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.dto.CarResponseDTO;
import ru.astongroup.carmanagement.dto.CarUpdateDTO;
import ru.astongroup.carmanagement.service.CarService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    public ResponseEntity<CarCreateDTO> createCar(
            @Valid @RequestBody
            CarCreateDTO carCreateDTO) {
        return ResponseEntity.ok(carService.createCar(carCreateDTO));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarResponseDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CarResponseDTO> updateCar(@PathVariable Long id, @Valid @RequestBody CarUpdateDTO carUpdateDTO) {
        CarResponseDTO updatedCar = carService.updateCar(id, carUpdateDTO);
        return updatedCar != null ? ResponseEntity.ok(updatedCar) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("del/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}