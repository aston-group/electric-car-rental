package ru.astongroup.carmanagement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;
import ru.astongroup.carmanagement.mapper.CarMapper;
import ru.astongroup.carmanagement.repository.CarRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private CarRepository carRepository;
    private CarMapper carMapper;
    @Transactional
    public CarCreateDTO createCar(CarCreateDTO carCreateDTO){
        Car car = carMapper.toEntity(carCreateDTO);
        Car savedCar = carRepository.save(car);
        return carMapper.toDTO(savedCar);
    }

    public CarCreateDTO getById(long id) {
        Car car = carRepository.getById(id);
        return carMapper.toDTO(car);
    }
}
