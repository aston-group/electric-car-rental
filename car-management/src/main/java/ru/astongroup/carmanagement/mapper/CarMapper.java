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
        carCreateDTO.setBatteryCapacityInKWh(savedCar.getBatteryCapacityInKWh());
        carCreateDTO.setMileageInKilometers(savedCar.getMileageInKilometers());
        carCreateDTO.setRangeInKilometers(savedCar.getRangeInKilometers());
        carCreateDTO.setStatus(savedCar.getStatus());
        carCreateDTO.setCostPerMinute(savedCar.getCostPerMinute());
        carCreateDTO.setIssues(savedCar.getIssues());
        return carCreateDTO;
    }

    public Car toEntity(CarCreateDTO carCreateDTO) {
        Car car = new Car();
        car.setModel(carCreateDTO.getModel());
        car.setManufacturer(carCreateDTO.getManufacturer());
        car.setBatteryCapacityInKWh(carCreateDTO.getBatteryCapacityInKWh());
        car.setMileageInKilometers(carCreateDTO.getMileageInKilometers());
        car.setRangeInKilometers(carCreateDTO.getRangeInKilometers());
        car.setStatus(carCreateDTO.getStatus());
        car.setCostPerMinute(carCreateDTO.getCostPerMinute());
        car.setIssues(carCreateDTO.getIssues());
        return car;
    }
}
