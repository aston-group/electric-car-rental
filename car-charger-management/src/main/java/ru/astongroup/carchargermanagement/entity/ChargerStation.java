package ru.astongroup.carchargermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chargerstations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerStation {

    @Id
    @Column(name = "station_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    @Column(name = "name", nullable = false)
    private String nameStation;

    @Column(name = "address", nullable = false)
    private String addressStation;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailableStation;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;
}
