package ru.astongroup.carmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarCreateDTO {
    private String model;
    private String manufacturer;
    private double batteryCapacityInKWh; // kWh
    private int mileageInKilometers;
    private int rangeInKilometers;
    private String status; //
    private BigDecimal costPerMinute; //Cost per minute of rental
    private String issues;
}
