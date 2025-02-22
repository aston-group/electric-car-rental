package ru.astongroup.carmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;

@Component
public class CarMapper {

    public CarCreateDTO toDTO(Car savedCar) {
        CarCreateDTO carCreateDTO = new CarCreateDTO();
        carCreateDTO.setModel(savedCar.getModel());
        carCreateDTO.setManufacturer(savedCar.getManufacturer());
        carCreateDTO.setBattery_capacity_in_kwh(savedCar.getBattery_capacity_in_kwh());
        carCreateDTO.setMileage_in_kilometers(savedCar.getMileage_in_kilometers());
        carCreateDTO.setRange_in_kilometers(savedCar.getRange_in_kilometers());
        carCreateDTO.setStatus(savedCar.getStatus());
        carCreateDTO.setCostPerMinute(savedCar.getCostPerMinute());
        carCreateDTO.setIssues(savedCar.getIssues());
        return carCreateDTO;
    }

    public Car toEntity(CarCreateDTO carCreateDTO) {
        Car car = new Car();
        car.setModel(carCreateDTO.getModel());
        car.setManufacturer(carCreateDTO.getManufacturer());
        car.setBattery_capacity_in_kwh(carCreateDTO.getBattery_capacity_in_kwh());
        car.setMileage_in_kilometers(carCreateDTO.getMileage_in_kilometers());
        car.setRange_in_kilometers(carCreateDTO.getRange_in_kilometers());
        car.setStatus(carCreateDTO.getStatus());
        car.setCostPerMinute(carCreateDTO.getCostPerMinute());
        car.setIssues(carCreateDTO.getIssues());
        return car;
    }
}
