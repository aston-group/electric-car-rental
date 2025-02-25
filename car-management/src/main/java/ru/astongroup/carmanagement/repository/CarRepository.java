package ru.astongroup.carmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astongroup.carmanagement.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
