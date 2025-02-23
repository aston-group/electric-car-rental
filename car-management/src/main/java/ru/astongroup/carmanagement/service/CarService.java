package ru.astongroup.carmanagement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;
import ru.astongroup.carmanagement.mapper.CarMapper;
import ru.astongroup.carmanagement.repository.CarRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Transactional
    public CarCreateDTO createCar(CarCreateDTO carCreateDTO) {
        Car product = carMapper.toEntity(carCreateDTO);
        Car savedCar = carRepository.save(product);
        return carMapper.toDTO(savedCar);
    }

    public Optional<CarCreateDTO> getCarById(Long id) {
        log.info("Получение автомобиля с id = {}", id);
        return carRepository.findById(id).map(carMapper::toDTO);
    }

    public List<CarCreateDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
