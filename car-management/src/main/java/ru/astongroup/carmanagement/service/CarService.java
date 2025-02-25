package ru.astongroup.carmanagement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.dto.CarResponseDTO;
import ru.astongroup.carmanagement.dto.CarUpdateDTO;
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
    //private final CarMapper carMapper;

    @Transactional
    public CarCreateDTO createCar(CarCreateDTO carCreateDTO) {
        log.info("Добавление нового автомобиля");
        Car product = CarMapper.toEntity(carCreateDTO);
        Car savedCar = carRepository.save(product);
        return CarMapper.toDTO(savedCar);
    }

    public Optional<CarResponseDTO> getCarById(Long id) {
        log.info("Получение автомобиля с id = {}", id);
        return carRepository.findById(id)
                .map(CarMapper::toResponseDto);
    }

    public List<CarResponseDTO> getAllCars() {
        log.info("Получение списка автомобилей.");
        return carRepository.findAll()
                .stream()
                .map(CarMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarResponseDTO updateCar(Long id, CarUpdateDTO carUpdateDTO) {
        return carRepository.findById(id)
                .map(car -> {
                    CarMapper.updateEntity(carUpdateDTO, car);
                    Car updatedCar = carRepository.save(car);
                    return CarMapper.toResponseDto(updatedCar);
                })
                .orElse(null);
    }

    public void deleteCar(Long id) {
        log.info("Удаление автомобиля с id = {}", id);
        carRepository.deleteById(id);
    }
}
