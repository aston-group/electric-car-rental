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

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "battery_capacity_in_kwh")
    private double battery_capacity_in_kwh; // kWh

    @Column(name = "mileage_in_kilometers")
    private int mileage_in_kilometers;

    @Column(name = "range_in_kilometers")
    private int range_in_kilometers;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private CarStatus status; //

    @Column(name = "cost_per_minute")
    private BigDecimal cost_per_minute; //Cost per minute of rental

    @Column(name = "issues")
    private String issues;
}
