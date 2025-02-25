package ru.astongroup.carmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.dto.CarResponseDTO;
import ru.astongroup.carmanagement.dto.CarUpdateDTO;
import ru.astongroup.carmanagement.entity.Car;
import ru.astongroup.carmanagement.entity.CarStatus;

public class CarMapper {

    public static CarCreateDTO toDTO(Car savedCar) {
        if (savedCar == null) {
            return null;
        }
        CarCreateDTO carCreateDTO = new CarCreateDTO();
        carCreateDTO.setModel(savedCar.getModel());
        carCreateDTO.setManufacturer(savedCar.getManufacturer());
        carCreateDTO.setBatteryCapacityInKwh(savedCar.getBatteryCapacityInKwh());
        carCreateDTO.setMileageInKilometers(savedCar.getMileageInKilometers());
        carCreateDTO.setRangeInKilometers(savedCar.getRangeInKilometers());
        carCreateDTO.setStatus(String.valueOf(savedCar.getStatus()));
        carCreateDTO.setCostPerMinute(savedCar.getCostPerMinute());
        carCreateDTO.setIssues(savedCar.getIssues());
        return carCreateDTO;
    }

    public static Car toEntity(CarCreateDTO carCreateDTO) {
        if (carCreateDTO == null) {
            return null;
        }
        Car car = new Car();
        car.setModel(carCreateDTO.getModel());
        car.setManufacturer(carCreateDTO.getManufacturer());
        car.setBatteryCapacityInKwh(carCreateDTO.getBatteryCapacityInKwh());
        car.setMileageInKilometers(carCreateDTO.getMileageInKilometers());
        car.setRangeInKilometers(carCreateDTO.getRangeInKilometers());
        car.setStatus(CarStatus.valueOf(carCreateDTO.getStatus()));
        car.setCostPerMinute(carCreateDTO.getCostPerMinute());
        car.setIssues(carCreateDTO.getIssues());
        return car;
    }

    public static CarResponseDTO toResponseDto(Car car) {
        if (car == null) {
            return null;
        }
        CarResponseDTO carResponseDTO = new CarResponseDTO();
        carResponseDTO.setId(car.getId());
        carResponseDTO.setModel(car.getModel());
        carResponseDTO.setManufacturer(car.getManufacturer());
        carResponseDTO.setBatteryCapacityInKwh(car.getBatteryCapacityInKwh());
        carResponseDTO.setMileageInKilometers(car.getMileageInKilometers());
        carResponseDTO.setRangeInKilometers(car.getRangeInKilometers());
        carResponseDTO.setStatus(String.valueOf(car.getStatus()));
        carResponseDTO.setCostPerMinute(car.getCostPerMinute());
        carResponseDTO.setIssues(car.getIssues());
        return carResponseDTO;
    }

    public static void updateEntity(CarUpdateDTO carUpdateDTO, Car car) {
        car.setModel(carUpdateDTO.getModel());
        car.setManufacturer(carUpdateDTO.getManufacturer());
        car.setBatteryCapacityInKwh(carUpdateDTO.getBatteryCapacityInKwh());
        car.setMileageInKilometers(carUpdateDTO.getMileageInKilometers());
        car.setRangeInKilometers(carUpdateDTO.getRangeInKilometers());
        car.setStatus(CarStatus.valueOf(carUpdateDTO.getStatus()));
        car.setCostPerMinute(carUpdateDTO.getCostPerMinute());
        car.setIssues(carUpdateDTO.getIssues());
    }
}
