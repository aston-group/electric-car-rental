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
    private double battery_capacity_in_kwh; // kWh
    private int mileage_in_kilometers;
    private int range_in_kilometers;
    private String status; //
    private BigDecimal costPerMinute; //Cost per minute of rental
    private String issues;
}
