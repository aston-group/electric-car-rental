package ru.astongroup.carmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarCreateDTO {
    private String model;
    private String manufacturer;
    private double battery_capacity_in_kwh; // kWh
    private int mileage_in_kilometers;
    private int range_in_kilometers;
    private String status; //
    private BigDecimal costPerMinute; //Cost per minute of rental
    private String issues;
}
