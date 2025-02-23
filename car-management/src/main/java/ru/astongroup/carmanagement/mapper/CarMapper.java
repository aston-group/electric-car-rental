package ru.astongroup.carmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;

@Component
public class CarMapper {

    public CarCreateDTO toDTO(Car savedCar) {
        if (savedCar == null) {
            return null;
        }
        CarCreateDTO carCreateDTO = new CarCreateDTO();
        carCreateDTO.setModel(savedCar.getModel());
        carCreateDTO.setManufacturer(savedCar.getManufacturer());
        carCreateDTO.setBattery_capacity_in_kwh(savedCar.getBattery_capacity_in_kwh());
        carCreateDTO.setMileage_in_kilometers(savedCar.getMileage_in_kilometers());
        carCreateDTO.setRange_in_kilometers(savedCar.getRange_in_kilometers());
        carCreateDTO.setStatus(savedCar.getStatus());
        carCreateDTO.setCost_per_minute(savedCar.getCost_per_minute());
        carCreateDTO.setIssues(savedCar.getIssues());
        return carCreateDTO;
    }

    public Car toEntity(CarCreateDTO carCreateDTO) {
        if (carCreateDTO == null) {
            return null;
        }
        Car car = new Car();
        car.setModel(carCreateDTO.getModel());
        car.setManufacturer(carCreateDTO.getManufacturer());
        car.setBattery_capacity_in_kwh(carCreateDTO.getBattery_capacity_in_kwh());
        car.setMileage_in_kilometers(carCreateDTO.getMileage_in_kilometers());
        car.setRange_in_kilometers(carCreateDTO.getRange_in_kilometers());
        car.setStatus(carCreateDTO.getStatus());
        car.setCost_per_minute(carCreateDTO.getCost_per_minute());
        car.setIssues(carCreateDTO.getIssues());
        return car;
    }
}
