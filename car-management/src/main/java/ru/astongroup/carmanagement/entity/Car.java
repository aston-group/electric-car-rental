package ru.astongroup.carmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String manufacturer;
    private double batteryCapacityInKWh; // kWh
    private int mileageInKilometers;
    private int rangeInKilometers;
    private String status; //
    private BigDecimal costPerMinute; //Cost per minute of rental
    private String issues;
}
