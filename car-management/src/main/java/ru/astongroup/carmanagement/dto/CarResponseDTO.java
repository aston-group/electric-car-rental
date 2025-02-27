package ru.astongroup.carmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarResponseDTO {
    private Long id;
    private String model;
    private String manufacturer;
    private double batteryCapacityInKwh; // kWh
    private int mileageInKilometers;
    private int rangeInKilometers;
    private String status;
    private BigDecimal costPerMinute; //Cost per minute of rental
    private String issues;
}
