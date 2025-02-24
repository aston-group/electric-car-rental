package ru.astongroup.carmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.astongroup.carmanagement.dto.CarCreateDTO;
import ru.astongroup.carmanagement.entity.Car;
import ru.astongroup.carmanagement.entity.CarStatus;

@Component
public class CarMapper {

    public CarCreateDTO toDTO(Car savedCar) {
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

    public Car toEntity(CarCreateDTO carCreateDTO) {
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
}
