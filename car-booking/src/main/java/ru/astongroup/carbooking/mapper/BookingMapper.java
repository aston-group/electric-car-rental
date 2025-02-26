package ru.astongroup.carbooking.mapper;

import org.springframework.stereotype.Service;
import ru.astongroup.carbooking.dto.BookingReqCreateDto;
import ru.astongroup.carbooking.dto.BookingResponseDTO;
import ru.astongroup.carbooking.entity.Booking;

@Service
public class BookingMapper {

    public Booking toBookingEntity(BookingReqCreateDto bookingReqCreateDto) {
        return Booking.builder()
                .userId(bookingReqCreateDto.getUserId())
                .carId(bookingReqCreateDto.getCarId())
                .startTime(bookingReqCreateDto.getStartTime())
                .endTime(bookingReqCreateDto.getEndTime())
                .build();
    }

    public BookingResponseDTO toBookingResponseDto(Booking booking) {

        return BookingResponseDTO.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .carId(booking.getCarId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .price(booking.getPrice())
                .build();
    }
}


