package ru.astongroup.carmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarUpdateDTO {
    private Long id;

    @NotBlank
    private String model;

    @NotBlank
    private String manufacturer;

    private double batteryCapacityInKwh; // kWh
    private int mileageInKilometers;
    private int rangeInKilometers;

    @NotBlank
    private String status;

    @NotBlank
    private BigDecimal costPerMinute; //Cost per minute of rental

    private String issues;
}
