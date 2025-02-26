package ru.astongroup.carbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astongroup.carbooking.entity.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    //не видит БД с докера, как исправить?
//    @Query(value = """
//            SELECT id, user_id, car_id, start_time, end_time, status, price
//            FROM bookings
//            WHERE user_id = :user_id""", nativeQuery = true)
//    List<Booking> findByUserId(@Param(value = "user_id") Long userId);

    List<Booking> findByUserId(Long userId);
}
