package ru.astongroup.carmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double batteryCapacityInKwh; // kWh

    @Column(name = "mileage_in_kilometers")
    private int mileageInKilometers;

    @Column(name = "range_in_kilometers")
    private int rangeInKilometers;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private CarStatus status; //

    @Column(name = "cost_per_minute")
    private BigDecimal costPerMinute; //Cost per minute of rental

    @Column(name = "issues")
    private String issues;
}
