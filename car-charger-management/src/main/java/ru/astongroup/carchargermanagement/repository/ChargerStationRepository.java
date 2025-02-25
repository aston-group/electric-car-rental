package ru.astongroup.carchargermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astongroup.carchargermanagement.entity.ChargerStation;

public interface ChargerStationRepository extends JpaRepository<ChargerStation, Long> {
}
