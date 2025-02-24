package ru.astongroup.carmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarCreateDTO {
    private Long id;

    @NotBlank
    private String model;

    @NotBlank
    private String manufacturer;

    private double battery_capacity_in_kwh; // kWh
    private int mileage_in_kilometers;
    private int range_in_kilometers;

    @NotBlank
    private String status;

    @NotBlank
    private BigDecimal cost_per_minute; //Cost per minute of rental

    private String issues;
}
